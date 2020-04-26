package com.tranquocdai.freshmarket.dto;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class PurchaseAddDTO {
    private Double purchaseNumber;

    private Long postId;

    private Long statusPurchaseId;

    private String fullName;

    private String phoneNumber;

    private String address;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
