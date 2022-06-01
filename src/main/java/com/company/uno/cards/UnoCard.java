package main.java.com.company.uno.cards;

import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IRank;
import main.java.com.company.template.cards.ISuit;

/**
 * UnoCard
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
public class UnoCard implements ICard {
    private final ISuit suit;
    private final IRank rank;
    private boolean playable = false;

    public UnoCard(ISuit suit, IRank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public ISuit getSuit() {
        return this.suit;
    }


    @Override
    public IRank getRank() {
        return this.rank;
    }


    @Override
    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    @Override
    public boolean isPlayable() {
        return this.playable;
    }

    @Override
    public int compareTo(ICard cw) {
       return this.getRank().compareTo(cw.getRank());
    }

    @Override
    public String toString() {
        return "UnoCard{" +
                "suit=" + suit +
                ", rank=" + rank +
                ", playable=" + playable +
                '}' + "\n";
    }
}
