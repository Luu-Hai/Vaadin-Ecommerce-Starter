package com.lcaohoanq.controllers;

import com.lcaohoanq.constant.GoogleAuthentication;
import com.lcaohoanq.utils.EnvUtil;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

@Route("oauth2/callback/google")
public class GoogleOAuthCallback extends Composite<Div> {
    public GoogleOAuthCallback() {
        try{
            // Extract authorization code from the URL
            String authorizationCode = getAuthorizationCode();

            // Exchange authorization code for access token
            String accessToken = getAccessToken(authorizationCode);

            // Retrieve user information
            Map<String, Object> userInfo = getUserInfo(accessToken);

            // Handle user information (e.g., store in session, redirect, etc.)
            handleUserLogin(userInfo);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String getAuthorizationCode() {
        return UI.getCurrent().getInternals().getLastHandledLocation().getQueryParameters().getParameters().get("code").get(0);
    }

    private String getAccessToken(String authorizationCode) {
        String clientId = GoogleAuthentication.GOOGLE_CLIENT_ID;
        String clientSecret = GoogleAuthentication.GOOGLE_CLIENT_SECRET;
        String redirectUri = GoogleAuthentication.GOOGLE_REDIRECT_URI;
        String tokenEndpoint =  GoogleAuthentication.GOOGLE_LINK_GET_TOKEN; //https://accounts.google.com/o/oauth2/token
        String grantType =  GoogleAuthentication.GOOGLE_GRANT_TYPE; //authorization_code

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> requestBody = Map.of(
            "code", authorizationCode,
            "client_id", clientId,
            "client_secret", clientSecret,
            "redirect_uri", redirectUri,
            "grant_type", grantType
        );

        Map<String, Object> response = restTemplate.postForObject(tokenEndpoint, requestBody, Map.class);
        return (String) response.get("access_token");
    }

    private Map<String, Object> getUserInfo(String accessToken) {
        String userInfoEndpoint = GoogleAuthentication.GOOGLE_LINK_GET_USER_INFO; //https://www.googleapis.com/oauth2/v1/userinfo?access_token=
        String url = userInfoEndpoint + accessToken;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, Map.class);
    }

    private void handleUserLogin(Map<String, Object> userInfo) {
        String email = (String) userInfo.get("email");
        VaadinSession.getCurrent().setAttribute("user", email);
        UI.getCurrent().getPage().setLocation("http://localhost:3000"); // Redirect to the home page after successful login
    }
}
