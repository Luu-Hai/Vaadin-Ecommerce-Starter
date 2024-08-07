package com.lcaohoanq.views.forgotpassword;

import com.lcaohoanq.constant.ApiConstant;
import com.lcaohoanq.models.User;
import com.lcaohoanq.utils.ApiUtils;
import com.lcaohoanq.utils.ValidateUtils;
import com.lcaohoanq.views.MainLayout;
import com.lcaohoanq.views.resetpassword.ResetPasswordView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import java.io.IOException;
import java.net.http.HttpResponse;
import lombok.Getter;
import lombok.Setter;

@PageTitle("Forgot Password")
@Route(value = "forgot-password", layout = MainLayout.class)
@Getter
@Setter
public class ForgotPasswordView extends Composite<VerticalLayout> {

    private TextField textField_Email_Phone = new TextField("Email or Phone Number");
    private Button button_Send = new Button("Send");
    private VerticalLayout layoutRow = new VerticalLayout();
    private VerticalLayout layoutColumn2 = new VerticalLayout();
    private User user = new User();

    public ForgotPasswordView() {
        // Check if the user is already logged in
        if (VaadinSession.getCurrent().getAttribute("user") != null) {
            return;
        }

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        initComponent();
        doAction();
    }

    private void initComponent(){

        textField_Email_Phone.setWidth("30%");
        button_Send.setWidth("30%");
        button_Send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button_Send.getStyle().set("cursor", "pointer");

        layoutColumn2.add(textField_Email_Phone, button_Send);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.getStyle().set("height", "80vh");
        layoutColumn2.setPadding(true);
        layoutColumn2.setSpacing(true);

        layoutRow.add(layoutColumn2);
        layoutRow.setAlignItems(Alignment.START);
        layoutRow.setJustifyContentMode(JustifyContentMode.START);
        layoutRow.setPadding(true);
        layoutRow.setSpacing(true);
        layoutRow.getStyle().set("height", "80vh");

        getContent().add(layoutRow);
    }

    private void doAction(){
        button_Send.addClickListener(event -> {
            if (textField_Email_Phone.isEmpty()) {
                Notification.show("Email or Phone Number is required");
            } else {
                String url = ApiConstant.BASE_URL_BE + "/forgot-password?email_phone=" + textField_Email_Phone.getValue();

                if(ValidateUtils.checkTypeAccount(textField_Email_Phone.getValue())){
                    user.setEmail(textField_Email_Phone.getValue());
                } else{
                    user.setPhone(textField_Email_Phone.getValue());
                }

                try {
                    HttpResponse<String> response = ApiUtils.getRequest(url);

                    System.out.println(response.body());

                    String otpExtractFromResponse = response.body().substring(38,44);

                    switch (response.statusCode()) {
                        case 200:
                            Notification.show("An email has been sent to your email address. Please check your email to reset your password.");
                            VaadinSession.getCurrent().setAttribute("userRequiredForgotPassword", user);
                            showOtpDialog(otpExtractFromResponse);
                            break;
                        case 400:
                            Notification.show("Email or Phone Number not found");
                            break;
                        default:
                            Notification.show("Failed to send email");
                            break;
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void showOtpDialog(String extractOtp) {
        Dialog otpDialog = new Dialog();
        otpDialog.setModal(true);
        otpDialog.setDraggable(false);
        otpDialog.setResizable(false);

        VerticalLayout dialogLayout = new VerticalLayout();
        TextField otpField = new TextField("Enter OTP");
        Button submitButton = new Button("Submit");

        submitButton.addClickListener(e -> {
            String otp = otpField.getValue();
            if(otp.equals(extractOtp)){
                Notification.show("OTP Submitted Successfully");
                otpDialog.close();
                UI.getCurrent().navigate(ResetPasswordView.class);
            } else {
                Notification.show("Invalid OTP");
            }
            otpDialog.close();
        });

        dialogLayout.add(otpField, submitButton);
        otpDialog.add(dialogLayout);
        otpDialog.open();
    }

}
