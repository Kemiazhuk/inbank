package com.kem.inbank.entity;

public enum Segments {

    DEBT(0),
    SEGMENT_1(100),
    SEGMENT_2(300),
    SEGMENT_3(1000);

    private final int creditModifier;

    Segments(int creditModifier) {
        this.creditModifier = creditModifier;
    }

    public int getCreditModifier() {
        return creditModifier;
    }
}

