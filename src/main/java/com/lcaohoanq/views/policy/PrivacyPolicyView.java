package com.lcaohoanq.views.policy;

import com.lcaohoanq.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Privacy Policy")
@Route(value = "privacy-policy", layout = MainLayout.class)
public class PrivacyPolicyView extends Composite<VerticalLayout> {

    public PrivacyPolicyView() {
        getContent().add("Privacy Policy content");
    }
}
