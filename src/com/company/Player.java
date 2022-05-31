package com.company;

import java.util.ArrayList;

public class Player {
    private String name;
    private int point = 0;
    private int statesTrick;    //ist hier das gewünschte spiel für die runde
    private CardDeck deck;
    private ICard playedCard = null;

    public Player(String name)
    {
        this.name = name;
        this.statesTrick = -1;
        this.deck = new CardDeck(new ArrayList<>());
    }

    public void addCard(ICard card) {
        this.deck.add((Card) card);
    }

    public void addPoint(int points) {
        this.point += points;
    }

    public int getPoints() {
        return this.point;
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

    public CardDeck getDeck() {
        return this.deck;
    }

    public ICard getPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(ICard playedCard) {
        this.playedCard = playedCard;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + "\n" +
                "numOfCards=" + deck.getDeck().size() + "\n" +
                "set=" + deck.printCards() + "\n" +
                "}\n";
    }
}
