package main.java.com.company.schafkopf.game;

import main.java.com.company.schafkopf.cards.SchafkopfDeck;
import main.java.com.company.schafkopf.cards.SchafkopfCard;
import main.java.com.company.template.Player;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.ISuit;
import main.java.com.company.template.game.Game;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Schafkopf
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */

//TODO set player am zug
//TODO neue runde starten
//TODO stats erstellen
public class Schafkopf extends Game {
    private static final int NUM_PLAYERS = 4;

    private final Queue<Player> players;
    private int stichCounter = 0;
    private Queue<Player> currentRound;
    private SchafkopfDeck playedCard = new SchafkopfDeck(new ArrayList<>());
    private GameType gameType = GameType.NORMAL;
    private List<Player> team1;
    private List<Player> team2;
    private Player lastWinner;

    public Schafkopf(Queue<Player> players) {
        super();
        if (players.size() == NUM_PLAYERS) {
            this.players = new ArrayDeque<>(players);
            initializeRound();
        } else {
            throw new UnsupportedOperationException("Es müssen genau vier Spieler spielen");
        }
    }

    private void initializeRound() {

        //counter auf 0 setzen
        this.stichCounter = 0;

        //playerDecks erstellen
        this.players.forEach(p -> p.setDeck(new SchafkopfDeck(new ArrayList<>())));

        //Kartendeck erstellen
        SchafkopfDeck deck = new SchafkopfDeck();
        deck.shuffleDeck();

        //Karten verteilen
        List<ICard> schafkopfCards = deck.getDeck();
        while (schafkopfCards.size() != 0) {
            for (Player p : this.players) {
                ICard card = schafkopfCards.remove(0);
                p.addCard(card);
            }
        }

        //gewähltes spiel bei allen spielern auf -1 setzen
        this.players.forEach(player -> player.setStatesTrick(-1));

        //alle Karten auf playable setzen
        this.players.forEach(p -> {
            p.getDeck().getDeck().forEach(wizardCard -> wizardCard.setPlayable(true));
        });

        //alle Karten sortieren
        this.players.forEach(p -> p.getDeck().sortDeck());

        //queue für runde erstellen
        this.currentRound = new ArrayDeque<>(this.players);

    }

    private void initializeStich() {
        //runde um eins erhöhen
        this.stichCounter++;

        //queue für runde erstellen
        this.currentRound = new ArrayDeque<>(this.players);

        //queue der spieler auf gewinner der letzten runde setzen
        if(this.lastWinner != null) {
            while(this.currentRound.peek() != this.lastWinner) {
                Player temp = this.currentRound.remove();
                this.currentRound.add(temp);
            }
        }

        //playedCard bei allen auf null setzen
        this.currentRound.forEach(player -> player.setPlayedCard(null));

        //map der gespielten Karten -> neue map
        this.playedCard = new SchafkopfDeck(new ArrayList<>());

        //alle Karten auf playable setzen
        this.players.forEach(p -> p.getDeck().getDeck().forEach(wizardCard -> wizardCard.setPlayable(true)));

        //Karten sortieren
        this.players.forEach(p -> p.getDeck().sortDeck());

    }

    private void setTeams() {
        this.team1 = new ArrayList<>();
        this.team2 = new ArrayList<>();

        List<Player> temp = new ArrayList<>(this.players);

        switch (this.gameType) {
            case NORMAL, RAMSCH -> {
                //in diesen Fällen wird 2 vs 2 gespielt
                team1.add(temp.get(0));
                team2.add(temp.get(1));
                team1.add(temp.get(2));
                team2.add(temp.get(3));
            }
            default -> {
                //spieler finden, der spiel vorgegeben hat
                //ordinal = gametype.ordinal()
                Player tempPlayer = temp.stream()
                        .filter(p -> p.getStatesTrick() == gameType.ordinal())
                        .collect(Collectors.toList())
                        .get(0);

                team1.add(tempPlayer);

                //alle anderen Spieler ins andere team
                for (Player p : temp) {
                    if (p != tempPlayer) {
                        team2.add(p);
                    }
                }
            }
        }
    }


    @Override
    public void setTrick(Player player, int trick) {

        //prüfen ob Spieler an der Reihe ist
        if (player.equals(currentRound.peek()) == false) {
            System.err.println("Spieler ist nicht am Zug");
            return;
        }

        //Prüfen ob Spieler sein Spiel gewählt hat
        if (currentRound.peek().getStatesTrick() != -1) {
            System.err.println("Spiel wurde bereits gewählt");
            return;
        }

        //Prüfen ob die Spielwahl gültig ist
        if (trick < 0 || trick >= GameType.values().length) {
            System.err.println("Ungültige Auswahl.");
            return;
        }


        //höchsten trick ermitteln; nur das höchste angesagte Spiel wird gespielt
        int max = this.currentRound.stream()
                .max((o1, o2) -> Math.max(o1.getStatesTrick(), o2.getStatesTrick()))
                .get()
                .getStatesTrick();

        //Setzen des gewünschten Spiels beim Player und anfügen ans Ende
        Player temp = this.currentRound.remove();

        //wenn vorherige überboten wurden setzen sonst 0 (normales Spiel)
        temp.setStatesTrick(trick > max ? trick : 0);

        this.currentRound.add(temp);

        System.out.println("Player hat folgendes Spiel gewählt: " + GameType.values()[trick]);

        //checken ob alle Spieler ein Spiel gewählt haben
        assert this.currentRound.peek() != null;
        if (this.currentRound.peek().getStatesTrick() != -1) {

            setGameType();
        }


    }

    private void setGameType() {
        //höchtwertiges gespieltes Spiel finden
        int max = this.currentRound.stream().mapToInt(Player::getStatesTrick).filter(player -> player >= 0).max().orElse(0);
        this.gameType = GameType.values()[max];

        System.out.println("Folgendes Spiel wird gespielt: " + GameType.values()[max]);

        //in allen Decks das Spiel entsprechend einstellen
        this.playedCard.setType(this.gameType);
        this.currentRound.forEach(p -> p.getDeck().setType(this.gameType));

        //1. stich initialisieren
        initializeStich();

        //teams setzen
        setTeams();
    }

    @Override
    public void playCard(Player player, ICard card) {
        Player temp = this.currentRound.peek();
        assert temp != null;

        //checken ob Spieler an der Reihe ist
        if (temp.equals(player) == false) {
            System.err.println("Spieler ist nicht am Zug, er kann keine Karte legen");
            return;
        }

        //checken ob Spiel gewählt wurde
        if (temp.getStatesTrick() == -1) {
            System.err.println("Spieler hat noch kein Spiel ausgewählt");
            return;
        }

        //checken ob Karte gültig ist
        if (temp.getDeck().getDeck().contains((SchafkopfCard) card) == false) {
            System.err.println("Ungültige Karte gespielt; ist nicht auf der Hand");
            return;
        }

        //checken ob Karte spielbar ist
        if (card.isPlayable() == false) {
            System.err.println("Karte kann nicht gespielt werden");
            return;
        }

        System.err.println(card);

        //spieler vom Beginn der Schlange entfernen
        this.currentRound.remove();

        //Karte aus der Hand des Spielers entfernen
        temp.getDeck().remove(card);

        //Karte ausspielen auf Tisch
        this.playedCard.add((SchafkopfCard) card);
        player.setPlayedCard(card);

        //wenn erste Karte gespielt wurde muss für alle Karten angezeigt werden, ob sie spielbar sind
        if (this.playedCard.getDeck().size() == 1) {
            System.out.println("Playable setzen für Spieler");
            this.playedCard.setFirstPlayedCard((SchafkopfCard) card);
            this.players.forEach(p -> p.getDeck().setPlayable((SchafkopfCard) card));
        }

        //countdown für neuen spieler setzen
        this.setCountdownCurrentUser();

        //auswertung wenn jeder spieler eine Karte gelegt hat
        if (this.currentRound.isEmpty()) {
            assesStich();
        }

    }

    @Override
    public ISuit getTrump() {
        return this.playedCard.getTrump();
    }

    @Override
    public List<ICard> getPlayedCards() {
        return new ArrayList<>(this.playedCard.getDeck());
    }


    @Override
    public Player getCurrentUser() {
        return this.currentRound.peek();
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(this.currentRound);
    }

    public void assesStich() {
        System.out.println(this.playedCard);

        //sortieren nach Spiel und erster gespielter Karte
        this.playedCard.sortDeck();

        //höchste Karte finden
        ICard max = this.playedCard.getDeck().get(0);

        //spieler finden der die höchste Karte gelegt hat
        Player temp = this.players.stream()
                .filter(player -> player.getPlayedCard().equals(max))
                .collect(Collectors.toList())
                .get(0);

        //gewinner setzen als lastWinner
        this.lastWinner = temp;

        //Ausgabe des Gewinners und des Stiches
        System.err.println(temp);
        System.err.println(this.playedCard.getDeck());

        //Punkte berechnen die in diesem Stich liegen
        int points = this.playedCard.getDeck().stream()
                .mapToInt(c -> c.getRank().getValue()).sum();

        //Punkt vergeben an Spieler
        temp.addPoint(points);

        //checken ob es der letzte stich war
        if (this.stichCounter == 8) {
            //wenn ja endauswertung
            assessRound();
        } else {
            //wenn nein nächter stich
            initializeStich();
        }
    }

    //TODO neue runde starten und punkte speichern
    private void assessRound() {

        int pointsTeam1 = team1.stream().mapToInt(Player::getPoints).sum();
        int pointsTeam2 = team2.stream().mapToInt(Player::getPoints).sum();

        //umgekehrt bei Ramsch
        if (gameType == GameType.RAMSCH) {
            if (pointsTeam1 < pointsTeam2) {
                System.out.println("Team 1 hat gewonnen");
            } else {
                System.out.println("Team 2 hat gewonnen");
            }
        } else {
            //Team mit mehr Punkten gewinnt
            if (pointsTeam1 > pointsTeam2) {
                System.out.println("Team 1 hat gewonnen");
            } else {
                System.out.println("Team 2 hat gewonnen");
            }
        }
    }

    @Override
    public String toString() {
        return "Schafkopf{\n" +
                "players=" + players + "\n" +
                '}';
    }
}
