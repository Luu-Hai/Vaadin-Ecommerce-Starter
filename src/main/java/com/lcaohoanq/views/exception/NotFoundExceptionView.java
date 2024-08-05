package com.lcaohoanq.views.exception;

import com.lcaohoanq.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Not found")
@Route(value = "errors/not-found", layout = MainLayout.class)
public class NotFoundExceptionView extends AbstractExceptionView {
    public NotFoundExceptionView() {
        super();
        title.setText("Error 404 Not found");
    }

}
