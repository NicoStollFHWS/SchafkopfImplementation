package main.java.com.company.wizard.game;


import main.java.com.company.template.Player;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IDeck;
import main.java.com.company.template.cards.ISuit;
import main.java.com.company.template.game.Game;
import main.java.com.company.wizard.cards.RankWizard;
import main.java.com.company.wizard.cards.WizardDeck;

import java.util.*;

public class WizardTable extends Game {
    List<Player> listPlayer;        //TODO list und queue player zusammenfassen
    Queue<Player> queuePlayer = new LinkedList<>();
    Queue<Player> inRoundQueuePlayer;
    Map<ICard, Player> table = new HashMap<>(); //TODO kann man umdrehen dann hast du die karten zum vergleichen als key-set
    WizardDeck deck;
    Player currentUser; //TODO ist der erste user in der Queue und deshalb redundant
    Map<Player, Integer> points;
    int runningRound;
    int round;
    int inRoundInt;
    int guessLoop;
    ICard firstCard;
    ICard trump;

    public WizardTable(List<Player> playerList)
    {
        super();
        this.deck = new WizardDeck();
        this.listPlayer = playerList;
        this.round = 60/ listPlayer.size();
        this.inRoundInt = listPlayer.size();
        this.guessLoop = listPlayer.size();
        queuePlayer.addAll(playerList);
        this.inRoundQueuePlayer = queuePlayer;
        this.runningRound = 1;
    }

    public void playCardWT(Player player, ICard card)
    {
        if(player == queuePlayer.peek())
        {
            if(player.getDeck().getCards().contains(card)) {

                player.getDeck().getCards().remove(card);

                if(table.isEmpty()) {
                    firstCard = card;
                    setPlayableAllCards();
                    table.put(card, player);
                } else {
                    //DONE ob 4 Karten auf table sind
                    if(table.size() == listPlayer.size())
                    {
                        trickWin(table).setWonTrick();
                        //TODO kÃ¶nennn wir das benutzen in haupt queue
                        Player temp = trickWin(table);
                        while(temp != queuePlayer.peek())
                        {
                            queuePlayed();
                        }
                    }
                }
            }
            //TODO Speil muss geupdated (NICO)
        }
    }

    public Player trickWin(Map<ICard, Player> table)    //Map<ICard, Player>
    {
        //System.out.println(table.entrySet());
        Player winner = new Player("temp");

        //gespielte Karten einfügen
        List<ICard> cardList = new ArrayList<>(table.keySet());
        WizardDeck tempDeck = new WizardDeck(cardList);
        tempDeck.setTrump(trump);
        tempDeck.setFirstPlayedCard(this.firstCard);

        //gespielte Karten sortieren
        System.out.println("Table without sorts" + cardList);
        tempDeck.sortDeck();
        /**
        for (ICard ic: table.values()) {
            if(!cardList.get(0).getSuit().equals(ic.getSuit()) && (!ic.getSuit().equals(trump.getSuit())) && (!ic.getRank().equals(RankWizard.WIZARD)) && (!ic.getRank().equals(RankWizard.JESTER)))
                cardList.remove(ic);
        }
        tempDeck.setCards(cardList);
        //System.out.println(tempDeck.setCards(cardList));
         */


        //Winnercard nehmen aus liste und als value suchen
        ICard winnerCard = cardList.get(cardList.size()-1);
        System.out.println("Winner card " + winnerCard);

        return table.get(winnerCard);
        /**
        for(Player key: table.keySet())
        {
            if(winnerCard.equals(table.get(key)))
            {
                winner = key;
            }
        }

         */
//        winner = table.entrySet().stream().filter(entry -> winnerCard.equals(entry.getValue()))
//                .findFirst().map(Map.Entry::getKey).orElse(null);
        //System.out.println(tempDeck);
        //System.out.println(winner.getName());
        //return winner;
    }
    /************ setPoints after ends round *****************/
    public void setPoints()//Map<Player, Integer> points
    {
        for(Player p: listPlayer)
        {
            if(p.getWonTrick() == p.getStatesTrick())
            {
                p.setPoints(20 + p.getStatesTrick()*10);
            }
            else if(p.getWonTrick() != p.getStatesTrick())
            {
                p.setPoints(-p.getStatesTrick()*10);
            }
        }
        this.points = points;
    }

    public void guessTrick(Player player, int number)
    {
        if(guessLoop != 0){
            if(player.getStatesTrick() == 0 && player == queuePlayer.peek())
            {
                player.setStatesTrick(number);
                queuePlayed();
            }
            guessLoop--;
        }
    }

    private void setPlayableAllCards(){
        boolean imSet = false;
        //Done setzen fÃ¼r alle playable carsd bezÃ¼glich farbe
        //TODO muss getestet werden
        for(Player p: listPlayer){
            for(ICard ic: p.getDeck().getCards()){
                if(ic.getSuit().equals(firstCard.getSuit())){
                    imSet = true;
                }
            }
            if(imSet == true){
                for(ICard ic: p.getDeck().getCards()){
                    if(ic.getSuit().equals(firstCard.getSuit())){
                        ic.setPlayable(true);
                    }
                    else if(ic.getRank().equals(RankWizard.WIZARD) || ic.getRank().equals(RankWizard.JESTER)){
                        ic.setPlayable(true);
                    }
                    else{
                        ic.setPlayable(false);
                    }
                }
            }
            else {
                for (ICard ic : p.getDeck().getCards()) {
                    ic.setPlayable(true);
                }
            }
        }
    }

    public void startGame()
    {
        startRound();

    }


    private void startRound()
    {
        if(runningRound < round)
        {
            cardsLay(runningRound);
            System.out.println(deck.getTrump());
            //System.out.println(runningRound + " round " + round);
            runningRound++;
            for(Player p: listPlayer)
            {
                p.setStatesTrick(0);
            }
            this.guessLoop = listPlayer.size();
            //Done alle player setTrick == 0;
        }
        inRoundQueuePlayer = queuePlayer;
    }

    //First card in round. This is unchangeable queue
    private void queuePlayed()
    {
        Player tempPlayer = queuePlayer.element();
        queuePlayer.remove(); //Player which played is removed from queue
        queuePlayer.add(tempPlayer); //Player as last on Queue
        //  runningRound++;
    }

    //All the player's cards in the round must be used here.
    //TODO mit Nico ob man das braucht
    private void inRound()
    {
        Player tempPlayerInRound = inRoundQueuePlayer.element();
        if(inRoundInt != 0)
        {
            //TODO
            // if(inRoundQueuePlayer.element().equals(table.winner))
            {

            }
            //Carten in table lÃ¶schen;
            inRoundInt--;
        }
    }

    //Lay cards between players according to runningRound
    public void cardsLay(int runningRound)
    {
        for(int i = 0; i < runningRound; i++)
        {
            deck.shuffleDeck();
            for(Player p: listPlayer)
            {
                ICard ic = deck.deal();
                ic.setPlayable(true);
                p.getDeck().getCards().add(ic);
            }
        }
        trump = deck.setTrump();
    }

    public void setListPlayer(List<Player> listPlayer) {
        this.listPlayer = listPlayer;
    }

    public int getRound(){
        return round;
    }

    public List<Player> getListPlayer() {
        return listPlayer;
    }

    public WizardDeck getDeck() {
        return deck;
    }

    public void setDeck(WizardDeck deck) {
        this.deck = deck;
    }

    public Player getCurrentUser() {
        return currentUser;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }

    public void setCurrentUser(Player currentUser) {
        this.currentUser = currentUser;
    }

    public Map<Player, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Player, Integer> points) {
        this.points = points;
    }

    public int getRunningRound() {
        return runningRound;
    }

    public void setRunningRound(int runningRound) {
        this.runningRound = runningRound;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public void setTrick(Player player, int trick) {

    }

    @Override
    public void playCard(Player player, ICard card) {

    }

    public ISuit getTrump() {
        return trump.getSuit();
    }

    @Override
    public List<ICard> getPlayedCards() {
        return null;
    }

    public void setTrump(ICard trump) {
        this.trump = trump;
    }


    public ICard getFirstCard() {
        return firstCard;
    }

    public void setFirstCard(ICard firstCard) {
        this.firstCard = firstCard;
    }

    //    public List<ICard> getTable() {
//        return table;
//    }
//    public void setTable(List<ICard> table) {
//        this.table = table;
//    }
}

