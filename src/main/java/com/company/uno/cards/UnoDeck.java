package main.java.com.company.uno.cards;

import main.java.com.company.schafkopf.game.GameType;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IDeck;
import main.java.com.company.template.cards.ISuit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UnoDeck implements IDeck {
    private List<UnoCard> cards;

    public UnoDeck() {
        this.cards = new ArrayList<>();
        for(UnoRank rank : UnoRank.values()) {
            for(UnoSuit suit : UnoSuit.values()) {
                this.cards.add(new UnoCard(suit, rank));
            }
        }
    }

    public UnoDeck(List<UnoCard> cards) {
        this.cards = cards;
    }

    @Override
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    @Override
    public ISuit getTrump() {
        throw new UnsupportedOperationException("Es gibt keinen Trump in UNO");
    }

    @Override
    public void sortDeck() {
        Collections.sort(this.cards, new Comparator<UnoCard>() {
            @Override
            public int compare(UnoCard o1, UnoCard o2) {
                UnoRank rankCard1 = (UnoRank) o1.getRank();
                UnoSuit suitCard1 = (UnoSuit) o1.getSuit();

                UnoRank rankCard2 = (UnoRank) o2.getRank();
                UnoSuit suitCard2 = (UnoSuit) o2.getSuit();

                //nimm vier ist die höchste Kate
                if(rankCard1.equals(UnoRank.TAKE_FOUR) || rankCard2.equals(UnoRank.TAKE_FOUR)) {
                    if(rankCard1.equals(UnoRank.TAKE_FOUR) && rankCard2.equals(UnoRank.TAKE_FOUR)) {
                        return 0;
                    }
                    return rankCard1.equals(UnoRank.TAKE_FOUR) ?  1 : -1;
                }

                //sortieren zuerst nach Farbe und dann nach zahlenwert
                if(suitCard1.equals(suitCard2)) {
                    return rankCard1.ordinal() - rankCard2.ordinal();
                } else {
                    return suitCard1.ordinal() - suitCard2.ordinal();
                }
            }
        });
    }

    @Override
    public void add(ICard card) {
        this.cards.add((UnoCard) card);
    }

    @Override
    public boolean remove(ICard card) {
        return this.cards.remove((UnoCard) card);
    }

    @Override
    public List<? extends ICard> getDeck() {
        return this.cards;
    }

    @Override
    public String printCards() {
        AtomicReference<String> out = new AtomicReference<>("");
        this.cards.forEach(c -> out.updateAndGet(v -> v + c));
        return out.toString();
    }

    @Override
    public void setPlayable(ICard playedCard) {
        UnoRank rankPlayedCard = (UnoRank) playedCard.getRank();
        UnoSuit suitPlayedCard = (UnoSuit) playedCard.getSuit();
        this.cards.forEach(c -> {
            UnoRank rankCard = (UnoRank) c.getRank();
            UnoSuit suitCard = (UnoSuit) c.getSuit();
            //plus 4 und farbe wählen sind immer wählbar
            if(rankCard.equals(UnoRank.TAKE_FOUR) || rankCard.equals(UnoRank.CHOOSE_COLOR)) {
                c.setPlayable(true);

                //gleiche farbe darf auf einander gelegt werden
            } else if(suitPlayedCard.equals(suitCard)) {
                c.setPlayable(true);

                //gleiche zahl darf aufeinander gelegt werden
            } else if(rankCard.equals(rankPlayedCard)) {
                c.setPlayable(true);

            } else {
                c.setPlayable(false);
            }
        });
    }

    @Override
    public void setType(GameType gameType) {
        throw new UnsupportedOperationException("Es gibt keinen Spieltyp in Uno");
    }


}
