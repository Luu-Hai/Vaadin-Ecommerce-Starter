package com.lcaohoanq.views.admin;

import com.lcaohoanq.views.employee.EmployeeLoginView;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "admin/login")
public class AdminLoginRedirectView implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.forwardTo(EmployeeLoginView.class);
    }
}