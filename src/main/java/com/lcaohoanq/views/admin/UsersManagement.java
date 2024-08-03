package com.lcaohoanq.views.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lcaohoanq.models.User;
import com.lcaohoanq.views.MainLayout;
import com.lcaohoanq.views.exception.InternalServerErrorExceptionView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

@PageTitle("Snake Game Management")
@Route(value = "admin/users", layout = MainLayout.class)
public class UsersManagement extends Composite<VerticalLayout> {

    private Grid<User> userGrid = new Grid<>(User.class);

    private final HttpClient httpClient;
    private ObjectMapper objectMapper;

    public UsersManagement() {
        VerticalLayout layout = getContent();
        layout.add(userGrid);

        // Set size for layout and grid to enable scrolling
        layout.setSizeFull();
        userGrid.setSizeFull();

        // This ensures the grid scrolls when content overflows
        layout.setHeight("100vh");

        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).enable(
            SerializationFeature.INDENT_OUTPUT);

        customizeGridColumns();
        doAction();
    }

    private void doAction() {
        // Listen to the component being attached to the UI
        addAttachListener((AttachEvent event) -> {
            fetchData();
        });
    }

    private void fetchData() {
        UI currentUI = UI.getCurrent();

        new Thread(() -> {
            try {
                // Replace with your API URL
                //https://jsonplaceholder.typicode.com/posts/1
                String apiUrl = "http://localhost:8081/users";
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

                HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    String responseBody = response.body();
                    //Object json = objectMapper.readValue(responseBody, Object.class);
                    List<User> usersList = objectMapper.readValue(responseBody,
                        new TypeReference<List<User>>() {
                        }); //deserializing JSON array to a list of User objects
                    String formattedJson = objectMapper.writeValueAsString(usersList);
                    // Access the UI to update the grid
                    currentUI.access(() -> {
                        userGrid.setItems(usersList);
                        Notification.show("Data fetched successfully!");
                    });
                } else {
                    currentUI.access(() -> {
                        Notification.show("GET request failed: " + response.statusCode());
                    });
                }
            } catch (Exception ex) {
                UI.getCurrent().navigate(InternalServerErrorExceptionView.class);
            }
        }).start();

    }

    private void customizeGridColumns() {
        userGrid.removeAllColumns(); // Remove all existing columns

        // Add specific columns that you want to display
        userGrid.addColumn(User::getId).setHeader("ID");
        userGrid.addColumn(User::getFirstName).setHeader("First Name");
        userGrid.addColumn(User::getEmail).setHeader("Email");
        userGrid.addColumn(User::getPhone).setHeader("Phone");
        userGrid.addColumn(User::getRole).setHeader("Role");
        userGrid.addColumn(User::getStatus).setHeader("Status");
        userGrid.addColumn(User::getSubscription).setHeader("Subscription");
        // Add a custom column for the address
//        userGrid.addColumn(user -> {
//            // Custom logic to handle overflow, e.g., truncate long addresses
//            String address = user.getAddress();
//            return address.length() > 20 ? address.substring(0, 20) + "..." : address;
//        }).setHeader("Address");

        // Add a column for actions (e.g., edit, delete buttons) using ComponentRenderer
        userGrid.addColumn(new ComponentRenderer<>(user -> {
            Button editButton = new Button("Edit", e -> {
                // Handle edit action
                Notification.show("Edit " + user.getFirstName());
            });
            Button deleteButton = new Button("Delete", e -> {
                // Handle delete action
                //Show the dialog to confirm delete?
                Dialog dialog = new Dialog();
                //i want to add a title for this dialog
                dialog.add(new H3("Are you sure you want to delete?"));
                dialog.add(new Button("Confirm", event -> {
                    Notification.show("Delete " + user.getFirstName());
                    dialog.close();
                }), new Button("Cancel", event -> {
                    dialog.close();
                }));
                dialog.open();
            });

            editButton.getStyle().set("cursor", "pointer");
            deleteButton.getStyle().set("background-color", "red");
            deleteButton.getStyle().set("color", "white");
            deleteButton.getStyle().set("cursor", "pointer");

            HorizontalLayout actions = new HorizontalLayout(editButton, deleteButton);
            actions.getStyle().set("margin", "15px");
            return actions;
        })).setHeader("Actions");
    }

}
