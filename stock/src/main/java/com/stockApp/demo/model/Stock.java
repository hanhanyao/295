package com.stockApp.demo.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false, unique = true)
    private double price;

    private Boolean purchase;

    //@Column(columnDefinition="LONGVARCHAR")
    private double purchaseprice;

    public Stock(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Boolean getPurchase() {
        return purchase;
    }

    public double getPurchaseprice() {
        return purchaseprice;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPurchase(Boolean purchase) {
        this.purchase = purchase;
    }

    public void setPurchaseprice(double purchaseprice) {
        this.purchaseprice = purchaseprice;
    }
}
