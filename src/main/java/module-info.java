module com.example.fuelstationfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.web;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;


    opens com.example.fuelstationfx to javafx.fxml;
    exports com.example.fuelstationfx;
    exports com.example.fuelstationfx.service;
    opens com.example.fuelstationfx.service to javafx.fxml;
    exports com.example.fuelstationfx.model;
    opens com.example.fuelstationfx.model to javafx.fxml;
    exports com.example.fuelstationfx.exception;
    opens com.example.fuelstationfx.exception to javafx.fxml;
}