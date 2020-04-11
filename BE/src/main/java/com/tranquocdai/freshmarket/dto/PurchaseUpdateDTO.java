package com.tranquocdai.freshmarket.dto;

import javax.validation.constraints.NotEmpty;

public class PurchaseUpdateDTO {
    private Long purchaseId;

    @NotEmpty
    private Double purchaseNumber;

    @NotEmpty
    private Long statusPurchaseId;

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
}
