package main.java.com.company.template;

import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IDeck;

/**
 * Player
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
public class Player {
    private String name;
    private int point = 0;
    private int statesTrick;    //ist hier das gewünschte spiel für die runde
    private IDeck deck;
    private ICard playedCard = null;
    private int wonTrick = 0;

    public Player(String name)
    {
        this.name = name;
        this.statesTrick = -1;
    }

    public void addCard(ICard card) {
        this.deck.add(card);
    }

    public void setDeck(IDeck deck) {
        this.deck = deck;
    }

    public void addPoint(int points) {
        this.point += points;
    }

    public int getPoints() {
        return this.point;
    }

    public void setPoints(int points) {
        this.point = points;
    }

    public void resetPoints() {
        this.point = 0;
    }

    public void setStatesTrick(int i) {
        this.statesTrick = i;
    }

    public int getStatesTrick() {
        return this.statesTrick;
    }

    public IDeck getDeck() {
        return this.deck;
    }

    public ICard getPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(ICard playedCard) {
        this.playedCard = playedCard;
    }

    public int getWonTrick() {
        return this.wonTrick;
    }

    public void setWonTrick() {
        this.wonTrick++;
    }

    public void resetWonTrick() {
        this.wonTrick = 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + "\n" +
                "numOfCards=" + deck.getCards().size() + "\n" +
                "set=" + deck.printCards() + "\n" +
                "}\n";
    }
}
