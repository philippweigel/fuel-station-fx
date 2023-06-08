package com.example.fuelstationfx.model;

public class Invoice {

    private String downloadLink;
    private String creationTime;

    public Invoice(String downloadLink, String creationTime) {
        this.downloadLink = downloadLink;
        this.creationTime = creationTime;
    }

    public Invoice() {
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
