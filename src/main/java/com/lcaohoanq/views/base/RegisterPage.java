package com.lcaohoanq.views.base;

import com.lcaohoanq.schemas.RegisterRequest;
import com.lcaohoanq.schemas.UserRegisterRequest;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.util.Map;
import lombok.Getter;

@Getter
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
        stylingComponent();
        initComponent();
        doAction();
    }
    public void stylingComponent(){
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);

        title.setWidth("min-content");
        textField_Email_Phone.setWidth("100%");
        textField_First_Name.setWidth("min-content");
        textField_Last_Name.setWidth("min-content");
        textField_Password.setWidth("min-content");
        textField_Confirmed_Password.setWidth("min-content");

        textField_Address.setWidth("100%");
        datePicker_Birthday.setWidth("100%");
        select_G.setWidth("100%");
        select_G.setItems("Male", "Female", "Other", "Not provide"); //MALE
        select_G.setValue("Not provide"); //default value

        layoutRow.setWidthFull();
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");

        layoutColumn2.setWidthFull();
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutColumn2.getStyle().set("height", "80vh");

        button_Save.setWidth("100%");
        button_Save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button_Save.getStyle().set("cursor", "pointer");

        formLayout2Col.setWidth("100%");

        checkbox.setLabel("I accept the terms and conditions");

        buttonLayout.setWidthFull();
        buttonLayout.setPadding(false);
        buttonLayout.setSpacing(true);
        buttonLayout.setAlignItems(Alignment.CENTER);
        getContent().setFlexGrow(1.0, layoutColumn2);
    }

    public void initComponent(){
        formLayout2Col.add(textField_First_Name);
        formLayout2Col.add(textField_Last_Name);
        formLayout2Col.add(textField_Address);
        formLayout2Col.add(datePicker_Birthday);
        formLayout2Col.add(textField_Password);
        formLayout2Col.add(textField_Confirmed_Password);
        formLayout2Col.add(select_G);

        layoutColumn2.add(title);
        layoutColumn2.add(textField_Email_Phone);
        layoutColumn2.add(formLayout2Col);
        layoutColumn2.add(termAndPolicy);
        layoutColumn2.add(layoutRow);
        getContent().add(layoutColumn2);

        buttonLayout.add(link_Terms, button_Save);

        termAndPolicy.setWidthFull();
        termAndPolicy.setPadding(false);
        termAndPolicy.setSpacing(true);
        termAndPolicy.setAlignItems(Alignment.START);
        termAndPolicy.setJustifyContentMode(JustifyContentMode.START);
        termAndPolicy.add(checkbox, link_Terms, link_Policy);

        layoutRow.add(buttonLayout);
    }

    public abstract void doAction();
    public abstract Map<String, Object> fetchData(T user, Map<String, Object> payload);

}
