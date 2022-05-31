package main.java.com.company.template.cards;

import main.java.com.company.schafkopf.cards.SchafkopfCard;
import main.java.com.company.schafkopf.cards.SchafkopfSuit;
import main.java.com.company.schafkopf.game.GameType;

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

    List<? extends ICard> getDeck();

    String printCards();

    void setPlayable(ICard first);

    void setType(GameType gameType);
}