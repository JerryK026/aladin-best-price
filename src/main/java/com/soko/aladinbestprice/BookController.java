package com.soko.aladinbestprice;

import com.soko.aladinbestprice.ui.UsedBooksBestPriceRequest;
import com.soko.aladinbestprice.ui.UsedBooksBestPriceResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/best-price")
    public String affordablePrice(Model model, UsedBooksBestPriceRequest request) {
        if (request.isBlank()) {
            throw new IllegalArgumentException("아이템 ID가 전송되지 않았습니다");
        }

        UsedBooksBestPriceResponse bestPriceBooks = bookService.findBestPriceBooks(request);
        model.addAttribute("usedBooks", bestPriceBooks.getUsedBooks());

        return "index";
    }
}
