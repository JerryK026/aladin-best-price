package com.soko.aladinbestprice;

import com.soko.aladinbestprice.ui.UsedBook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BestPriceCalculatorTest {

    private static final String BOOK1 = "Book1";
    private static final String BOOK2 = "Book2";
    private static final Seller DELIVERY_FARE_3000_SELLER = new Seller("Seller1", 3000);
    private static final Seller DELIVERY_FARE_5000_SELLER = new Seller("Seller2", 5000);
    private static final Seller DELIVERY_FARE_50000_SELLER = new Seller("Seller3", 50000);

    private final BestPriceCalculator bestPriceCalculator = new BestPriceCalculator();

    @DisplayName("책 각각의 가격은 더 비싸지만 같은 판매자에게서 구매하면 더 저렴한 경우")
    @Test
    void eachBooksPricesAreExpensiveButAffordableWhenBuyFromSameSeller() {
        Books book1s = new Books();
        Books book2s = new Books();

        book1s.add(new Book(BOOK1, 5000, DELIVERY_FARE_3000_SELLER));
        book1s.add(new Book(BOOK1, 4500, DELIVERY_FARE_5000_SELLER));
        book2s.add(new Book(BOOK2, 5000, DELIVERY_FARE_3000_SELLER));
        book2s.add(new Book(BOOK2, 4500, DELIVERY_FARE_50000_SELLER));

        var usedBooks = bestPriceCalculator.calculateBestPriceCombination(List.of(book1s, book2s));

        assertThat(usedBooks.getUsedBooks())
                .extracting(UsedBook::getSellerName)
                .containsExactly(DELIVERY_FARE_3000_SELLER.getName(), DELIVERY_FARE_3000_SELLER.getName());
    }

    @DisplayName("판매자는 모두 다르지만 책 각각 구매하면 더 저렴한 경우")
    @Test
    void eachBooksPricesAreAffordable() {
        Books book1s = new Books();
        Books book2s = new Books();

        book1s.add(new Book(BOOK1, 2500, DELIVERY_FARE_3000_SELLER));
        book1s.add(new Book(BOOK1, 6500, DELIVERY_FARE_5000_SELLER));
        book2s.add(new Book(BOOK2, 50000, DELIVERY_FARE_3000_SELLER));
        book2s.add(new Book(BOOK2, 5000, DELIVERY_FARE_5000_SELLER));

        var usedBooks = bestPriceCalculator.calculateBestPriceCombination(List.of(book1s, book2s));

        assertThat(usedBooks.getUsedBooks())
                .extracting(UsedBook::getSellerName)
                .containsExactly(DELIVERY_FARE_3000_SELLER.getName(), DELIVERY_FARE_5000_SELLER.getName());
    }

    @DisplayName("책 가격은 더 비싸지만 배송비까지 합치면 더 저렴한 경우")
    @Test
    void samePriceAndDifferentDeliveryFare() {
        Books book1s = new Books();

        book1s.add(new Book(BOOK1, 7500, DELIVERY_FARE_3000_SELLER));
        book1s.add(new Book(BOOK1, 6500, DELIVERY_FARE_5000_SELLER));

        var usedBooks = bestPriceCalculator.calculateBestPriceCombination(List.of(book1s));

        assertThat(usedBooks.getUsedBooks().get(0).getSellerName())
                .isEqualTo(DELIVERY_FARE_3000_SELLER.getName());
    }
}