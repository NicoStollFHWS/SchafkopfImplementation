package main.java.com.company.schafkopf.cards;

import main.java.com.company.schafkopf.game.GameType;
import main.java.com.company.template.cards.ICard;
import main.java.com.company.template.cards.IDeck;
import main.java.com.company.template.cards.ISuit;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 * SchafkopfDeck
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public class SchafkopfDeck implements IDeck {

    private final List<SchafkopfCard> deck;
    private SchafkopfCard firstPlayedSchafkopfCard = null;
    private ISuit trump = SchafkopfSuit.HERZ;
    private GameType type = GameType.NORMAL;

    public SchafkopfDeck() {
        this.deck = new ArrayList<>();

        for(SchafkopfSuit s : SchafkopfSuit.values()) {
            for(SchafkopfRank r : SchafkopfRank.values()) {
                deck.add(new SchafkopfCard(s, r));
            }
        }
    }

    public SchafkopfDeck(List<SchafkopfCard> wizardCards) {
        this.deck = wizardCards;
    }

    @Override
    public void add(ICard wizardCard) {
        this.deck.add((SchafkopfCard) wizardCard);
    }

    @Override
    public boolean remove(ICard schafkopfCard) {
        return this.deck.remove((SchafkopfCard) schafkopfCard);
    }

    public List<? extends ICard> getDeck() {
        return this.deck;
    }

    public SchafkopfCard getFirstPlayedCard() {
        return firstPlayedSchafkopfCard;
    }

    public void setFirstPlayedCard(SchafkopfCard playedWizardCard) {
        this.firstPlayedSchafkopfCard = playedWizardCard;
    }

    @Override
    public ISuit getTrump() {
        return trump;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public void setTrump(SchafkopfSuit trump) {
        this.trump = trump;
    }

    public String printCards() {
        AtomicReference<String> out = new AtomicReference<>("");
        this.deck.forEach(c -> out.updateAndGet(v -> v + c));
        return out.toString();
    }

    public void setPlayable(ICard first) {
        SchafkopfCard firstPlayed = (SchafkopfCard) first;
        long numCardWithSameSuitAsFirstPlayer = this.deck.stream()
                .filter(c -> c.getRank() != SchafkopfRank.QUEEN && c.getRank() != SchafkopfRank.JACK)
                .filter(c -> c.getSuit() == firstPlayed.getSuit()).count();
        switch(this.type) {
            case NORMAL: case RAMSCH: case SOLO:
                setPlayableNormalRamschSolo(numCardWithSameSuitAsFirstPlayer, firstPlayed);
                break;
            case WENZ:
                setPlayableWenz(numCardWithSameSuitAsFirstPlayer, firstPlayed);
                break;
            case GEIER:
                setPlayableGeier(numCardWithSameSuitAsFirstPlayer, firstPlayed);
                break;
            default:
                setPlayableNormalRamschSolo(numCardWithSameSuitAsFirstPlayer, firstPlayed);

        }
    }

    private void setPlayableNormalRamschSolo(long numCardWithSameSuitAsFirstPlayer, SchafkopfCard firstPlayed) {
        boolean trumpGespielt = firstPlayed.getSuit().equals(trump);

        this.deck.forEach(c -> {

            //wenn Karten vom typ der ersten Karte sind müssen sie gespielt werden
            //wenn nicht darf jede Karte gespielt werden
            if(numCardWithSameSuitAsFirstPlayer > 0) {
                c.setPlayable(c.getSuit() == firstPlayed.getSuit());
            } else {
                if(trumpGespielt) {
                    c.setPlayable(false);
                } else {
                    c.setPlayable(true);
                }
            }

            //Trümpfe sind immer Spielbar
            if(c.getRank() == SchafkopfRank.QUEEN || c.getRank() == SchafkopfRank.JACK) {
                c.setPlayable(true);
            }
        });
    }

    private void setPlayableWenz(long numCardWithSameSuitAsFirstPlayer, SchafkopfCard firstPlayed) {
        boolean trumpGespielt = firstPlayed.getSuit().equals(trump);

        this.deck.forEach(c -> {

            //wenn Karten vom typ der ersten Karte sind müssen sie gespielt werden
            //wenn nicht darf jede Karte gespielt werden
            if(numCardWithSameSuitAsFirstPlayer > 0) {
                c.setPlayable(c.getSuit() == firstPlayed.getSuit());
            } else {
                if(trumpGespielt) {
                    c.setPlayable(false);
                } else {
                    c.setPlayable(true);
                }
            }

            //Trümpfe sind immer Spielbar
            if(c.getRank() == SchafkopfRank.JACK) {
                c.setPlayable(true);
            }
        });
    }

    private void setPlayableGeier(long numCardWithSameSuitAsFirstPlayer, SchafkopfCard firstPlayed) {
        boolean trumpGespielt = firstPlayed.getSuit().equals(trump);

        this.deck.forEach(c -> {

            //wenn Karten vom typ der ersten Karte sind müssen sie gespielt werden
            //wenn nicht darf jede Karte gespielt werden
            if(numCardWithSameSuitAsFirstPlayer > 0) {
                c.setPlayable(c.getSuit() == firstPlayed.getSuit());
            } else {
                if(trumpGespielt) {
                    c.setPlayable(false);
                } else {
                    c.setPlayable(true);
                }
            }

            //Trümpfe sind immer Spielbar
            if(c.getRank() == SchafkopfRank.QUEEN) {
                c.setPlayable(true);
            }
        });
    }

    @Override
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }


    @Override
    public void sortDeck() {
        Comparator<SchafkopfCard> comp = getComparator(this.type);
        this.deck.sort(comp);
    }

    private Comparator<SchafkopfCard> getComparator(GameType type) {
        if(GameType.NORMAL == type || GameType.RAMSCH == type) {
            return getComparatorNormalRamsch();
        } else if(GameType.SOLO == type) {
            return getComparatorSolo();
        } else if(GameType.WENZ == type) {
            return getComparatorWenz();
        } else if (GameType.GEIER == type){
            return getComparatorQueen();
        } else {
            return getComparatorNormalRamsch();
        }
    }

    private Comparator<SchafkopfCard> getComparatorSolo() {
        return (o1, o2) -> {
            //TODO Comparator schreiben für Solo
            return 0;
        };
    }

    private Comparator<SchafkopfCard> getComparatorNormalRamsch() {
        return (o1, o2) -> {
            //dealing with queen
            if (o1.getRank() == SchafkopfRank.QUEEN || o2.getRank() == SchafkopfRank.QUEEN) {
                if (o1.getRank() == SchafkopfRank.QUEEN && o2.getRank() == SchafkopfRank.QUEEN) {
                    return o1.compareTo(o2);
                } else if (o1.getRank() == SchafkopfRank.QUEEN) {
                    return 1;
                }
                return -1;
                //handling jack
            } else if (o1.getRank() == SchafkopfRank.JACK || o2.getRank() == SchafkopfRank.JACK) {
                if (o1.getRank() == SchafkopfRank.JACK && o2.getRank() == SchafkopfRank.JACK) {
                    return o1.compareTo(o2);
                }
                return (o1.getRank() == SchafkopfRank.JACK) ? 1: -1;

                //handling trump
            } else if (o1.getSuit() == trump || o2.getSuit() == trump) {
                if (o1.getSuit() == trump && o2.getSuit() == trump) {
                    return o1.getRank().compareTo(o2.getRank());
                }
                return (o1.suit == trump)  ?  1 : -1;
            }

            //card is played SchafkopfSuit
            else if (firstPlayedSchafkopfCard != null) {
                if (o1.suit == firstPlayedSchafkopfCard.suit || o2.suit == firstPlayedSchafkopfCard.suit) {
                    if (o1.suit == firstPlayedSchafkopfCard.suit && o2.suit == firstPlayedSchafkopfCard.suit) {
                        return o1.rank.compareTo(o2.rank);
                    }
                    return  (o1.suit == firstPlayedSchafkopfCard.suit) ? 1 : -1;
                }
            }
            //doesn't matter both wizardCards are worth nothing
            return o1.compareTo(o2);
        };
    }

    private Comparator<SchafkopfCard> getComparatorWenz() {
        return (o1, o2) -> {

            //handling jack
            if (o1.rank == SchafkopfRank.JACK || o2.rank == SchafkopfRank.JACK) {
                if (o1.rank == SchafkopfRank.JACK && o2.rank == SchafkopfRank.JACK) {
                    return o1.compareTo(o2);
                }
                return (o1.rank == SchafkopfRank.JACK) ? 1 : -1;
            }

            //card is played SchafkopfSuit
            else if (firstPlayedSchafkopfCard != null) {
                if (o1.suit == firstPlayedSchafkopfCard.suit || o2.suit == firstPlayedSchafkopfCard.suit) {
                    if (o1.suit == firstPlayedSchafkopfCard.suit && o2.suit == firstPlayedSchafkopfCard.suit) {
                        return o1.rank.compareTo(o2.rank);
                    }
                    return (o1.suit == firstPlayedSchafkopfCard.suit) ? 1 : -1;

                }
            }
            //doesn't matter both wizardCards are worth nothing
            return 0;
        };
    }

    private Comparator<SchafkopfCard> getComparatorQueen() {
        return (o1, o2) -> {

            //handling jack
            if (o1.rank == SchafkopfRank.QUEEN || o2.rank == SchafkopfRank.QUEEN) {
                if (o1.rank == SchafkopfRank.QUEEN && o2.rank == SchafkopfRank.QUEEN) {
                    return o1.suit.compareTo(o2.suit);
                }
                return (o1.rank == SchafkopfRank.QUEEN) ? 1 : -1;
            }

            //card is played SchafkopfSuit
            else if (firstPlayedSchafkopfCard != null) {
                if (o1.suit == firstPlayedSchafkopfCard.suit || o2.suit == firstPlayedSchafkopfCard.suit) {
                    if (o1.suit == firstPlayedSchafkopfCard.suit && o2.suit == firstPlayedSchafkopfCard.suit) {
                        return o1.rank.compareTo(o2.rank);
                    }
                    return (o1.suit == firstPlayedSchafkopfCard.suit) ? 1 : -1;
                }
            }
            //doesn't matter both wizardCards are worth nothing
            return 0;
        };
    }


}
