package com.soko.aladinbestprice;

import com.soko.aladinbestprice.ui.UsedBooksBestPriceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BestPriceCalculator {

    private Logger logger = LoggerFactory.getLogger(BestPriceCalculator.class);

    public UsedBooksBestPriceResponse calculateBestPriceCombination(
            List<Books> booksList
    ) {
        Queue<State> stateQueue = new LinkedList<>();
        stateQueue.add(new State());

        for (int i = 0; i < booksList.size(); i++) {
            // title 별로 중고책들 나누기
            Books books = booksList.get(i);
            int qSize = stateQueue.size();
            for (int k = 0; k < qSize; k++) {
                State state = stateQueue.poll();
                for (int j = 0; j < books.size(); j++) {
                    Book book = books.get(j);
                    List<Book> newStatusBookList = new ArrayList<>(state.bookList);
                    newStatusBookList.add(book);

                    State newState = new State(newStatusBookList, state.price + book.getPrice());
                    stateQueue.add(newState);
                }
            }
        }

        State minPriceState = new State(List.of(), Integer.MAX_VALUE);

        while (!stateQueue.isEmpty()) {
            State state = stateQueue.poll();
            if (minPriceState.price() > state.price()) {
                minPriceState = state;
            }
        }

        return UsedBooksBestPriceResponse.from(minPriceState.bookList);
    }

    class State {
        List<Book> bookList = new ArrayList<>();
        int price = 0;

        public State() {
        }

        public State(List<Book> bookList, int price) {
            this.bookList = bookList;
            this.price = price;
        }

        public int price() {
            Set<Seller> visited = new HashSet<>();
            int sum = price;

            for (Book book : bookList) {
                if (!visited.contains(book.getSeller())) {
                    visited.add(book.getSeller());
                    sum += book.getSeller().getDeliveryFare();
                }
            }

            return sum;
        }
    }
}
