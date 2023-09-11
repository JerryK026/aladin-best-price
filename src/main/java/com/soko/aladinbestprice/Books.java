package com.soko.aladinbestprice;

import java.util.*;

/**
 * 같은 종류의 중고책들이 들어간다
 */
public class Books implements Iterable<Book> {
    List<Book> books = new ArrayList<>();

    public void add(Book newBook) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            if (doesHaveSameSeller(newBook, book)) {
                if (book.getPrice() <= newBook.getPrice()) return;

                books.remove(i);
                books.add(newBook);
                return;
            }
        }

        books.add(newBook);
    }

    public void add(String title, int price, String seller, int deliveryFare) {
        add(new Book(title, price, new Seller(seller, deliveryFare)));
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

    private boolean doesHaveSameSeller(Book newBook, Book book) {
        return book.getSeller().getName().equals(newBook.getSeller().getName());
    }
}
