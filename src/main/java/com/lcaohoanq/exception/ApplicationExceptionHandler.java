package com.lcaohoanq.exception;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(InternalServerException.class)
    @ResponseBody
    @ResponseStatus
    public void handleInternalServerError(InternalServerException e) {
        System.out.println(e.getMessage());
        VaadinSession.getCurrent().setAttribute("errors", e.getMessage());
        UI.getCurrent().getPage().setLocation("http://localhost:8082/errors");
    }

}
