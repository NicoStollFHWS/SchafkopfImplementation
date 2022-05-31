package com.company.schafkopf.cards;

import com.company.schafkopf.game.GameType;
import com.company.template.cards.ICard;
import com.company.template.cards.IDeck;
import com.company.template.cards.ISuit;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 * CardDeck
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public class CardDeck implements IDeck {

    private final List<SchafkopfCard> deck;
    private SchafkopfCard firstPlayedSchafkopfCard = null;
    private ISuit trump = SchafkopfSuit.HERZ;
    private GameType type = GameType.NORMAL;

    public CardDeck() {
        this.deck = new ArrayList<>();

        for(SchafkopfSuit s : SchafkopfSuit.values()) {
            for(SchafkopfRank r : SchafkopfRank.values()) {
                deck.add(new SchafkopfCard(s, r));
            }
        }
    }

    public CardDeck(List<SchafkopfCard> wizardCards) {
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

    public List<SchafkopfCard> getDeck() {
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

    public void setPlayable(SchafkopfCard firstPlayed) {
        long numCardWithSameSuitAsFirstPlayer = this.deck.stream()
                .filter(c -> c.getRank() != SchafkopfRank.QUEEN && c.getRank() != SchafkopfRank.JACK)
                .filter(c -> c.getSuit() == firstPlayed.getSuit()).count();
        switch(this.type) {
            case NORMAL: case RAMSCH: case SOLO:
                setPlayableNormalRamschSolo(numCardWithSameSuitAsFirstPlayer, firstPlayed);
                break;
            case RUF:
                setPlayableRuf(numCardWithSameSuitAsFirstPlayer, firstPlayed);
            case WENZ:
                setPlayableWenz(numCardWithSameSuitAsFirstPlayer, firstPlayed);
                break;
            case GEIER:
                setPlayableGeier(numCardWithSameSuitAsFirstPlayer, firstPlayed);
                break;

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

    private void setPlayableRuf(long numCardsWithSameSuitAsFirstPlayer, SchafkopfCard firstPlayed) {

        boolean containsAceOfRuf = this.deck.stream()
                .filter(wizardCard -> wizardCard.getSuit() == firstPlayed.getSuit() && wizardCard.getRank() == SchafkopfRank.ACE)
                .count() == 1;

        if(containsAceOfRuf) {
            this.deck.forEach(c -> c.setPlayable(c.getSuit() == firstPlayed.getSuit() && c.getRank() == SchafkopfRank.ACE));
        } else {
            setPlayableNormalRamschSolo(numCardsWithSameSuitAsFirstPlayer, firstPlayed);
        }
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
        if(GameType.NORMAL == type || GameType.RAMSCH == type || GameType.RUF == type) {
            return getComparatorNormalRamschRuf();
        } else if(GameType.SOLO == type) {
            //TODO für solo den comparator festlegen --> man braucht von player was der trumpf sein soll
            return new Comparator<>() {
                @Override
                public int compare(SchafkopfCard o1, SchafkopfCard o2) {
                    return 0;
                }
            };
        } else if(GameType.WENZ == type) {
            return getComparatorWenz();
        } else {
            return getComparatorQueen();
        }
    }

    private Comparator<SchafkopfCard> getComparatorNormalRamschRuf() {
        return (o1, o2) -> {
            //dealing with queen
            if (o1.rank == SchafkopfRank.QUEEN || o2.rank == SchafkopfRank.QUEEN) {
                if (o1.rank == SchafkopfRank.QUEEN && o2.rank == SchafkopfRank.QUEEN) {
                    return o1.compareTo(o2);
                } else if (o1.rank == SchafkopfRank.QUEEN) {
                    return 1;
                }
                return -1;
                //handling jack
            } else if (o1.rank == SchafkopfRank.JACK || o2.rank == SchafkopfRank.JACK) {
                if (o1.rank == SchafkopfRank.JACK && o2.rank == SchafkopfRank.JACK) {
                    return o1.compareTo(o2);
                } else if (o1.rank == SchafkopfRank.JACK) {
                    return 1;
                }
                return -1;

                //handling trump
            } else if (o1.suit == trump || o2.suit == trump) {
                if (o1.suit == trump && o2.suit == trump) {
                    return o1.rank.compareTo(o2.rank);
                } else if (o1.suit == trump) {
                    return 1;
                }
                return -1;
            }

            //card is played SchafkopfSuit
            else if (firstPlayedSchafkopfCard != null) {
                if (o1.suit == firstPlayedSchafkopfCard.suit || o2.suit == firstPlayedSchafkopfCard.suit) {
                    if (o1.suit == firstPlayedSchafkopfCard.suit && o2.suit == firstPlayedSchafkopfCard.suit) {
                        return o1.rank.compareTo(o2.rank);
                    } else if (o1.suit == firstPlayedSchafkopfCard.suit) {
                        return 1;
                    }
                    return -1;
                }
            }
            //doesn't matter both wizardCards are worth nothing
            return 0;
        };
    }

    private Comparator<SchafkopfCard> getComparatorWenz() {
        return (o1, o2) -> {

            //handling jack
            if (o1.rank == SchafkopfRank.JACK || o2.rank == SchafkopfRank.JACK) {
                if (o1.rank == SchafkopfRank.JACK && o2.rank == SchafkopfRank.JACK) {
                    return o1.compareTo(o2);
                } else if (o1.rank == SchafkopfRank.JACK) {
                    return 1;
                }
                return -1;
            }

            //card is played SchafkopfSuit
            else if (firstPlayedSchafkopfCard != null) {
                if (o1.suit == firstPlayedSchafkopfCard.suit || o2.suit == firstPlayedSchafkopfCard.suit) {
                    if (o1.suit == firstPlayedSchafkopfCard.suit && o2.suit == firstPlayedSchafkopfCard.suit) {
                        return o1.rank.compareTo(o2.rank);
                    } else if (o1.suit == firstPlayedSchafkopfCard.suit) {
                        return 1;
                    }
                    return -1;
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
                } else if (o1.rank == SchafkopfRank.QUEEN) {
                    return 1;
                }
                return -1;
            }

            //card is played SchafkopfSuit
            else if (firstPlayedSchafkopfCard != null) {
                if (o1.suit == firstPlayedSchafkopfCard.suit || o2.suit == firstPlayedSchafkopfCard.suit) {
                    if (o1.suit == firstPlayedSchafkopfCard.suit && o2.suit == firstPlayedSchafkopfCard.suit) {
                        return o1.rank.compareTo(o2.rank);
                    } else if (o1.suit == firstPlayedSchafkopfCard.suit) {
                        return 1;
                    }
                    return -1;
                }
            }
            //doesn't matter both wizardCards are worth nothing
            return 0;
        };
    }


}
