package com.lcaohoanq.views.exception;

import com.lcaohoanq.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Not found")
@Route(value = "errors/not-found", layout = MainLayout.class)
public class NotFoundExceptionView extends Composite<VerticalLayout> {

    public NotFoundExceptionView() {
        getContent().add("Error 404 Not found");
    }

}
