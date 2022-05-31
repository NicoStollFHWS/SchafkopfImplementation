package com.company;

public interface ICard {
    Suit getSuit();
    Rank getRank();
    void setPlayable(boolean playable);
    boolean isPlayable();
}
