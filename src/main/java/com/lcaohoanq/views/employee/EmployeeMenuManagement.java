package com.lcaohoanq.views.employee;

import com.lcaohoanq.constant.ApiConstant;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.views.MainLayout;
import com.lcaohoanq.views.base.LoginPage;
import com.lcaohoanq.views.utils.ComponentUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Menu Management")
@Route(value = "employee/menu", layout = MainLayout.class)
public class EmployeeMenuManagement extends LoginPage implements ComponentUtils {

    //create a button for show all users data, show all score data, click to do corresponding action\

    private final Button button_Show_All_Scores = new Button("Show All Scores");
    private final Button button_Show_Data_Grid = new Button("Show Data Grid");
    private final VerticalLayout layoutRow = new VerticalLayout();
    private final VerticalLayout layoutColumn2 = new VerticalLayout();
    private final H3 title = new H3();

    public EmployeeMenuManagement() {
        super(() -> {
            // Check if the user is already logged in
            if (UI.getCurrent().getSession().getAttribute("user") == null) {
                UI.getCurrent().getPage().setLocation(ApiConstant.BASE_URL_FE + "users/login");
                return true;
            }
            return false;
        });
    }

    @Override
    public void initElement(){
        title.setText("Management Menu");
        title.setWidth("max-content");

        button_Show_All_Scores.setWidth("min-content");
        button_Show_Data_Grid.setWidth("min-content");

        layoutColumn2.add(title, button_Show_All_Scores, button_Show_Data_Grid);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setPadding(true);
        layoutColumn2.setSpacing(true);

        layoutRow.add(layoutColumn2);
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.START);
        layoutRow.setPadding(true);
        layoutRow.setSpacing(true);

        getContent().add(layoutRow);
    }

    @Override
    public void doAction(){
        // Initially disable the button

        button_Show_All_Scores.setEnabled(true);

        button_Show_All_Scores.addClickListener(event -> {
            // Show High Score
        });

        button_Show_Data_Grid.addClickListener(event -> {
            //UI.getCurrent().getPage().setLocation("data-grid"); //premium component
        });
    }

    @Override
    public void checkTestAccount(String email_phone, String password) {
        if (email_phone.equals("hoang") && password.equals("1")) {
            VaadinSession.getCurrent().setAttribute("user", email_phone);
            VaadinSession.getCurrent().setAttribute("role", UserRoleEnum.EMPLOYEE);
            showSuccessDialog("Login Successful!", "Close",UserRoleEnum.EMPLOYEE);
        }
    }
}
