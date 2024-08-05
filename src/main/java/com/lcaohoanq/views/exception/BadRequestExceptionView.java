package com.lcaohoanq.views.exception;

import com.lcaohoanq.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Bad request")
@Route(value = "errors/bad-request", layout = MainLayout.class)
public class BadRequestExceptionView extends AbstractExceptionView {

    public BadRequestExceptionView() {
        super();
        title.setText("Error 400 Bad request");
    }

}
