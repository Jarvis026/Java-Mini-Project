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
        Button checkoutBtn = new Button("Check-Out (by Room)");
        Button availableBtn = new Button("Show Available Rooms");
        Button clearBtn = new Button("Clear Display");

        displayArea.setEditable(false);

        // 🔹 Reserve Room
        reserveRoomBtn.setOnAction(e -> {
            String name = guestName.getText();
            String room = roomNumber.getText();

            if (name.isEmpty() || room.isEmpty()) {
                displayArea.appendText("Please enter both name and room number\n");
                return;
            }

            // Validate room number (only digits)
            if (!room.matches("\\d+")) {
                displayArea.appendText("Enter valid room number\n");
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

        // 🔹 View Reservations
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

        // 🔹 Check-Out by Room Number
        checkoutBtn.setOnAction(e -> {
            String room = roomNumber.getText();

            if (room.isEmpty()) {
                displayArea.appendText("Enter room number to check out\n");
                return;
            }

            boolean found = false;

            for (int i = 0; i < reservations.size(); i++) {
                if (reservations.get(i).contains("Room: " + room)) {
                    reservations.remove(i);
                    displayArea.appendText("Room " + room + " has been checked out\n");
                    found = true;
                    break;
                }
            }

            if (!found) {
                displayArea.appendText("Room not found\n");
            }

            roomNumber.clear();
        });

        // 🔹 Show Available Rooms (101–105)
        availableBtn.setOnAction(e -> {
            displayArea.appendText("\n--- Available Rooms ---\n");

            for (int i = 101; i <= 105; i++) {
                boolean booked = false;

                for (String r : reservations) {
                    if (r.contains("Room: " + i)) {
                        booked = true;
                        break;
                    }
                }

                if (!booked) {
                    displayArea.appendText("Room " + i + "\n");
                }
            }
        });

        // 🔹 Clear Display
        clearBtn.setOnAction(e -> {
            displayArea.clear();
        });

        VBox root = new VBox(10,
                guestName,
                roomNumber,
                reserveRoomBtn,
                viewBtn,
                checkoutBtn,
                availableBtn,
                clearBtn,
                displayArea
        );

        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 450, 500);

        stage.setTitle("Hotel Room Reservation System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}