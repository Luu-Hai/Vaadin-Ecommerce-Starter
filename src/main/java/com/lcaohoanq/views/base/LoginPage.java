package com.lcaohoanq.views.base;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
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
        doAction();
    }

    public abstract void initElement();
    public abstract void doAction();
    public abstract void checkTestAccount(String email_phone, String password);

}
