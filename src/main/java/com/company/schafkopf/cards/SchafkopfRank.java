package main.java.com.company.schafkopf.cards;

import main.java.com.company.template.cards.IRank;

/**
 * SchafkopfRank
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
enum SchafkopfRank implements IRank {
    SEVEN(0), EIGTH(0), NINE(0), KING(4), TEN(10), ACE(11), JACK(2), QUEEN(3);

    private final int value;

    SchafkopfRank(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public int compareTo(IRank rank) {
        if(this.getClass().equals(rank.getClass()) == false) {
            throw new UnsupportedOperationException("Verschiedene Ranks können nicht verglichen werden");
        }

        return this.ordinal() - rank.ordinal();
    }
}
