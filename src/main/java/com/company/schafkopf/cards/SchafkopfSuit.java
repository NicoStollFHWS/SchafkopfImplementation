package main.java.com.company.schafkopf.cards;

import main.java.com.company.template.cards.ISuit;

/**
 * SchafkopfSuit
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public enum SchafkopfSuit implements ISuit {
    SCHELLE, HERZ, GRAS, EICHEL;

    @Override
    public int compareTo(ISuit suit) {
        if(this.getClass().equals(suit.getClass()) == false) {
            throw new UnsupportedOperationException("Verschiedene Suits können nicht verglichen werden");
        }
        return this.ordinal() - suit.ordinal();
    }
}
