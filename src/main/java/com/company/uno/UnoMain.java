package main.java.com.company.uno;

import main.java.com.company.template.Player;
import main.java.com.company.template.game.Game;
import main.java.com.company.uno.game.UnoGame;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

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

        Game uno = new UnoGame(players);

        Scanner sc = new Scanner(System.in);
        while(true) {
            Player temp = players.peek();
            System.out.println(temp);

            System.out.println("1 - Farbe Wählen nach '+4' oder 'farbwahl' \n2- Karte legen");
            int in = sc.nextInt();
            if(in == 1) {
                System.out.println("0 - RED\n1 - BLUE\n2 - YELLOW\n3 - GREEN\n");
                int auswahl = sc.nextInt();
                uno.setTrick(temp, auswahl);
            } else if(in == 2) {

                //playable beachten
                System.out.println("Karte im Deck wählen, die spielbar ist");
                int auswahl = sc.nextInt();
                uno.playCard(temp, temp.getDeck().getDeck().get(auswahl));
            }
            players.remove();
            players.add(temp);
        }
    }
}
