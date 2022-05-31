package main.java.com.company.template.cards;

/**
 * ICard
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public interface ICard {
    ISuit getSuit();
    void setSuit(ISuit suit);
    IRank getRank();
    void setRank(IRank rank);
    void setPlayable(boolean playable);
    boolean isPlayable();
    String toString();
    int compareTo(ICard cw);

    //wofür werden die jeweils benötigt
    /**
    String getSuitString();
    int getNumberValue();
    String getNumberVal();
    void setNumberValue(int numberValue);
    */

}
