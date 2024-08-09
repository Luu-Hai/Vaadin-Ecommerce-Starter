package com.lcaohoanq.views.menu;

import com.lcaohoanq.views.MainLayout;
import com.lcaohoanq.views.user.login.UsersLoginView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Snake Game Menu")
@Route(value = "menu", layout = MainLayout.class)
public class GameMenuView extends Composite<VerticalLayout> {
    private VerticalLayout layoutRow = new VerticalLayout();
    private VerticalLayout layoutColumn2 = new VerticalLayout();
    private H3 title = new H3();
    private Button button_Start_Game = new Button("Start Game");
    private Button button_High_Score = new Button("High Score");
    private Button button_Setting = new Button("Setting");
    private Button button_Help = new Button("Help");
    private Button button_Exit = new Button("Exit");

    public GameMenuView() {

        if (VaadinSession.getCurrent().getAttribute("user") == null) {
            UI.getCurrent().navigate(UsersLoginView.class);
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
        title.setText("Game Menu");
        title.setWidth("max-content");

        button_Start_Game.setWidth("min-content");
        button_High_Score.setWidth("min-content");
        button_Setting.setWidth("min-content");
        button_Help.setWidth("min-content");
        button_Exit.setWidth("min-content");

        layoutColumn2.add(title, button_Start_Game, button_High_Score, button_Setting, button_Help, button_Exit);
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
        button_Start_Game.setEnabled(true);
        button_High_Score.setEnabled(true);
        button_Setting.setEnabled(true);
        button_Help.setEnabled(true);
        button_Exit.setEnabled(true);

        // Add action to button
        button_Start_Game.addClickListener(event -> {
            // Start Game
        });

        button_High_Score.addClickListener(event -> {
            // Show High Score
        });

        button_Setting.addClickListener(event -> {
            // Show Setting
        });

        button_Help.addClickListener(event -> {
            // Show Help
        });

        button_Exit.addClickListener(event -> {
            // Exit
        });
    }

}
