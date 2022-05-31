package com.company.template.cards;

/**
 * ICard
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public interface ICard {
    ISuit getSuit();
    IRank getRank();
    void setPlayable(boolean playable);
    boolean isPlayable();
    String toString();
    int compareTo(ICard cw);

    //wofür werden die jeweils benötigt
    /**
    void setSuit(String suit);
    String getSuitString();
    int getNumberValue();
    String getNumberVal();
    void setNumber(String number);
    void setNumberValue(int numberValue);
    */

}
