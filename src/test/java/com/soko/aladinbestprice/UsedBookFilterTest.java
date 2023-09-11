package com.soko.aladinbestprice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UsedBookFilterTest {

    private final UsedBookFilter usedBookFilter = new UsedBookFilter();

    @DisplayName("가장 저렴한 물품만 남긴다 - 가격이 저렴한 경우")
    @Test
    void filter_lowestPrice() {
        // given
        List<Books> booksList = new ArrayList<>();
        Books books = new Books();
        books.add(new Book("Book1", 2500, new Seller("Seller1", 2500)));
        books.add(new Book("Book1", 3500, new Seller("Seller2", 2000)));
        books.add(new Book("Book1", 4500, new Seller("Seller3", 2000)));
        books.add(new Book("Book1", 5500, new Seller("Seller4", 2000)));
        booksList.add(books);

        // when
        List<Books> filtered = usedBookFilter.filter(booksList);

        // then
        var target = filtered.get(0);
        assertThat(target.size()).isEqualTo(1);
        assertThat(target.get(0).getSeller().getName()).isEqualTo("Seller1");
    }

    @DisplayName("가장 저렴한 물품만 남긴다 - 배송비가 저렴한 경우")
    @Test
    void filter_lowestDeliveryFare() {
        List<Books> booksList = new ArrayList<>();
        Books books = new Books();
        books.add(new Book("Book1", 3000, new Seller("Seller1", 2000)));
        books.add(new Book("Book1", 2500, new Seller("Seller2", 3000)));
        books.add(new Book("Book1", 2500, new Seller("Seller3", 4000)));
        books.add(new Book("Book1", 2500, new Seller("Seller4", 5000)));
        booksList.add(books);

        List<Books> filtered = usedBookFilter.filter(booksList);

        var target = filtered.get(0);
        assertThat(target.size()).isEqualTo(1);
        assertThat(target.get(0).getSeller().getName()).isEqualTo("Seller1");
    }

}