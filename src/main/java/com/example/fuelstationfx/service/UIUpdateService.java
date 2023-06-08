package com.example.fuelstationfx.service;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class UIUpdateService {
    public static void updateStatusLabel(Label statusLabel, String text, String color) {
        Platform.runLater(() -> {
            statusLabel.setText(text);
            statusLabel.setStyle("-fx-text-fill: " + color + ";");
        });
    }

}
