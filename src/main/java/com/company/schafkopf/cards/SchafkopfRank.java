package main.java.com.company.schafkopf.cards;

import main.java.com.company.template.cards.IRank;

/**
 * SchafkopfRank
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public enum SchafkopfRank implements IRank {
    SEVEN(0), EIGTH(0), NINE(0), TEN(10), JACK(2), QUEEN(3), KING(4), ACE(11);

    private final int value;

    SchafkopfRank(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
