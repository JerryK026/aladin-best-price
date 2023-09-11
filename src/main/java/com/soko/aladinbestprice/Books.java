package com.soko.aladinbestprice;

import java.util.*;

public class Books implements Iterable<Book> {
    List<Book> books = new ArrayList<>();

    public void add(Book book) {
        books.add(book);
    }

    public void add(String title, int price, String seller, int deliveryFare) {
        books.add(new Book(title, price, new Seller(seller, deliveryFare)));
    }

    public void add(Books books) {
        this.books.addAll(books.asList());
    }

    public int size() {
        return this.books.size();
    }

    public Book get(int idx) {
        return books.get(idx);
    }

    public List<Book> asList() {
        return this.books;
    }

    @Override
    public Iterator<Book> iterator() {
        return this.books.iterator();
    }

}
