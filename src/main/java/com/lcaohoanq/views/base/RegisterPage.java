package com.lcaohoanq.views.base;

import com.lcaohoanq.schemas.RegisterRequest;
import com.lcaohoanq.schemas.UserRegisterRequest;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Map;

public abstract class RegisterPage<T extends RegisterRequest> extends Composite<VerticalLayout> {

    protected H3 title = new H3();
    protected TextField textField_Email_Phone = new TextField("Email or Phone Number");
    protected TextField textField_First_Name = new TextField("First Name");
    protected TextField textField_Last_Name = new TextField("Last Name");
    protected PasswordField textField_Password = new PasswordField("Password");
    protected TextField textField_Address = new TextField("Address");
    protected DatePicker datePicker_Birthday = new DatePicker("Birthday");

    //chose gender: male, female, others
    protected ComboBox<String> select_G = new ComboBox<>("Gender");


    protected PasswordField textField_Confirmed_Password = new PasswordField("Confirmed password");
    protected FormLayout formLayout2Col = new FormLayout();
    protected HorizontalLayout layoutRow = new HorizontalLayout();
    protected VerticalLayout buttonLayout = new VerticalLayout();
    protected HorizontalLayout termAndPolicy = new HorizontalLayout();
    protected VerticalLayout layoutColumn2 = new VerticalLayout();
    protected Anchor link_Terms = new Anchor("http://localhost:3000/terms-of-service", "Terms of Service");
    protected Anchor link_Policy = new Anchor("http://localhost:3000/privacy-policy", "Privacy Policy");
    protected Checkbox checkbox = new Checkbox();
    protected Button button_Save = new Button("Save");


    public RegisterPage(){
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        initComponent();
        doAction();
    }

    public abstract void initComponent();
    public abstract void doAction();
    public abstract Map<String, Object> fetchData(T user, Map<String, Object> payload);

}
