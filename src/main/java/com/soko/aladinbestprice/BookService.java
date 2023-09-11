package com.soko.aladinbestprice;

import com.soko.aladinbestprice.ui.UsedBooksBestPriceRequest;
import com.soko.aladinbestprice.ui.UsedBooksBestPriceResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookInfoCrawler bookInfoCrawler;
    private final BestPriceCalculator bestPriceCalculator;

    public BookService(BookInfoCrawler bookInfoCrawler, BestPriceCalculator bestPriceCalculator) {
        this.bookInfoCrawler = bookInfoCrawler;
        this.bestPriceCalculator = bestPriceCalculator;
    }

    public UsedBooksBestPriceResponse findBestPriceBooks(UsedBooksBestPriceRequest usedBooksBestPriceRequest) {
        List<Books> usedBooksList = new ArrayList<>();
        for (Integer id : usedBooksBestPriceRequest.getItemIds()) {
            Books usedBooks = bookInfoCrawler.usedBooks(id, usedBooksBestPriceRequest.getQuality());
            if (usedBooks.size() != 0) {
                usedBooksList.add(usedBooks);
            }
        }

        return bestPriceCalculator.calculateBestPriceCombination(usedBooksList);
    }
}
