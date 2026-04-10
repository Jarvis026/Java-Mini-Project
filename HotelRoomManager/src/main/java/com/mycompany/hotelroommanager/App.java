package com.mycompany.hotelroommanager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application {

    ArrayList<String> reservations = new ArrayList<>();

    TextArea displayArea = new TextArea();

    @Override
    public void start(Stage stage) {

        TextField guestName = new TextField();
        guestName.setPromptText("Enter Guest Name");

        TextField roomNumber = new TextField();
        roomNumber.setPromptText("Enter Room Number");

        Button reserveRoomBtn = new Button("Reserve Room");
        Button viewBtn = new Button("View Reservations");
        Button checkoutBtn = new Button("Check-Out Guest");

        displayArea.setEditable(false);

        // Reserve Room
        reserveRoomBtn.setOnAction(e -> {
    String name = guestName.getText();
    String room = roomNumber.getText();

    if (name.isEmpty() || room.isEmpty()) {
        displayArea.appendText("Please enter both name and room number\n");
        return;
    }

    boolean roomBooked = false;

    for (String r : reservations) {
        if (r.contains("Room: " + room)) {
            roomBooked = true;
            break;
        }
    }

    if (roomBooked) {
        displayArea.appendText("Room " + room + " is already booked\n");
    } else {
        String record = "Guest: " + name + " | Room: " + room;
        reservations.add(record);
        displayArea.appendText("Room reserved for " + name + "\n");
    }

    guestName.clear();
    roomNumber.clear();
    });

        // View Reservations
        viewBtn.setOnAction(e -> {
            displayArea.appendText("\n--- Current Reservations ---\n");
            if (reservations.isEmpty()) {
                displayArea.appendText("No active reservations\n");
            } else {
                for (String r : reservations) {
                    displayArea.appendText(r + "\n");
                }
            }
        });

        // Check-Out Guest
        checkoutBtn.setOnAction(e -> {
            String name = guestName.getText();

            if (name.isEmpty()) {
                displayArea.appendText("Enter guest name to check out\n");
                return;
            }

            boolean found = false;

            for (int i = 0; i < reservations.size(); i++) {
                if (reservations.get(i).contains(name)) {
                    reservations.remove(i);
                    displayArea.appendText(name + " has checked out\n");
                    found = true;
                    break;
                }
            }

            if (!found) {
                displayArea.appendText("Guest not found\n");
            }

            guestName.clear();
        });

        VBox root = new VBox(10,
                guestName,
                roomNumber,
                reserveRoomBtn,
                viewBtn,
                checkoutBtn,
                displayArea
        );

        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 420, 420);

        stage.setTitle("Hotel Room Reservation System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}