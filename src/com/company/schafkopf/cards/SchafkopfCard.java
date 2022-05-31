package com.company.schafkopf.cards;

import com.company.template.cards.ICard;
import com.company.template.cards.IRank;
import com.company.template.cards.ISuit;

/**
 * SchafkopfCard
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public class SchafkopfCard implements ICard {
    protected final SchafkopfSuit suit;
    protected final SchafkopfRank rank;
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
        return this.suit.compareTo(((SchafkopfCard) cw).suit);
    }
}
