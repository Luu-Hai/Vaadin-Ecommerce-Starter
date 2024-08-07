package com.lcaohoanq.views.admin;

import com.lcaohoanq.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Menu Management")
@Route(value = "admin/employee", layout = MainLayout.class)
public class EmployeeManagement extends Composite<VerticalLayout> {

    public EmployeeManagement(){
        getContent().add("Employee Management");
    }

}
