package com.example.fuelstationfx.service;

import com.example.fuelstationfx.model.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

public class InvoiceService {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String INVOICE_URL = BASE_URL + "/invoices/";

    public Invoice fetchInvoice(int customerId) throws RestClientException, JsonProcessingException {
        String invoiceUrl = INVOICE_URL + customerId;

        ResponseEntity<String> responseEntity = sendGetRequest(invoiceUrl, HttpMethod.GET, null);
        String responseBody = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseBody, Invoice.class);
    }

    public void generateInvoice(int customerId) throws RestClientException {
        String invoiceUrl = INVOICE_URL + customerId;
        sendPostRequest(invoiceUrl, HttpMethod.POST, null);
    }

    private ResponseEntity<String> sendGetRequest(String url, HttpMethod method, Object request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, new HttpEntity<>(request), String.class);
    }

    private void sendPostRequest(String url, HttpMethod method, Object request) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(url, method, new HttpEntity<>(request), String.class);
    }

}