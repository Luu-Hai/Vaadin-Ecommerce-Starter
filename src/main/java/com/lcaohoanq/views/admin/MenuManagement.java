package com.lcaohoanq.views.admin;

import com.lcaohoanq.views.MainLayout;
import com.lcaohoanq.views.userslogin.UsersLoginView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Menu Management")
@Route(value = "admin", layout = MainLayout.class)
public class MenuManagement extends Composite<VerticalLayout> {

    //create a button for show all users data, show all score data, click to do corresponding action\

    private Button button_Show_All_Users = new Button("Show All Users");
    private Button button_Show_All_Scores = new Button("Show All Scores");
    private Button button_Show_Data_Grid = new Button("Show Data Grid");
    private Button button_Show_Grid_With_Filter = new Button("Show Grid With Filter");
    private Button button_Show_Master_Detail = new Button("Show Master Detail");
    private VerticalLayout layoutRow = new VerticalLayout();
    private VerticalLayout layoutColumn2 = new VerticalLayout();
    private H3 title = new H3();

    public MenuManagement() {
        if(UI.getCurrent().getSession().getAttribute("isAdminLogin") == null){
            UI.getCurrent().navigate(UsersLoginView.class);
            return;
        }else{

        }

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        initComponent();
        doAction();
    }

    private void initComponent(){
        title.setText("Management Menu");
        title.setWidth("max-content");

        button_Show_All_Users.setWidth("min-content");
        button_Show_All_Scores.setWidth("min-content");
        button_Show_Data_Grid.setWidth("min-content");
        button_Show_Grid_With_Filter.setWidth("min-content");
        button_Show_Master_Detail.setWidth("min-content");

        layoutColumn2.add(title, button_Show_All_Users, button_Show_All_Scores, button_Show_Data_Grid, button_Show_Grid_With_Filter, button_Show_Master_Detail);
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

    private void doAction(){
        // Initially disable the button

        button_Show_All_Users.setEnabled(true);
        button_Show_All_Scores.setEnabled(true);

        // Add action to button
        button_Show_All_Users.addClickListener(event -> {
            UI.getCurrent().navigate(UsersManagement.class);
        });

        button_Show_All_Scores.addClickListener(event -> {
            // Show High Score
        });

        button_Show_Data_Grid.addClickListener(event -> {
            //UI.getCurrent().getPage().setLocation("data-grid"); //premium component
        });

        button_Show_Grid_With_Filter.addClickListener(event -> {
            UI.getCurrent().getPage().setLocation("grid-with-filters");
        });

        button_Show_Master_Detail.addClickListener(event -> {
            UI.getCurrent().getPage().setLocation("master-detail");
        });

    }

}
