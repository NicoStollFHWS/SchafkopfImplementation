package main.java.com.company.wizard.cards;

import main.java.com.company.schafkopf.game.GameType;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IDeck;
import main.java.com.company.template.cards.ISuit;

import java.util.*;

//TODO first played in sort einfügen
public class WizardDeck implements IDeck {
    private List<ICard> cards =  new ArrayList<>();
    private ICard trump;
    private ICard first = null;

    public WizardDeck() {
        for (Suit suit : Suit.values()) {
            for (RankWizard number : RankWizard.values()) {
                cards.add(new CardWizard(number, suit));
            }
        }
    }

    public WizardDeck(List<ICard> wizardCards) {
        this.cards = wizardCards;
    }

    public ICard setTrump()
    {
        shuffleDeck();
        trump = deal();

        //TODO Player should choose

        return trump;
    }

    public ICard setTrump(ICard trump)
    {

        return this.trump = trump;
        //TODO Player should choose

        //return trump;
    }

    @Override
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    @Override
    public ICard deal() {
        return cards.remove(0);
    }

    @Override
    public void burn() {
        cards.remove(0);
    }

    @Override
    public String toString()
    {
        return cards.toString();
    }

    @Override
    public ISuit getTrump() {
        return trump.getSuit();
    }

    public void sortDeck()
    {
        this.cards.sort((card1, card2) -> {
            //Logik fÃ¼r wizard
            if (card1.getRank() == RankWizard.WIZARD && card2.getRank() == RankWizard.WIZARD) return 1;
            if (card1.getRank() == RankWizard.WIZARD) return 1;
            if (card2.getRank() == RankWizard.WIZARD) return -1;

            //Logik for JESTER
            if (card1.getRank() == RankWizard.JESTER && card2.getRank() == RankWizard.JESTER) return 1;
            if (card1.getRank() == RankWizard.JESTER) return -1;
            if (card2.getRank() == RankWizard.JESTER) return 1;

            //Logik for trump
            if (card1.getSuit().equals(trump.getSuit()) && card2.getSuit().equals(trump.getSuit())) {
                if (card1.getRank().ordinal() > card2.getRank().ordinal()) return 1;
                else return -1;
            }
            if (card1.getSuit().equals(trump.getSuit())) return 1;
            if (card2.getSuit().equals(trump.getSuit())) return -1;

            //compareTo
            return card1.compareTo(card2);

        });
    }

    @Override
    public void add(ICard card) {
        //TODO
    }

    @Override
    public boolean remove(ICard card) {
        //TODO
        return false;
    }

    @Override
    public List<ICard> getCards() {
        //TODO
        return null;
    }

    @Override
    public String printCards() {
        //TODO
        return null;
    }

    @Override
    public void setPlayable(ICard first) {
        //TODO

    }

    @Override
    public void setType(GameType gameType) {
        //TODO
    }

    @Override
    public void setFirstPlayedCard(ICard card) {
        this.first = card;
    }


    public static void main(String[] args) {
        WizardDeck deck = new WizardDeck();
        //deck.shuffleDeck();
        System.out.println(deck);
        deck.setTrump();
        System.out.println(deck.getTrump());
        deck.sortDeck();
        System.out.println(deck);
    }

    public List<ICard> setCards(List<ICard> iCardList) {
        return this.cards = iCardList;

    }
}

