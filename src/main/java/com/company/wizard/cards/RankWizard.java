package main.java.com.company.wizard.cards;

import main.java.com.company.template.cards.IRank;

public enum RankWizard implements IRank {
    JESTER, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN, WIZARD;

    public int getValue(){
        return this.ordinal();
    }

    @Override
    public int compareTo(IRank rank) {
        return 0;
    }
}

