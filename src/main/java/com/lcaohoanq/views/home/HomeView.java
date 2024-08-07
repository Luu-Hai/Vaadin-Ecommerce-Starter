package com.lcaohoanq.views.home;

import com.lcaohoanq.views.MainLayout;
import com.lcaohoanq.views.menu.GameMenuView;
import com.lcaohoanq.views.userslogin.UsersLoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Home")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView() {

        if(VaadinSession.getCurrent().getAttribute("user") != null){
            return;
        }

        setSpacing(false);

        Image img = new Image("images/snake.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("Welcome to Snake Game World");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("It’s a place where bring the greatest experience 🤗"));

        //create a button login with #191819 color and navigate to users/login
        Button button = new Button("Login Now!", e -> UI.getCurrent().navigate(UsersLoginView.class));
        button.getStyle().set("background-color", "#19b719");
        button.getStyle().set("color", "#FFFFFF");
        button.getStyle().set("cursor", "pointer");
        add(button);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
