package com.company;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Queue<Player> players = new ArrayDeque<>();
        players.add(new Player("Nico"));
        players.add(new Player("Vladi"));
        players.add(new Player("Julian"));
        players.add(new Player("Kevin"));
        Schafkopf schafkopf = new Schafkopf(players);


        Scanner sc = new Scanner(System.in);
        while(true) {
            Player temp = players.peek();
            System.out.println(temp);

            System.out.println("1 - Spiel wählen \n2- Karte legen");
            int in = sc.nextInt();
            if(in == 1) {
                System.out.println("0 - NORMAL\n1 - RAMSCH\n2 - RUF\n3 - SOLO\n4 - WENZ\n5 - GEIER");
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
