package com.example.fuelstationfx.service;

import javafx.scene.control.Label;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;


public class InvoiceErrorHandler {

    public static void handleHttpClientErrorException(Label label, HttpClientErrorException e, int customerId) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            UIUpdateService.updateStatusLabel(label, "Customer with id: " + customerId + " cannot be found", "red");
        }
        e.printStackTrace();
    }

    public static void handleRestClientException(Label label, RestClientException e) {
        UIUpdateService.updateStatusLabel(label,"Error occurred during invoice generation", "red");
        e.printStackTrace();
    }
}
