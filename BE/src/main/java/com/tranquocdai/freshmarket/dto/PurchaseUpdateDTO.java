package com.tranquocdai.freshmarket.dto;

import javax.validation.constraints.NotEmpty;

public class PurchaseUpdateDTO {
    private Long purchaseId;

    private Double purchaseNumber;

    private String buyerName;

    private String address;

    private String phoneNumber;

    private Long statusPurchaseId;

    private Double transportCost;

    private Long transportationTypeId;

    public Long getTransportationTypeId() {
        return transportationTypeId;
    }

    public void setTransportationTypeId(Long transportationTypeId) {
        this.transportationTypeId = transportationTypeId;
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(Double transportCost) {
        this.transportCost = transportCost;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getStatusPurchaseId() {
        return statusPurchaseId;
    }

    public void setStatusPurchaseId(Long statusPurchaseId) {
        this.statusPurchaseId = statusPurchaseId;
    }

    public Double getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Double purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
