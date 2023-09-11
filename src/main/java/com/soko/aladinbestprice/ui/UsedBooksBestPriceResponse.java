package com.soko.aladinbestprice.ui;

import com.soko.aladinbestprice.Book;

import java.util.ArrayList;
import java.util.List;

public class UsedBooksBestPriceResponse {

    private List<UsedBook> usedBooks = new ArrayList<>();

    public UsedBooksBestPriceResponse() {
    }

    public UsedBooksBestPriceResponse(List<UsedBook> usedBooks) {
        this.usedBooks = new ArrayList<>(usedBooks);
    }

    public List<UsedBook> getUsedBooks() {
        return usedBooks;
    }

    public static UsedBooksBestPriceResponse empty() {
        return new UsedBooksBestPriceResponse();
    }

    public static UsedBooksBestPriceResponse from(List<Book> books) {
        return new UsedBooksBestPriceResponse(
                books.stream()
                        .map(book -> new UsedBook(
                                book.getTitle(),
                                book.getPrice(),
                                book.getSeller().getName())
                        )
                        .toList()
        );
    }
}
