package com.soko.aladinbestprice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooksTest {

    private final Seller seller1 = new Seller("Seller1", 1000);
    private final Seller seller2 = new Seller("Seller2", 2000);

    @DisplayName("한 판매자가 여러 책을 등록하면 가장 저렴한 것만을 등록한다")
    @Test
    void add_pickLowest() {
        Books books = new Books();

        books.add(new Book("Book1", 1000, seller1));
        books.add(new Book("Book1", 2000, seller1));
        books.add(new Book("Book1", 3000, seller1));
        books.add(new Book("Book1", 4000, seller1));
        books.add(new Book("Book1", 5000, seller1));

        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0).getPrice()).isEqualTo(1000);
    }

    @DisplayName("한 판매자가 여러 책을 등록하면 가장 저렴한 것만을 등록하고, 다른 책들에는 영향을 주지 않는다")
    @Test
    void add_pickLowestMixed() {
        Books books = new Books();

        books.add(new Book("Book1", 1000, seller1));
        books.add(new Book("Book1", 2000, seller1));
        books.add(new Book("Book1", 3000, seller2));
        books.add(new Book("Book1", 4000, seller2));

        assertThat(books.size()).isEqualTo(2);
        assertThat(books)
                .extracting(Book::getPrice)
                .contains(1000, 3000);
    }
}