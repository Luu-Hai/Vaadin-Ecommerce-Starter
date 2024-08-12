package com.lcaohoanq.views.employee;

import com.lcaohoanq.constant.ApiConstant;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.exception.InternalServerException;
import com.lcaohoanq.utils.ApiUtils;
import com.lcaohoanq.views.base.LoginPage;
import com.lcaohoanq.views.menu.GameMenuView;
import com.lcaohoanq.schemas.UserLoginRequest;
import com.lcaohoanq.views.utils.ComponentUtils;
import com.vaadin.flow.component.UI;
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

@PageTitle("Employee Login")
@Route(value = "employee")
@Slf4j
public class EmployeeLoginView extends LoginPage implements ComponentUtils {

    public EmployeeLoginView() {
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
        i18n.getForm().setTitle("Employee Login");

        layoutColumn2.add(loginForm);

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        getContent().setFlexGrow(1.0, layoutColumn2);
        getContent().add(layoutColumn2);
    }

    @Override
    public void doAction() {
        // Add login listeners
        loginForm.addLoginListener(event -> {
            UserLoginRequest userLoginRequest = new UserLoginRequest(event.getUsername(),
                event.getPassword());
            checkTestAccount(userLoginRequest.getEmail_phone(), userLoginRequest.getPassword());

            try {
                Map<String, Object> payload = Map.of(
                    "email_phone", userLoginRequest.getEmail_phone(),
                    "password", userLoginRequest.getPassword());

                HttpResponse<String> response = ApiUtils.postRequest(
                    ApiConstant.BASE_URL_BE + ApiConstant.API_PATCH + "/employee/login", payload);

                // Handle the response
                switch (response.statusCode()) {
                    case 200:
                        VaadinSession.getCurrent()
                            .setAttribute("user", userLoginRequest.getEmail_phone());
                        VaadinSession.getCurrent().setAttribute("role", UserRoleEnum.EMPLOYEE);
                        showSuccessDialog("Login Successful!", "Close", UserRoleEnum.EMPLOYEE);
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
    }

    @Override
    public void checkTestAccount(String email_phone, String password) {
        if (email_phone.equals("test") && password.equals("employee")) {
            VaadinSession.getCurrent().setAttribute("user", email_phone);
            VaadinSession.getCurrent().setAttribute("role", UserRoleEnum.EMPLOYEE);
            showSuccessDialog("Login Successful!", "Close", UserRoleEnum.EMPLOYEE);
        }
    }
}
