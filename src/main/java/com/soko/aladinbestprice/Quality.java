package com.soko.aladinbestprice;

import java.util.Arrays;
import java.util.Objects;

public enum Quality {
    BEST("최상", 1), HIGH("상", 11), MEDIUM("중", 21);

    private final String value;
    private final int priority;

    Quality(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    public boolean isUnqualified(Quality quality) {
        return this.priority > quality.priority;
    }

    public static Quality of(String inputValue) {
        if (Objects.isNull(inputValue)) {
            return MEDIUM;
        }

        return Arrays.stream(values())
                .filter(quality -> quality.value.equals(inputValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("품질 입력이 잘못되었습니다. 입력된 값:" + inputValue));
    }
}
