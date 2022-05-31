package com.company;

public enum Rank {
    SEVEN(0), EIGTH(0), NINE(0), TEN(10), JACK(2), QUEEN(3), KING(4), ACE(11);

    protected final int value;

    Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
