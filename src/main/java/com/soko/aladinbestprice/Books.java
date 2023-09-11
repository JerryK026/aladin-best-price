package com.soko.aladinbestprice;

import java.util.*;

/**
 * 같은 종류의 중고책들이 들어간다
 */
public class Books implements Iterable<Book> {
    private List<Book> books = new ArrayList<>();
    private Set<String> sellerCache = new HashSet<>();

    public void add(Book newBook) {
        // 기존에 등록된 적 없는 판매자면 바로 add한다
        if (!sellerCache.contains(newBook.getSeller().getName())) {
            books.add(newBook);
            sellerCache.add(newBook.getSeller().getName());
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            if (doesHaveSameSeller(newBook, book)) {
                if (book.getPrice() <= newBook.getPrice()) return;

                books.remove(i);
                books.add(newBook);
                return;
            }
        }
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

    @Override
    public Iterator<Book> iterator() {
        return this.books.iterator();
    }

    private boolean doesHaveSameSeller(Book newBook, Book book) {
        return book.getSeller().getName().equals(newBook.getSeller().getName());
    }
}
