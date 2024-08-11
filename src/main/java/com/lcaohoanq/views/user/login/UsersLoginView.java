package com.lcaohoanq.views.user.login;

import com.lcaohoanq.constant.ApiConstant;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.exception.InternalServerException;
import com.lcaohoanq.utils.ApiUtils;
import com.lcaohoanq.utils.EnvUtil;
import com.lcaohoanq.views.MainLayout;
import com.lcaohoanq.views.base.LoginPage;
import com.lcaohoanq.views.forgotpassword.ForgotPasswordView;
import com.lcaohoanq.views.menu.GameMenuView;
import com.lcaohoanq.schemas.UserLoginRequest;
import com.lcaohoanq.views.utils.ComponentUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@PageTitle("Login")
@Route(value = "users/login", layout = MainLayout.class)
@Slf4j
public class UsersLoginView extends LoginPage implements ComponentUtils {

    public UsersLoginView() {
        super(() -> {
            // Check if the user is already logged in
            if (VaadinSession.getCurrent().getAttribute("user") != null) {
                UI.getCurrent().navigate(GameMenuView.class);
                return true;
            }
            return false;
        });
    }

    @Override
    public void initElement() {
        // Customize LoginForm labels
        i18n.getForm().setTitle("Login");

        layoutColumn2.add(loginForm);

        Image googleLogo = new Image("icons/icons8-google.svg", "");
        googleLogo.setHeight("16px");
        googleLogo.setWidth("16px");
        googleLoginButton = new Button("Login with Google", googleLogo);
        googleLoginButton.getStyle().set("background-color", "#F1F1F1");
        googleLoginButton.getStyle().set("color", "black");
        googleLoginButton.getStyle().set("cursor", "pointer");

        Image facebookLogo = new Image("icons/icons8-facebook.svg", "");
        facebookLogo.setHeight("16px");
        facebookLogo.setWidth("16px");
        facebookLoginButton = new Button("Login with Facebook", facebookLogo);
        facebookLoginButton.getStyle().set("background-color", "#3479ea");
        facebookLoginButton.getStyle().set("color", "white");
        facebookLoginButton.getStyle().set("cursor", "pointer");

        layoutRowBottom.setWidthFull();
        layoutRowBottom.setWidth("100%");
        layoutRowBottom.setMaxWidth("800px");
        layoutRowBottom.setHeight("min-content");
        layoutRowBottom.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutRowBottom.setAlignItems(Alignment.CENTER);

        // Add the buttons to the layout
        layoutRowBottom.add(googleLoginButton, facebookLoginButton);
        layoutColumn2.add(layoutRowBottom);
        getContent().setFlexGrow(1.0, layoutColumn2);
        getContent().add(layoutColumn2);
    }

    @Override
    public void doAction() {
        // Add login listeners
        loginForm.addLoginListener(event -> {
            UserLoginRequest userLoginRequest = new UserLoginRequest(event.getUsername(),
                event.getPassword());
            try {
                Map<String, Object> payload = Map.of(
                    "email_phone", userLoginRequest.getEmail_phone(),
                    "password", userLoginRequest.getPassword());

                HttpResponse<String> response = ApiUtils.postRequest(
                    ApiConstant.BASE_URL_BE + ApiConstant.API_PATCH + "/users/login", payload);

                // Handle the response
                switch (response.statusCode()) {
                    case 200:
                        VaadinSession.getCurrent()
                            .setAttribute("user", userLoginRequest.getEmail_phone());
                        VaadinSession.getCurrent().setAttribute("role", UserRoleEnum.USER);
                        showSuccessDialog("Login Successful!", "Close", UserRoleEnum.USER);
                        log.info("Login successful");
                        break;
                    case 400:
                        //Notification.show("Login failed", 3000, Notification.Position.MIDDLE);
                        loginForm.setError(true);
                        break;
                    case 408:
                        Notification.show("Request Time Out", 3000, Notification.Position.MIDDLE);
                        break;
                    default:
                        throw new InternalServerException("Error 500 Internal server error");
                }

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
        });

        // Add forgot password listener
        loginForm.addForgotPasswordListener(event -> {
            UI.getCurrent().navigate(ForgotPasswordView.class);
        });

        googleLoginButton.addClickListener(e -> handleGoogleLogin());

        facebookLoginButton.addClickListener(e -> handleFacebookLogin());
    }

    private void handleGoogleLogin() {
        try {
            String googleAuthUrl = EnvUtil.get("GOOGLE_AUTH_URL");
            UI.getCurrent().getPage().setLocation(googleAuthUrl);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleFacebookLogin() {
        // Redirect to Facebook authentication endpoint
    }

    @Override
    public void checkTestAccount(String email_phone, String password) {
    }

    @Override
    public void showSuccessDialog(String dialogMessage, String buttonMessage, UserRoleEnum userRole) {
        Dialog successDialog = new Dialog();
        Button closeButton = new Button(buttonMessage, e -> handleCloseButton(userRole, successDialog));
        closeButton.getStyle().set("background-color", "lightblue");
        closeButton.getStyle().set("align-items", "center");
        successDialog.add(new H3(dialogMessage), new Div(closeButton));
        successDialog.open();
    }

    @Override
    public void handleCloseButton(UserRoleEnum userRole, Dialog successDialog) {
        successDialog.close();
        switch (userRole) {
            case USER:
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "/menu");
                break;
            case USER_GOLD:
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "/menu/gold");
                break;
            case USER_PREMIUM:
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "/menu/premium");
                break;
            default:
                break;
        }
    }
}
