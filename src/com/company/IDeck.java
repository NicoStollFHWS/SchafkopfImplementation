package com.company;

public interface IDeck {
    void shuffleDeck();
    String toString();
    Suit getTrump();
    void sortDeck();
}
