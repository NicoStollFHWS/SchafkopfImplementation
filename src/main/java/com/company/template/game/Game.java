package main.java.com.company.template.game;

import main.java.com.company.template.Player;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.ISuit;

import java.util.List;

/**
 * Game
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
public abstract class Game {
    private final long gameStart;
    private long countdownCurrentUser;
    private String stats;

    public Game() {
        this.gameStart = System.currentTimeMillis();
        this.countdownCurrentUser = System.currentTimeMillis();
    }

    abstract public void setTrick(Player player, int trick);
    abstract public void playCard(Player player, ICard card);

    abstract public ISuit getTrump();
    abstract public List<ICard> getPlayedCards();
    abstract public Player getCurrentUser();
    abstract public List<Player> getPlayers();

    public long getGameStart() {
        return gameStart;
    }

    public long getCountdownCurrentUser() {
        return countdownCurrentUser;
    }

    public void setCountdownCurrentUser() {
        this.countdownCurrentUser = System.currentTimeMillis();
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }
}
