package com.soko.aladinbestprice.ui;

import com.soko.aladinbestprice.Quality;

import java.util.List;
import java.util.Objects;

public class UsedBooksBestPriceRequest {
    private List<Integer> itemIds;
    private String quality;

    public UsedBooksBestPriceRequest(List<Integer> itemIds, String quality) {
        this.itemIds = itemIds;
        this.quality = quality;
    }

    public List<Integer> getItemIds() {
        return itemIds.stream()
                .filter(Objects::nonNull)
                .toList()
                .subList(0, 4);
    }

    public Quality getQuality() {
        return Quality.of(quality);
    }

    public boolean isBlank() {
        return Objects.isNull(itemIds);
    }
}
