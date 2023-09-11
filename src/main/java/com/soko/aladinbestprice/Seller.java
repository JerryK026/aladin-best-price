package com.soko.aladinbestprice;

public class Seller {
    private String name;
    private int deliveryFare;

    public Seller(String name, int deliveryFare) {
        this.name = name;
        this.deliveryFare = deliveryFare;
    }

    public String getName() {
        return name;
    }

    public int getDeliveryFare() {
        return deliveryFare;
    }

    public boolean isSame(String name) {
        return this.name.equals(name);
    }
}
