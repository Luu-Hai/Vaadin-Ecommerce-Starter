package com.lcaohoanq.views.utils;

import com.lcaohoanq.enums.UserRoleEnum;
import com.vaadin.flow.component.dialog.Dialog;

public interface ComponentUtils {
    void showSuccessDialog(String message, String buttonMessage, UserRoleEnum userRole);
    void checkTestAccount(String email_phone, String password);
    void handleCloseButton(UserRoleEnum userRole, Dialog successDialog);
}
