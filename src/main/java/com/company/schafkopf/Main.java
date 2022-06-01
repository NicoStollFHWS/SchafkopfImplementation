package main.java.com.company.schafkopf;

import main.java.com.company.schafkopf.game.Schafkopf;
import main.java.com.company.template.Player;
import main.java.com.company.template.game.Game;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * Main
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
public class Main {

    public static void main(String[] args) {

        Queue<Player> players = new ArrayDeque<>();
        players.add(new Player("Nico"));
        players.add(new Player("Vladi"));
        players.add(new Player("Julian"));
        players.add(new Player("Kevin"));
        Game schafkopf = new Schafkopf(players);


        Scanner sc = new Scanner(System.in);
        while(true) {
            Player temp = players.peek();
            System.out.println(temp);

            System.out.println("1 - Spiel wählen \n2- Karte legen");
            int in = sc.nextInt();
            if(in == 1) {
                System.out.println("0 - NORMAL\n1 - RAMSCH\n2 - SOLO\n4 - WENZ\n5 - GEIER");
                int auswahl = sc.nextInt();
                schafkopf.setTrick(temp, auswahl);
            } else if(in == 2) {

                //playable beachten
                System.out.println("Karte im Deck wählen, die spielbar ist");
                int auswahl = sc.nextInt();
                schafkopf.playCard(temp, temp.getDeck().getDeck().get(auswahl));
            }
            players.remove();
            players.add(temp);
        }

    }
}
