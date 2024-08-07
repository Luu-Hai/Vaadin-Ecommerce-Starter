package com.lcaohoanq.views.policy;

import com.lcaohoanq.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Terms Of Service")
@Route(value = "terms-of-service", layout = MainLayout.class)
public class TermsOfServiceView extends Composite<VerticalLayout> {

    public TermsOfServiceView() {
        getContent().add("Terms of Service");
    }

}
