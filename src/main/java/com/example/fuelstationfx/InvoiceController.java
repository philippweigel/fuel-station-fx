package com.example.fuelstationfx;

import com.example.fuelstationfx.model.Invoice;
import com.example.fuelstationfx.service.InvoiceErrorHandler;
import com.example.fuelstationfx.service.InvoiceService;
import com.example.fuelstationfx.service.UIUpdateService;
import com.example.fuelstationfx.util.FileUtility;
import com.example.fuelstationfx.util.InvoiceScheduler;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.TimeUnit;
import javafx.scene.control.Hyperlink;

public class InvoiceController {
    @FXML
    private TextField customerIdField;

    @FXML
    private Button generateInvoiceButton;

    @FXML
    private Label statusLabel;

    @FXML
    private Hyperlink downloadLink;

    private final InvoiceService invoiceService = new InvoiceService();

    @FXML
    private void initialize() {
        setupUI();
        setupScheduler();
    }
    private void setupUI() {
        generateInvoiceButton.setOnAction(event -> generateInvoice());
        downloadLink.setVisible(false);
    }
    private void setupScheduler() {
        InvoiceScheduler.scheduleAtFixedRate(this::updateInvoiceStatus, 0, 5, TimeUnit.SECONDS);
    }

    private void updateInvoiceStatus() {
        String customerIdText = customerIdField.getText();
        if (customerIdText.isEmpty()) {
            return;
        }

        int customerId;
        try {
            customerId = Integer.parseInt(customerIdText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        try {
            Invoice invoice = invoiceService.fetchInvoice(customerId);

            String downloadLinkUrl = invoice.getDownloadLink();
            String creationTime = invoice.getCreationTime();

            Platform.runLater(() -> {
                UIUpdateService.updateStatusLabel(statusLabel,"Invoice available for download, created at: " + creationTime, "green");
                downloadLink.setText("Download Invoice");
                downloadLink.setVisible(true);
                downloadLink.setOnAction(e -> {
                    try {
                        FileUtility.openLocalFile(downloadLinkUrl);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            });
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handle errors when calling the server
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                UIUpdateService.updateStatusLabel(statusLabel,"Customer with id: " + customerId + " cannot be found", "red");
            }
        } catch (JsonProcessingException e) {
            // Handle errors when processing the JSON
            e.printStackTrace();
        } catch (RestClientException e) {
            // Handle other errors from the RestTemplate
            e.printStackTrace();
        }
    }

    private void generateInvoice() {
        downloadLink.setVisible(false);
        String customerIdText = customerIdField.getText();
        if (customerIdText.isEmpty()) {
            UIUpdateService.updateStatusLabel(statusLabel,"Please enter a Customer ID", "red");
            return;
        }

        int customerId;
        try {
            customerId = Integer.parseInt(customerIdText);
        } catch (NumberFormatException e) {
            UIUpdateService.updateStatusLabel(statusLabel, "Invalid Customer ID format", "red");
            e.printStackTrace();
            return;
        }

        try {
            invoiceService.generateInvoice(customerId);
            UIUpdateService.updateStatusLabel(statusLabel, "Invoice generation request sent for: " + customerIdText, "green");
        } catch (HttpClientErrorException e) {
            InvoiceErrorHandler.handleHttpClientErrorException(statusLabel, e, customerId);
        } catch (RestClientException e) {
            InvoiceErrorHandler.handleRestClientException(statusLabel, e);
        }
    }
}






