package com.tranquocdai.freshmarket.dto;

public class PurchaseAddDTO {
    private Double purchaseNumber;

    private Long postId;

    private Long statusPurchaseId;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
