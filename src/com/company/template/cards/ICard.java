package com.company.template.cards;

import com.company.schafkopf.cards.SchafkopfRank;
import com.company.schafkopf.cards.SchafkopfSuit;

public interface ICard {
    SchafkopfSuit getSuit();
    SchafkopfRank getRank();
    void setPlayable(boolean playable);
    boolean isPlayable();

}
