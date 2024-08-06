package com.lcaohoanq.views.userslogin;

import com.lcaohoanq.exception.InternalServerException;
import com.lcaohoanq.utils.ApiUtils;
import com.lcaohoanq.utils.EnvUtil;
import com.lcaohoanq.views.MainLayout;
import com.lcaohoanq.views.admin.MenuManagement;
import com.lcaohoanq.views.forgotpassword.ForgotPasswordView;
import com.lcaohoanq.views.menu.GameMenuView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

@PageTitle("Login")
@Route(value = "users/login", layout = MainLayout.class)
@Slf4j
public class UsersLoginView extends Composite<VerticalLayout> {

    private VerticalLayout layoutColumn2 = new VerticalLayout();
    private HorizontalLayout layoutRowBottom = new HorizontalLayout();
    private LoginForm loginForm = new LoginForm();
    private LoginI18n i18n = LoginI18n.createDefault(); // Customize LoginForm labels

    // Create Google and Facebook login buttons
    private Button googleLoginButton = new Button("Login with Google");

    private Button facebookLoginButton = new Button("Login with Facebook");

    public UsersLoginView() {
        // Check if the user is already logged in
        if (VaadinSession.getCurrent().getAttribute("user") != null) {
            UI.getCurrent().navigate(GameMenuView.class);
            return;
        }

        initElement();
        doAction();
    }

    private void initElement() {
        // Customize LoginForm labels
        i18n.getForm().setTitle("Login");
        i18n.getForm().setUsername("Email or Phone Number");
        i18n.getForm().setPassword("Password");
        i18n.getForm().setSubmit("Log in");
        i18n.getForm().setForgotPassword("Forgot your password?");
        loginForm.setI18n(i18n);

        layoutColumn2.setWidthFull();
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutColumn2.getStyle().set("height", "80vh");
        layoutColumn2.add(loginForm);

        googleLoginButton.getStyle().set("background-color", "#F1F1F1");
        googleLoginButton.getStyle().set("color", "black");
        facebookLoginButton.getStyle().set("background-color", "#3B5998");
        facebookLoginButton.getStyle().set("color", "white");

        layoutRowBottom.setWidthFull();
        layoutRowBottom.setWidth("100%");
        layoutRowBottom.setMaxWidth("800px");
        layoutRowBottom.setHeight("min-content");
        layoutRowBottom.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutRowBottom.setAlignItems(Alignment.CENTER);

        // Add the buttons to the layout
        layoutRowBottom.add(googleLoginButton, facebookLoginButton);
        layoutColumn2.add(layoutRowBottom);

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        getContent().setFlexGrow(1.0, layoutColumn2);
        getContent().add(layoutColumn2);
    }

    private void doAction() {
        // Add login listeners
        loginForm.addLoginListener(event -> {
            UserLoginRequest userLoginRequest = new UserLoginRequest(event.getUsername(), event.getPassword());
            checkTestAccount(userLoginRequest.getEmail_phone(), userLoginRequest.getPassword());

            try {
                Map<String, Object> payload = Map.of(
                    "email_phone", userLoginRequest.getEmail_phone(),
                    "password", userLoginRequest.getPassword());

                HttpResponse<String> response = ApiUtils.postRequest(
                    "http://localhost:8081/users/login", payload);

                // Handle the response
                switch (response.statusCode()) {
                    case 200:
                        Dialog successDialog = new Dialog();

                        Button closeButton = new Button("Close",
                            e -> handleCloseButton(successDialog));
                        closeButton.getStyle().set("background-color", "lightblue");
                        closeButton.getStyle().set("align-items", "center");

                        successDialog.add(new H3("Login Successful!"), new Div(closeButton));
                        successDialog.open();

                        log.info("Login successful");

                        VaadinSession.getCurrent().setAttribute("user", userLoginRequest.getEmail_phone());
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

    private void checkTestAccount(String email_phone, String password) {
        if (email_phone.equals("admin") && password.equals("admin")) {
            VaadinSession.getCurrent().setAttribute("isAdminLogin", email_phone);
            UI.getCurrent().navigate(MenuManagement.class);
        } else if (email_phone.equals("hoang") && password.equals("1")) {
            VaadinSession.getCurrent().setAttribute("user", email_phone);
            UI.getCurrent().navigate(GameMenuView.class);
        }
    }

    private void handleCloseButton(Dialog successDialog) {
        successDialog.close();
        UI.getCurrent().navigate(GameMenuView.class);
    }
}
