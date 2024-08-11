package com.lcaohoanq.views.base;

import com.lcaohoanq.enums.UserRoleEnum;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.function.Supplier;

public abstract class LoginPage extends Composite<VerticalLayout> {
    protected VerticalLayout layoutColumn2 = new VerticalLayout();
    protected HorizontalLayout layoutRowBottom = new HorizontalLayout();
    protected LoginForm loginForm = new LoginForm();
    protected LoginI18n i18n = LoginI18n.createDefault(); // Customize LoginForm labels

    // Create Google and Facebook login buttons
    protected Button googleLoginButton;
    protected Button facebookLoginButton;

    public LoginPage(Supplier<Boolean> sessionCheck) {
        if(sessionCheck.get()){
            return;
        }
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);

        initElement();
        stylingElement();
        doAction();
    }

    public abstract void initElement();
    public void stylingElement(){
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
    }
    public abstract void doAction();
}
