package com.soko.aladinbestprice.ui;

public class UsedBook {
    private String title;
    private int price;
    private String sellerName;

    public UsedBook(String title, int price, String sellerName) {
        this.title = title;
        this.price = price;
        this.sellerName = sellerName;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getSellerName() {
        return sellerName;
    }
}
