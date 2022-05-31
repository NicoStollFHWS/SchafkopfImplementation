package com.company.template.cards;

import com.company.schafkopf.cards.SchafkopfCard;
import com.company.schafkopf.cards.SchafkopfSuit;
import com.company.schafkopf.game.GameType;

import java.util.Collection;
import java.util.List;

/**
 * IDeck
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public interface IDeck {
    void shuffleDeck();
    String toString();
    ISuit getTrump();
    void sortDeck();

    void add(ICard card);
    boolean remove(ICard card);

    List<SchafkopfCard> getDeck();

    String printCards();

    void setPlayable(SchafkopfCard card);

    void setType(GameType gameType);
}
