package main.java.com.company.uno.cards;

import main.java.com.company.schafkopf.game.GameType;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IDeck;
import main.java.com.company.template.cards.ISuit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * UnoDeck
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
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
        Collections.sort(this.cards, (o1, o2) -> {

            //nimm vier ist die höchste Kate
            if(o1.getRank().equals(UnoRank.TAKE_FOUR) || o2.getRank().equals(UnoRank.TAKE_FOUR)) {
                if(o1.getRank().equals(UnoRank.TAKE_FOUR) && o2.getRank().equals(UnoRank.TAKE_FOUR)) {
                    return 0;
                }
                return o1.getRank().equals(UnoRank.TAKE_FOUR) ?  1 : -1;
            }

            if(o1.getRank().equals(UnoRank.CHOOSE_COLOR) || o2.getRank().equals(UnoRank.CHOOSE_COLOR)) {
                if(o1.getRank().equals(UnoRank.CHOOSE_COLOR) && o2.getRank().equals(UnoRank.CHOOSE_COLOR)) {
                    return 0;
                }
                return o1.getRank().equals(UnoRank.CHOOSE_COLOR) ? 1 : -1;
            }

            //sortieren zuerst nach Farbe und dann nach zahlenwert
            if(o1.getSuit().equals(o2.getSuit())) {
                return o1.getRank().ordinal() - o2.getRank().ordinal();
            } else {
                return o1.getSuit().ordinal() - o2.getSuit().ordinal();
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
        this.cards.forEach(c -> {
            UnoRank rankCard = (UnoRank) c.getRank();
            UnoSuit suitCard = (UnoSuit) c.getSuit();
            //plus 4 und farbe wählen sind immer wählbar
            if(rankCard.equals(UnoRank.TAKE_FOUR) || rankCard.equals(UnoRank.CHOOSE_COLOR)) {
                c.setPlayable(true);

                //gleiche farbe darf auf einander gelegt werden
            } else if(playedCard.getSuit().equals(suitCard)) {
                c.setPlayable(true);

                //gleiche zahl darf aufeinander gelegt werden
            } else if(rankCard.equals(playedCard.getRank())) {
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
