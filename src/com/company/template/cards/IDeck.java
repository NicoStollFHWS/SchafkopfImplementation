package com.company.template.cards;

import com.company.schafkopf.cards.SchafkopfSuit;

public interface IDeck {
    void shuffleDeck();
    String toString();
    ISuit getTrump();
    void sortDeck();
}
