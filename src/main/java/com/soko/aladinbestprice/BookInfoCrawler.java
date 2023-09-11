package com.soko.aladinbestprice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class BookInfoCrawler implements BookInfoFinder {
    private final static String TITLE_SELECTOR = "#Ere_prod_allwrap_box > div.Ere_prod_topwrap > div.Ere_prod_titlewrap > div.left > div > ul > li:nth-child(1) > div > a.Ere_bo_title";
    private final static String NEW_BOOK_PRICE_SELECTOR = "#Ere_prod_allwrap_box > div.Ere_prod_bookwrap > div.Ere_prod_Binfowrap_used2 > div > div:nth-child(1) > ul > li:nth-child(2) > div.Ritem";

    private final static String GOODS_SELECTOR = "#Ere_prod_allwrap_box > div.Ere_prod_middlewrap > div.Ere_usedsell_table > table > tbody > tr";
    private final static String QUALITY_SELECTOR = "td:nth-child(3) > span";
    private final static String PRICE_SELECTOR = "td:nth-child(4) > div > ul > li:nth-child(1) > span";
    private final static String DELIVERY_FARE_SELECTOR = "td:nth-child(4) > div > ul > li:nth-child(3)";
    private final static String SELLER_SELECTOR = "td:nth-child(5) > div > ul > li:nth-child(1) > a";
    private final static String SELLER_METADATA_SELECTOR = "td:nth-child(5) > div > ul > li > div > a";
    private final static String STORE_SELLER_SELECTOR = "td:nth-child(5) > div > ul > li.Ere_store_name > a";

    private final static String ALADIN_DIRECT = "알라딘 직접 배송";
    private final static String COSMIC_STORE = "이 광활한 우주점";

    private final Logger logger = LoggerFactory.getLogger(BookInfoCrawler.class);

    public Books usedBooks(int itemId, Quality requiredQuality) {
        Books books = new Books();
        int pageNum = 1;
        String title = "";
        Integer newBookPrice = null;

        while (true) {
            String url = String.format("https://www.aladin.co.kr/shop/UsedShop/wuseditemall.aspx?ItemId=%d&page=%d", itemId, pageNum);
            try {
                Document doc = Jsoup.connect(url).get();
                Elements goods = doc.select(GOODS_SELECTOR);

                if (title.isBlank()) {
                    title = doc.select(TITLE_SELECTOR).text();
                }

                if (Objects.isNull(newBookPrice)) {
                    // 얻어오는 값 꼴 => 22,500원 + 마일리지 1,250원
                    newBookPrice = Integer.parseInt(doc.select(NEW_BOOK_PRICE_SELECTOR).text().split("원")[0].replaceAll(",", "").trim());
                }

                if (isEmptyPage(goods)) {
                    break;
                }

                for (Element book : goods) {
                    // 첫번째 요소는 metadata 영역이기 때문에 값을 추출할 수 없다
                    if (book.select(QUALITY_SELECTOR).text().isEmpty()) {
                        continue;
                    }

                    Quality quality = Quality.of(book.select(QUALITY_SELECTOR).text());
                    if (quality.isUnqualified(requiredQuality)) {
                        continue;
                    }

                    // 얻어오는 꼴 => 6,200
                    int price = Integer.parseInt(book.select(PRICE_SELECTOR).text().replaceAll(",", ""));
                    // 얻어오는 꼴 => 배송비 : 3,300원
                    int deliveryFare = Integer.parseInt(book.select(DELIVERY_FARE_SELECTOR).text().split(" ")[2].replaceAll(",", "").replaceAll("원", ""));
                    String seller = getSeller(book);

                    books.add(title, price, seller, deliveryFare);
                }

            } catch (IOException e) {
                logger.debug("[crawler exception]", e.getMessage());
                e.printStackTrace();
            }

            pageNum++;
        }

        return books;
    }

    /**
     * td의 자식들인 tr들 중 첫번째 tr은 상품들 칸에 대한 title임
     * 만약 페이지에 아무것도 없다면 첫번째 tr만 남아있다
     */
    private boolean isEmptyPage(Elements goods) {
        return goods.select("tr").size() == 1;
    }

    /**
     * Seller 종류는 3가지가 있다.
     * 1. 개인 판매자
     * 2. 알라딘 직접 배송
     * 3. 이 광할한 우주점
     */
    private String getSeller(Element book) {
        String seller = book.select(SELLER_SELECTOR).text();
        if (!seller.isEmpty()) {
            return seller;
        }

        String metadata = book.select(SELLER_METADATA_SELECTOR).text().trim();

        if (metadata.equals(ALADIN_DIRECT)) {
            return ALADIN_DIRECT;
        }

        if (metadata.equals(COSMIC_STORE)) {
            return book.select(STORE_SELLER_SELECTOR).text();
        }

        throw new IllegalArgumentException("예상하지 못한 값이 들어왔습니다." + metadata);
    }
}
