package com.soko.aladinbestprice;

public class Book {
    private String title;
    private int price;
    private Seller seller;

    public Book(String title, int price, Seller seller) {
        this.title = title;
        this.price = price;
        this.seller = seller;
    }

    public String getTitle() {
        return title;
    }

    public Seller getSeller() {
        return seller;
    }

    public int getPrice() {
        return price;
    }
}
