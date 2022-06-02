package main.java.com.company.wizard.cards;

import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IRank;
import main.java.com.company.template.cards.ISuit;

import java.util.Comparator;

public class CardWizard implements Comparable<ICard>, ICard {

    private final ISuit suit;
    private IRank number;
    boolean playable;

    public CardWizard(RankWizard number, Suit suit){
        this.number = number;
        this.suit = suit;

    }

    public CardWizard(Suit suit) {
        this.suit = suit;
    }

    public void setNumber(RankWizard number) {
        this.number = number;
    }

    public IRank getNumber() {
        return this.getRank();
    }

    @Override
    public void setPlayable(boolean pb) {
        this.playable = pb;
    }

    public int getNumberValue()
    {
        return number.getValue();
    }

    /****************************************/
    //TODO
    @Override
    public boolean isPlayable() {
        return false;
    }
    /***************************************/

    public int getSuitValue()
    {
        return -1;  //suit.getValue()+ suit" TODO fix
    }

    @Override
    public ISuit getSuit() {
        return suit;
    }

    @Override
    public IRank getRank() {
        //TODO
        return null;
    }

    public String getSuitString() {
        return this.suit.toString();
    }


    @Override
    public String toString(){
        return "" + number + " " + this.suit.toString();
    }

    @Override
    public int compareTo(ICard cw) {
        return Comparator.comparingInt(CardWizard::getSuitValue)
                .thenComparingInt(CardWizard::getNumberValue)
                .compare(this, (CardWizard) cw);
    }
}
