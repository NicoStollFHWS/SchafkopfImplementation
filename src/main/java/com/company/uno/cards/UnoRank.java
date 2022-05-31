package main.java.com.company.uno.cards;

import main.java.com.company.template.cards.IRank;

public enum UnoRank implements IRank {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGTH, NINE, REVERSE, SKIP, TAKE_TWO, TAKE_FOUR, CHOOSE_COLOR;

    @Override
    public int getValue() {
        return 0;
    }
}
