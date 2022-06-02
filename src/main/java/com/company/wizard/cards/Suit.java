package main.java.com.company.wizard.cards;

import main.java.com.company.template.cards.ISuit;

public enum Suit implements ISuit {
    HEART("RED"), DIAMOND("PURPLE"), CLUB("GREEN"), SPADE("BLACK");
//  Hearts (Red), Spades (Black), Clubs (Green), Diamonds (Purple)

    private final String color;
    private int trump = 0;

    Suit(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
    public int getValue() {
        return this.ordinal();
    }

    @Override
    public int compareTo(ISuit suit) {
        return this.compareTo(suit);
    }
}

