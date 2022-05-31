package com.company.schafkopf.cards;

import com.company.template.cards.ICard;

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
    public SchafkopfSuit getSuit() {
        return suit;
    }

    @Override
    public SchafkopfRank getRank() {
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
}
