package com.lcaohoanq.utils;

import com.lcaohoanq.constant.Regex;
import com.lcaohoanq.schemas.RegisterRequest;
import com.lcaohoanq.views.base.RegisterPage;
import java.time.LocalDateTime;

public interface ValidationUtils {

    default <T extends RegisterRequest> void validateFields(RegisterPage<T> page, String fieldName) {
        String emailPhone = page.getTextField_Email_Phone().getValue();
        String firstName = page.getTextField_First_Name().getValue();
        String lastName = page.getTextField_Last_Name().getValue();
        String password = page.getTextField_Password().getValue();
        String address = page.getTextField_Address().getValue();
        LocalDateTime birthday =
            page.getDatePicker_Birthday().getValue() != null ? page.getDatePicker_Birthday().getValue().atStartOfDay()
                : null;
        String gender = page.getSelect_G().getValue();
        String confirmedPassword = page.getTextField_Confirmed_Password().getValue();

        switch (fieldName) {
            case "emailPhone":
                if (page.getTextField_Email_Phone().isEmpty()) {
                    page.getTextField_Email_Phone().setErrorMessage("Email or Phone Number is required");
                    page.getTextField_Email_Phone().setInvalid(true);
                } else {
                    if (ValidateUtils.checkTypeAccount(emailPhone)) {
                        if (!emailPhone.matches(Regex.USER_EMAIL)) {
                            page.getTextField_Email_Phone().setErrorMessage("Invalid email format");
                            page.getTextField_Email_Phone().setInvalid(true);
                        } else {
                            page.getTextField_Email_Phone().setInvalid(false);
                        }
                    } else {
                        if (!emailPhone.matches(Regex.USER_PHONE)) {
                            page.getTextField_Email_Phone().setErrorMessage("Invalid phone number format");
                            page.getTextField_Email_Phone().setInvalid(true);
                        } else {
                            page.getTextField_Email_Phone().setInvalid(false);
                        }
                    }
                }
                break;
            case "firstName":
                page.getTextField_First_Name().setInvalid(false);
                if (firstName.isEmpty()) {
                    page.getTextField_First_Name().setErrorMessage("First name is required");
                    page.getTextField_First_Name().setInvalid(true);
                }
                break;
            case "lastName":
                page.getTextField_Last_Name().setInvalid(false);
                if (lastName.isEmpty()) {
                    page.getTextField_Last_Name().setErrorMessage("Last name is required");
                    page.getTextField_Last_Name().setInvalid(true);
                }
                break;
            case "password":
                page.getTextField_Password().setInvalid(false);
                if (password.isEmpty()) {
                    page.getTextField_Password().setErrorMessage("Password is required");
                    page.getTextField_Password().setInvalid(true);
                }
                break;
            case "confirmedPassword":
                page.getTextField_Confirmed_Password().setInvalid(false);
                if (confirmedPassword.isEmpty()) {
                    page.getTextField_Confirmed_Password().setErrorMessage("Confirmed password is required");
                    page.getTextField_Confirmed_Password().setInvalid(true);
                } else if (!confirmedPassword.equals(password)) {
                    page.getTextField_Confirmed_Password().setErrorMessage(
                        "Confirmed password does not match");
                    page.getTextField_Confirmed_Password().setInvalid(true);
                }
                break;
            case "address":
                page.getTextField_Address().setInvalid(false);
                if (address.isEmpty()) {
                    page.getTextField_Address().setErrorMessage("Address is required");
                    page.getTextField_Address().setInvalid(true);
                }
                break;
            case "birthday":
                if (birthday == null) {
                    page.getDatePicker_Birthday().setInvalid(true);
                    page.getDatePicker_Birthday().setErrorMessage("Birthday is required");
                } else {
                    page.getDatePicker_Birthday().setInvalid(false);
                }
                break;
            case "gender":
                page.getSelect_G().setInvalid(false);
                break;
        }
    }

}
