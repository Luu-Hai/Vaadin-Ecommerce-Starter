package com.lcaohoanq.views.exception;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Setter;

@Setter
public abstract class AbstractExceptionView extends Composite<VerticalLayout> {

    protected VerticalLayout verticalLayout = new VerticalLayout();
    protected H1 title = new H1();

    public AbstractExceptionView() {
        title.getStyle().set("color", "red");
        verticalLayout.setAlignItems(VerticalLayout.Alignment.CENTER);
        verticalLayout.setHorizontalComponentAlignment(Alignment.CENTER);
        verticalLayout.setPadding(true);
        verticalLayout.setSpacing(true);

        verticalLayout.add(title);
        getContent().add(verticalLayout);
    }

}
