package main.java.com.company.schafkopf.cards;

import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IRank;
import main.java.com.company.template.cards.ISuit;

/**
 * SchafkopfCard
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public class SchafkopfCard implements ICard {
    protected SchafkopfSuit suit;
    protected SchafkopfRank rank;
    protected boolean playable = false;

    protected SchafkopfCard(SchafkopfSuit suit, SchafkopfRank rank) {
        this.suit = suit;
        this.rank = rank;
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
    public ISuit getSuit() {
        return suit;
    }


    @Override
    public IRank getRank() {
        return rank;
    }


    @Override
    public String toString() {
        return "SchafkopfCard{" +
                "suit=" + suit +
                ", rank=" + rank +
                ", playable=" + playable +
                '}' + "\n";
    }

    @Override
    public int compareTo(ICard cw) {
        if(this.suit.compareTo(cw.getSuit()) == 0) {
            return this.getRank().compareTo(cw.getRank());
        }
        return this.suit.compareTo(cw.getSuit());
    }
}
