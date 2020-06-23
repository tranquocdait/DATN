package com.tranquocdai.freshmarket.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transportations")
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateOfShipment;

    private LocalDateTime dateOfReceipt;

    private Double Prince;

    @OneToOne(targetEntity = Purchase.class)
    private Purchase purchase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOfShipment() {
        return dateOfShipment;
    }

    public void setDateOfShipment(LocalDateTime dateOfShipment) {
        this.dateOfShipment = dateOfShipment;
    }

    public LocalDateTime getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(LocalDateTime dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public Double getPrince() {
        return Prince;
    }

    public void setPrince(Double prince) {
        Prince = prince;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
