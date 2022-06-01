package main.java.com.company.uno.cards;

import main.java.com.company.template.cards.IRank;

/**
 * UnoRank
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public enum UnoRank implements IRank {
    ONE_1(0), ONE_2(0), TWO_1(0), TWO_2(0), THREE_1(0), THREE_2(0),
    FOUR_1(0), FOUR_2(0), FIVE_1(0), FIVE_2(0), SIX_1(0), SIX_2(0),
    SEVEN_1(0), SEVEN_2(0), EIGHT_1(0), EIGHT_2(0), NINE_1(0), NINE_2(0),
    REVERSE(0), SKIP(0), TAKE_TWO(2), TAKE_FOUR(4), CHOOSE_COLOR(0);

    private final int value;

    @Override
    public int getValue() {
        return 0;
    }

    UnoRank(int value) {
        this.value = value;
    }
}
