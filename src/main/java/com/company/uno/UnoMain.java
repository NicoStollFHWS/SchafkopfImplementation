package main.java.com.company.uno;

import main.java.com.company.template.Player;
import main.java.com.company.uno.game.UnoGame;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * UnoMain
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public class UnoMain {
    public static void main(String[] args) {

        Queue<Player> players = new ArrayDeque<>();
        players.add(new Player("Nico"));
        players.add(new Player("Vladi"));
        players.add(new Player("Julian"));
        players.add(new Player("Kevin"));

        UnoGame uno = new UnoGame(players);
    }
}
