package com.tranquocdai.freshmarket.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = User.class)
    private User buyer;

    private LocalDateTime dateOfOrder;

    private Double purchaseNumber;

    @OneToOne(targetEntity = StatusPurchase.class)
    private StatusPurchase statusPurchase;

    @OneToOne(targetEntity = Post.class)
    private Post post;

    @OneToOne(targetEntity = TransportationType.class)
    private TransportationType transportationType;

    public TransportationType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public LocalDateTime getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(LocalDateTime dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public Double getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Double purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public StatusPurchase getStatusPurchase() {
        return statusPurchase;
    }

    public void setStatusPurchase(StatusPurchase statusPurchase) {
        this.statusPurchase = statusPurchase;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
