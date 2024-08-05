package com.lcaohoanq.views.exception;

import com.lcaohoanq.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Internal Server Error")
@Route(value = "errors", layout = MainLayout.class)
public class InternalServerErrorExceptionView extends AbstractExceptionView {

    public InternalServerErrorExceptionView() {
        super();
        VaadinSession.getCurrent().getAttribute("errors");
        if(VaadinSession.getCurrent().getAttribute("errors") != null) {
            title.setText(VaadinSession.getCurrent().getAttribute("errors").toString());
        } else {
            title.setText("Error 500 Internal server error");
        }
    }

}
