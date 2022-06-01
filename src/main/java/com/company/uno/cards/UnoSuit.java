package main.java.com.company.uno.cards;

import main.java.com.company.template.cards.ISuit;

/**
 * UnoSuit
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
public enum UnoSuit implements ISuit {
    RED, BLUE, YELLOW, GREEN;

    @Override
    public int compareTo(ISuit suit) {
        if(this.getClass().equals(suit.getClass()) == false) {
            throw new UnsupportedOperationException("Verschiedene Suits können nicht verglichen werden");
        }

        return this.ordinal() - suit.ordinal();
    }
}
