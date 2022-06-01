package main.java.com.company.uno.cards;

import main.java.com.company.template.cards.IRank;

public enum UnoRank implements IRank {
    ONE_1, ONE_2, TWO_1, TWO_2, THREE_1, THREE_2, FOUR_1, FOUR_2, FIVE_1, FIVE_2, SIX_1, SIX_2,
    SEVEN_1, SEVEN_2, EIGHT_1, EIGHT_2, NINE_1, NINE_2, REVERSE, SKIP, TAKE_TWO, TAKE_FOUR, CHOOSE_COLOR;

    @Override
    public int getValue() {
        return 0;
    }
}
