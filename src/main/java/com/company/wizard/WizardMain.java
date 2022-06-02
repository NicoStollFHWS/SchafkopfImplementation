package main.java.com.company.wizard;

import main.java.com.company.template.Player;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.wizard.cards.CardWizard;
import main.java.com.company.wizard.game.WizardTable;

import java.util.*;

public class WizardMain {
    public static void main(String[] args) {
        /*List<Player> playerList = new ArrayList<>(asList(new Player("Nico"), new Player("Vladi"), new Player("Juli"), new Player("Pako")));
        WizardTable wt = new WizardTable(playerList);
        wt.startGame();
        wt.getTable();
        //playerList.
       // System.out.println("Liegt auf dem Table " +wt.getTable());
*/
        List<Player> playerList = new ArrayList<>(Arrays.asList(new Player("Nico"), new Player("Vladi"), new Player("Juli"), new Player("Pako")));
        WizardTable wt = new WizardTable(playerList);
        wt.getDeck().setTrump();
        wt.cardsLay(3);
        System.out.println(wt.getFirstCard());
        System.out.println(wt.getDeck().getTrump());
        Map<ICard, Player> testTable = new HashMap<>();
        testTable.put( playerList.get(0).getDeck().getCards().get(0), playerList.get(0));
        testTable.put( playerList.get(1).getDeck().getCards().get(0), playerList.get(1));
        testTable.put( playerList.get(2).getDeck().getCards().get(0), playerList.get(2));
        testTable.put( playerList.get(3).getDeck().getCards().get(0), playerList.get(3));
        System.out.println(wt.trickWin(testTable));
        playerList.get(0).setStatesTrick(2);
        playerList.get(0).setWonTrick();
        System.out.println(playerList.get(0).getWonTrick());
        playerList.get(0).setWonTrick();
        System.out.println(playerList.get(0).getWonTrick());
        //playerList.get(0).resetWonTrick();

        wt.setPoints();
        //System.out.println(playerList.get(0).getWonTrick());
        System.out.println(playerList.get(0).getPoints());


        //zum testen des Spiels
        Queue<Player> players = new ArrayDeque<>(playerList);
        WizardTable table = new WizardTable(new ArrayList<>(players));
        Scanner sc = new Scanner(System.in);
        while(true) {
            Player temp = players.peek();
            System.out.println(temp);

            System.out.println("1 - SetTrick \n2- Karte legen");
            int in = sc.nextInt();
            if(in == 1) {
                System.out.println("Anzahl der Stiche die man gewinnen wird angeben");
                int auswahl = sc.nextInt();
                table.setTrick(temp, auswahl);
            } else if(in == 2) {

                //playable beachten
                System.out.println("Karte im Deck wählen, die spielbar ist");
                int auswahl = sc.nextInt();
                table.playCard(temp, temp.getDeck().getCards().get(auswahl));
            }
            players.remove();
            players.add(temp);
        }
    }

}
