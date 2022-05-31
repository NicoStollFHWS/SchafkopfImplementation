package main.java.com.company.uno.cards;

import main.java.com.company.schafkopf.game.GameType;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IDeck;
import main.java.com.company.template.cards.ISuit;

import java.util.Collections;
import java.util.List;

public class UnoDeck implements IDeck {
    private List<UnoCard> cards;

    @Override
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    @Override
    public ISuit getTrump() {
        throw new UnsupportedOperationException("Es gibt keinen Trump in UNO");
    }

    @Override
    public void sortDeck() {
        //TODO
    }

    @Override
    public void add(ICard card) {
        this.cards.add((UnoCard) card);
    }

    @Override
    public boolean remove(ICard card) {
        return this.cards.remove((UnoCard) card);
    }

    @Override
    public List<? extends ICard> getDeck() {
        return this.cards;
    }

    @Override
    public String printCards() {
        //TODO
        return null;
    }

    @Override
    public void setPlayable(ICard playedCard) {
        //TODO
    }

    @Override
    public void setType(GameType gameType) {
        throw new UnsupportedOperationException("Es gibt keinen Spieltyp in Uno");
    }


}
