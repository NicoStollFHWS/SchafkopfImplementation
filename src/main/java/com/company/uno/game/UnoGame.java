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

import java.util.*;

/**
 * UnoGame
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */

//TODO set player am zug
public class UnoGame extends Game {
    private final Queue<Player> players;
    private List<ICard> cards;
    private UnoDeck playedCards;
    private int cardsToDrawCounter;
    private boolean chooseColor = false;


    public UnoGame(Queue<Player> players) {
        super();
        if(players.size() >= 2) {
            this.players = new ArrayDeque<>(players);
            initializeGame();
        } else {
            throw new UnsupportedOperationException("Zu wenige Spieler für Uno");
        }
    }

    private void initializeGame() {

        //Deck erstellen
        IDeck deck = new UnoDeck();
        deck.shuffleDeck();
        this.cards = (List<ICard>) deck.getDeck();

        //Kartenanzahl erhöhen, wenn zu wenig Karten für Spieler vorhanden sind
        while (players.size() * 7 > this.cards.size()) {
            addCardsToStack();
        }

        //Hand bei Spielern erstellen
        this.players.forEach(player -> player.setDeck(new UnoDeck(new ArrayList<>())));

        //verteile Karten - jeder Spieler kriegt 7 Stück
        for (int i = 0; i < 7; i++) {
            for (Player p : this.players) {
                p.addCard(this.cards.remove(0));
            }
        }

        //ablagestapel erstellen
        this.playedCards = new UnoDeck(new ArrayList<>());

        //setze erste Karte für Ablagestapel
        ICard first = this.cards.remove(0);
        this.playedCards.add(first);

        //setze Spielbarkeit bei allen Karten
        this.players.forEach(player -> player.getDeck().setPlayable(first));

        //deck sortieren
        this.players.forEach(player -> player.getDeck().sortDeck());

        //zu ziehende Karten auf 0 stellen
        this.cardsToDrawCounter = 0;

        //prüfen ob Spieler 1 eine Karte spielen kann
        assert this.players.peek() != null;
        drawSingleCard(this.players.peek());

    }


    @Override
    /**
     * Used here to choose a color after a CHOOSE_COLOR OR PLUS_FOUR has been played
     */
    public void setTrick(Player player, int trick) {

        //prüfen ob Spieler am Zug ist
        if (player.equals(this.players.peek()) == false) {
            System.err.println("Spieler nicht am Zug - er darf keine Farbe wählen");
            return;
        }

        //prüfen ob Farbe gewählt werden darf
        if (chooseColor == false) {
            System.err.println("Es darf keine Farbe gewählt werden");
            return;
        }

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

    }

    @Override
    public void playCard(Player player, ICard card) {

        //prüfen ob eine Farbe gewählt werden muss
        if (this.chooseColor) {
            System.out.println("Es muss erste eine Farbe gewählt werden");
            return;
        }
        //checken ob Spieler am Zug ist
        assert this.players.peek() != null;
        if (this.players.peek().equals(player) == false) {
            System.err.println("Spieler ist nicht am Zug");
            return;
        }

        //checken, ob Karte auf der Hand des Spielers ist
        if (player.getDeck().getDeck().contains(card) == false) {
            System.err.println("Karte befindet sich nicht auf der Hand des Spielers");
            return;
        }

        //checken ob Karte spielbar ist
        if (card.isPlayable() == false) {
            System.err.println("Karte ist nicht spielbar");
            return;
        }

        UnoCard unoCard = (UnoCard) card;

        //TODO reagieren wenn Karten gezogen werden müssen und keine weitere plus2 reingegeben wird

        if (unoCard.getRank().equals(UnoRank.TAKE_FOUR)) playPlusFour(unoCard);
        else if (unoCard.getRank().equals(UnoRank.TAKE_TWO)) playPlusTwo(unoCard);
        else if (unoCard.getRank().equals(UnoRank.REVERSE)) playReverse(unoCard);
        else if (unoCard.getRank().equals(UnoRank.CHOOSE_COLOR)) chooseColor(unoCard);
        else if (unoCard.getRank().equals(UnoRank.SKIP)) skipPlayer(unoCard);
        else playNormalCard(unoCard);

        this.setCountdownCurrentUser();
        System.err.println("Karte gespielt: " + card);


    }

    private void playPlusFour(UnoCard card) {
        this.playedCards.add(card);

        //add four cards to next players hand
        this.cardsToDrawCounter += card.getRank().getValue();

        //spieler nach derzeitigem muss die karten ziehen
        List<Player> temp = new ArrayList<>(players);
        drawCards(temp.get(1));

        //fabe wählen
        this.chooseColor = true;
    }

    private void playPlusTwo(UnoCard card) {
        this.playedCards.add(card);

        //entferne Karte vom Spieler
        this.players.peek().getDeck().remove(card);

        //cardsToDrawIncrement
        this.cardsToDrawCounter += card.getRank().getValue();

        //rotatePlayers
        rotatePlayers();

        //prüfen ob nächster Spieler +2 auf der hand hat
        boolean nextPlayerHasPlusTwo = this.players.peek().getDeck().getDeck()
                .stream()
                .anyMatch(c -> c.getRank().equals(UnoRank.TAKE_TWO));

        //wenn er keine hat werden die karten direkt gezogen
        if (nextPlayerHasPlusTwo == false) {
            drawCards(this.players.peek());
        }
    }

    private void playReverse(UnoCard card) {
        this.playedCards.add(card);

        //entferne Karte vom Spieler
        this.players.peek().getDeck().remove(card);

        //reverse queue
        Stack<Player> stack = new Stack<>();
        while (!this.players.isEmpty()) {
            stack.add(this.players.remove());
        }
        while (!stack.isEmpty()) {
            this.players.add(stack.peek());
            stack.pop();
        }

    }

    private void chooseColor(UnoCard card) {
        this.playedCards.add(card);

        //entferne Karte vom Spieler
        this.players.peek().getDeck().remove(card);

        //farbe darf gewählt werden selber spielt ist dran
        this.chooseColor = true;

        //spieler werden nicht rotiert
    }

    private void skipPlayer(UnoCard card) {
        this.playedCards.add(card);
        setPlayableForPlayers(card);

        //entferne Karte vom Spieler
        this.players.peek().getDeck().remove(card);

        //rotate and skip next in line
        rotatePlayers();
        rotatePlayers();
    }

    private void playNormalCard(UnoCard card) {
        //wenn karten gezogen werden müssen
        if (this.cardsToDrawCounter > 0) {
            drawCards(this.players.peek());
        }

        this.playedCards.add(card);

        //entferne Karte vom Spieler
        this.players.peek().getDeck().remove(card);

        setPlayableForPlayers(card);
        rotatePlayers();
    }

    private void setPlayableForPlayers(ICard card) {
        this.players.forEach(player -> player.getDeck().setPlayable(card));
    }

    private void rotatePlayers() {
        Player temp = this.players.remove();
        this.players.add(temp);

        //prüfen ob erster Spieler eine Karte spielen kann
        assert this.players.peek() != null;
        drawSingleCard(this.players.peek());
    }

    private void drawSingleCard(Player player) {
        ICard lastPlayed = this.playedCards.getDeck().get(this.playedCards.getDeck().size() -1);
        long numOfPlayableCards = player.getDeck().getDeck()
                .stream()
                .filter(ICard::isPlayable)
                .count();

        while(numOfPlayableCards == 0) {
            System.err.println("Es muss eine Karte nachgezogen werden");
            //Karten zu Ziehstapel hinzufügen wenn Stapel leer
            if(this.cards.size() < 1) {
                addCardsToStack();
            }

            //Karte ziehen
            player.addCard(this.cards.remove(0));

            //isPlayable setzen
            player.getDeck().setPlayable(lastPlayed);

            //prüfen ob eine Karte spielbar ist
            numOfPlayableCards = player.getDeck().getDeck()
                    .stream()
                    .filter(ICard::isPlayable)
                    .count();

        }
    }

    private void drawCards(Player player) {
        //stapel erweitern wenn nicht genügend Karten zum ziehen vorhanden sind
        while (this.cards.size() < this.cardsToDrawCounter) {
            addCardsToStack();
        }

        //karten bei user hinzufügen
        for (int i = cardsToDrawCounter; i > 0; i--) {
            player.addCard(this.cards.remove(0));
        }

        this.cardsToDrawCounter = 0;
    }

    private void addCardsToStack() {
        UnoDeck deck = new UnoDeck();
        deck.shuffleDeck();
        this.cards.addAll(deck.getDeck());
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
