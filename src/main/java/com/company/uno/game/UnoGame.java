package main.java.com.company.uno.game;

import main.java.com.company.template.Player;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IDeck;
import main.java.com.company.template.cards.ISuit;
import main.java.com.company.template.game.Game;
import main.java.com.company.uno.cards.UnoCard;
import main.java.com.company.uno.cards.UnoDeck;
import main.java.com.company.uno.cards.UnoRank;
import main.java.com.company.uno.cards.UnoSuit;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UnoGame extends Game {
    private Queue<Player> players;
    private List<ICard> cards;
    private UnoDeck playedCards;
    private int cardsToDrawCounter;
    private boolean chooseColor = false;


    public UnoGame(Queue<Player> players) {
        this.players = players;
        initializeGame();
    }

    private void initializeGame() {

        //Deck erstellen
        IDeck deck = new UnoDeck();
        deck.shuffleDeck();
        this.cards = (List<ICard>) deck.getDeck();

        //Kartenanzahl erhöhen, wenn zu wenig Karten für Spieler vorhanden sind
        while(players.size() * 7 > this.cards.size()) {
            addCardsToStack();
        }

        //Hand bei Spielern erstellen
        this.players.forEach(player -> player.setDeck(new UnoDeck(new ArrayList<>())));

        //verteile Karten - jeder Spieler kriegt 7 Stück
        for(int i = 0; i < 7; i ++) {
            for(Player p : this.players) {
                p.addCard(this.cards.remove(0));
            }
        }

        //setze alle Karten auf spielbar
        this.players.forEach(player -> player.getDeck().getDeck().forEach(card -> card.setPlayable(true)));

        //ablagestapel erstellen
        this.playedCards = new UnoDeck(new ArrayList<>());

        //zu ziehende Karten auf 0 stellen
        this.cardsToDrawCounter = 0;
    }


    @Override
    /**
     * Used here to choose a color after a CHOOSE_COLOR OR PLUS_FOUR has been played
     */
    public void setTrick(Player player, int trick) {
        if(player.equals(this.players.peek())) {
            if (chooseColor) {
                //ungültige Eingabe
                if (trick < 0 || trick >= UnoSuit.values().length) {
                    return;
                }

                //setzen eines defaults
                UnoSuit suit = UnoSuit.BLUE;

                //finden der passenden Suit für eingabe
                for (UnoSuit s : UnoSuit.values()) {
                    if (s.ordinal() == trick) {
                        suit = s;
                        break;
                    }
                }

                //setzten der Spielbarkeit bei allen Spielern
                UnoSuit finalSuit = suit;
                this.players.forEach(p -> p.getDeck().setPlayable(new UnoCard(finalSuit, UnoRank.CHOOSE_COLOR)));

                //choose color wieder auf false setzen
                this.chooseColor = false;

                //rotate Players
                rotatePlayers();

            } else {
                System.out.println("Es darf keine Farbe gewählt werden");
            }
        } else {
            System.out.println("Spieler nicht am Zug - er darf keine Farbe wählen");
        }
    }

    @Override
    public void playCard(Player player, ICard card) {
        //checken ob Spieler am Zug ist
        if(this.players.peek().equals(player)) {

            //checken, ob Karte auf der Hand des Spielers ist
            if(player.getDeck().getDeck().contains(card)) {

                //checken ob Karte spielbar ist
                if(card.isPlayable()) {
                    UnoCard unoCard = (UnoCard) card;

                    //TODO reagieren wenn Karten gezogen werden müssen und keine weitere plus2 reingegeben wird

                    if(unoCard.getRank().equals(UnoRank.TAKE_FOUR)) playPlusFour(unoCard);
                    else if(unoCard.getRank().equals(UnoRank.TAKE_TWO)) playPlusTwo(unoCard);
                    else if(unoCard.getRank().equals(UnoRank.REVERSE)) playReverse(unoCard);
                    else if(unoCard.getRank().equals(UnoRank.CHOOSE_COLOR)) chooseColor(unoCard);
                    else if(unoCard.getRank().equals(UnoRank.SKIP)) skipPlayer(unoCard);
                    else playNormalCard(unoCard);

                }
            }
        }
    }

    private void playPlusFour(UnoCard card) {

    }

    private void playPlusTwo(UnoCard card) {

    }

    private void playReverse(UnoCard card) {

    }

    private void chooseColor(UnoCard card) {

    }

    private void skipPlayer(UnoCard card) {

    }

    private void playNormalCard(UnoCard card) {

    }

    private void rotatePlayers() {

    }

    private void drawCards(Player player, int numOfCards) {
        //stapel erweitern wenn nicht genügend Karten zum ziehen vorhanden sind
        while(this.cards.size() < numOfCards) {
            addCardsToStack();
        }

        //karten bei user hinzufügen
        for(int i = numOfCards; i > 0; i--) {
            player.addCard(this.cards.remove(0));
        }
    }

    private void addCardsToStack() {
        UnoDeck deck = new UnoDeck();
        deck.shuffleDeck();
        this.cards.addAll( deck.getDeck());
    }

    @Override
    public ISuit getTrump() {
        throw new UnsupportedOperationException("Kein Trump in Uno");
    }

    @Override
    public List<ICard> getPlayedCards() {
        throw new UnsupportedOperationException("Können nicht ausgegeben werden");
    }

    @Override
    public Player getCurrentUser() {
        return this.players.peek();
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(this.players);
    }
}
