package com.lcaohoanq.controllers;

import com.lcaohoanq.constant.ApiConstant;
import com.lcaohoanq.constant.GoogleAuthentication;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.enums.UserStatusEnum;
import com.lcaohoanq.models.Role;
import com.lcaohoanq.models.Status;
import com.lcaohoanq.models.UserGoogle;
import com.lcaohoanq.schemas.RegisterRequest;
import com.lcaohoanq.utils.ApiUtils;
import com.lcaohoanq.utils.AvatarConverter;
import com.lcaohoanq.utils.PayloadUtils;
import com.lcaohoanq.schemas.UserRegisterRequest;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Route("oauth2/callback/google")
public class GoogleOAuthCallback extends Composite<Div> {

    public GoogleOAuthCallback() {
        try {
            // Extract authorization code from the URL
            String authorizationCode = getAuthorizationCode();

            // Exchange authorization code for access token
            String accessToken = getAccessToken(authorizationCode);

            // Retrieve user information
            UserGoogle userInfo = getUserInfo(accessToken);

            // Handle user information (e.g., store in session, redirect, etc.)
            handleUserLogin(userInfo);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String getAuthorizationCode() {
        return UI.getCurrent().getInternals().getLastHandledLocation().getQueryParameters()
            .getParameters().get("code").get(0);
    }

    private String getAccessToken(String authorizationCode) {
        String clientId = GoogleAuthentication.GOOGLE_CLIENT_ID;
        String clientSecret = GoogleAuthentication.GOOGLE_CLIENT_SECRET;
        String redirectUri = GoogleAuthentication.GOOGLE_REDIRECT_URI;
        String tokenEndpoint = GoogleAuthentication.GOOGLE_LINK_GET_TOKEN; //https://accounts.google.com/o/oauth2/token
        String grantType = GoogleAuthentication.GOOGLE_GRANT_TYPE; //authorization_code

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", authorizationCode);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("grant_type", grantType);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, requestBody,
            Map.class);

        if (response.getBody() != null && response.getBody().containsKey("access_token")) {
            return (String) response.getBody().get("access_token");
        } else {
            throw new RuntimeException(
                "Failed to retrieve access token. Response: " + response.getBody());
        }
    }

    private UserGoogle getUserInfo(String accessToken) {
        String userInfoEndpoint = GoogleAuthentication.GOOGLE_LINK_GET_USER_INFO; //https://www.googleapis.com/oauth2/v1/userinfo?access_token=
        String url = userInfoEndpoint + accessToken;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserGoogle> response = restTemplate.getForEntity(url, UserGoogle.class);

        if (response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve user info. URL: " + url);
        }
    }

    private void handleUserLogin(UserGoogle userInfo) {
        String email = userInfo.getEmail();

        byte[] avatar_url = null;

        try {
            avatar_url = AvatarConverter.convertAvatarUrlToByteArray(userInfo.getPicture());
        } catch (IOException e) {
            System.out.println("Failed to convert avatar URL to byte array: " + e.getMessage());
        }

        RegisterRequest user = new UserRegisterRequest();
        user.setId(-2L); //out range of Long data, need to consider later
        user.setFirstName(userInfo.getGiven_name());
        user.setLastName(userInfo.getFamily_name());
        user.setEmail(userInfo.getEmail());
        user.setPhone(null);
        user.setPassword("");
        user.setAddress("");
        user.setBirthday("");
        user.setGender("NOT_PROVIDE");
        user.setRole(new Role(0, UserRoleEnum.USER));
        user.setStatus(new Status(1, UserStatusEnum.VERIFIED));
        user.setCreated_at(LocalDate.now().toString());
        user.setUpdated_at(LocalDate.now().toString());
        user.setAvatar_url(avatar_url);
        user.setGoogle_account_id(1);
        user.setFacebook_account_id(0);

        Map<String, Object> payload = new HashMap<>();
        PayloadUtils.generatePayloadUser(payload, user);

        //call api to store user info
        try {
            HttpResponse<String> response = ApiUtils.postRequest(
                ApiConstant.BASE_URL_BE + ApiConstant.API_PATCH + "/users/oauth2/callback/google", payload);
            Dialog dialog;
            switch (response.statusCode()) {
                case 200:
                    dialog = new Dialog();
                    dialog.add(new H3("Login successfully"));
                    dialog.open();
                    VaadinSession.getCurrent().setAttribute("user", email);
                    VaadinSession.getCurrent().setAttribute("role", UserRoleEnum.USER);
                    UI.getCurrent().getPage().setLocation(
                        "http://localhost:8082/menu"); // Redirect to the home page after successful login
                    break;
                case 400:
                    dialog = new Dialog();
                    dialog.add(new H3("Error 400"));
                    dialog.open();
                    break;
                default:
                    dialog = new Dialog();
                    dialog.add(new H3("Error 500"));
                    dialog.open();
                    break;
            }
        } catch (Exception e) {
            if (e instanceof IOException) {
                UI.getCurrent().getPage().setLocation("http://localhost:8082/errors");
                System.out.println("IOException: " + e.getMessage());
            }
            if (e instanceof InterruptedException) {
                UI.getCurrent().getPage().setLocation("http://localhost:8082/errors");
                System.out.println("InterruptedException: " + e.getMessage());
            }
        }
    }
}
