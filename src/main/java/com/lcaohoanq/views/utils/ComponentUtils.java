package com.lcaohoanq.views.utils;

import com.lcaohoanq.constant.ApiConstant;
import com.lcaohoanq.enums.UserRoleEnum;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;

public interface ComponentUtils {
    void checkTestAccount(String email_phone, String password);
    default void showSuccessDialog(String dialogMessage, String buttonMessage, UserRoleEnum userRole){
        Dialog successDialog = new Dialog();
        Button closeButton = new Button(buttonMessage, e -> handleCloseButton(userRole, successDialog));
        closeButton.getStyle().set("background-color", "lightblue");
        closeButton.getStyle().set("align-items", "center");
        successDialog.add(new H3(dialogMessage), new Div(closeButton));
        successDialog.open();
    }

    default void showFailDialog(String dialogMessage, String buttonMessage){
        Dialog failDialog = new Dialog();
        Button closeButton = new Button(buttonMessage, e -> failDialog.close());
        closeButton.getStyle().set("color", "white");
        closeButton.getStyle().set("background-color", "#FF0e0e");
        closeButton.getStyle().set("align-items", "center");
        failDialog.add(new H3(dialogMessage), new Div(closeButton));
        failDialog.open();
    }

    default void handleCloseButton(UserRoleEnum userRole, Dialog successDialog) {
        successDialog.close();
        switch (userRole) {
            case ADMIN:
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "/admin/menu");
                break;
            case EMPLOYEE:
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "/employee/menu");
                break;
            case USER:
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "/menu");
                break;
            case USER_GOLD:
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "/menu/gold");
                break;
            case USER_PREMIUM:
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "/menu/premium");
            default:
                break;
        }
    }
}
