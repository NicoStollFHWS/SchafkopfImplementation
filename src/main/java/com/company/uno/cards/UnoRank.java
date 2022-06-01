package main.java.com.company.uno.cards;

import main.java.com.company.template.cards.IRank;

/**
 * UnoRank
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public enum UnoRank implements IRank {
    ONE(0), TWO(0), THREE(0), FOUR(0), FIVE(0), SIX(0),
    SEVEN(0), EIGHT(0), NINE(0), REVERSE(0), SKIP(0),
    TAKE_TWO(2), TAKE_FOUR(4), CHOOSE_COLOR(0);

    private final int value;

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public int compareTo(IRank rank) {
        if(this.getClass().equals(rank.getClass()) == false) {
            throw new UnsupportedOperationException("Verschiedene Ranks können nicht verglichen werden");
        }

        return this.ordinal() - rank.ordinal();
    }

    UnoRank(int value) {
        this.value = value;
    }
}
