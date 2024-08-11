package com.lcaohoanq.views.admin;

import com.lcaohoanq.constant.ApiConstant;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.exception.InternalServerException;
import com.lcaohoanq.utils.ApiUtils;
import com.lcaohoanq.views.base.LoginPage;
import com.lcaohoanq.views.menu.GameMenuView;
import com.lcaohoanq.schemas.UserLoginRequest;
import com.lcaohoanq.views.utils.ComponentUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@PageTitle("Admin Login")
@Route(value = "admin")
@Slf4j
public class AdminLoginView extends LoginPage implements ComponentUtils {

    public AdminLoginView() {
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
        i18n.getForm().setTitle("Admin Login");

        layoutColumn2.add(loginForm);

        getContent().setFlexGrow(1.0, layoutColumn2);
        getContent().add(layoutColumn2);
    }

    @Override
    public void doAction() {
        // Add login listeners
        loginForm.addLoginListener(event -> {
            UserLoginRequest userLoginRequest = new UserLoginRequest(event.getUsername(), event.getPassword());
            checkTestAccount(userLoginRequest.getEmail_phone(), userLoginRequest.getPassword());

            try {
                Map<String, Object> payload = Map.of(
                    "email_phone", userLoginRequest.getEmail_phone(),
                    "password", userLoginRequest.getPassword());

                HttpResponse<String> response = ApiUtils.postRequest(
                    ApiConstant.BASE_URL_BE + ApiConstant.API_PATCH + "admin", payload);

                // Handle the response
                switch (response.statusCode()) {
                    case 200:
                        VaadinSession.getCurrent().setAttribute("user", userLoginRequest.getEmail_phone());
                        VaadinSession.getCurrent().setAttribute("role", UserRoleEnum.ADMIN);
                        showSuccessDialog("Login Successful!", "Close", UserRoleEnum.ADMIN);
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

        loginForm.addForgotPasswordListener(event -> {
            Notification.show("Do not have permission to perform this action", 3000, Notification.Position.MIDDLE);
        });
    }

    @Override
    public void checkTestAccount(String email_phone, String password) {
        if (email_phone.equals("admin") && password.equals("admin")) {
            VaadinSession.getCurrent().setAttribute("user", email_phone);
            VaadinSession.getCurrent().setAttribute("role", UserRoleEnum.ADMIN);
            showSuccessDialog("Login Successful!", "Close", UserRoleEnum.ADMIN);
        }
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
            case ADMIN:
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "/admin/menu");
                break;
            default:
                break;
        }
    }
}
