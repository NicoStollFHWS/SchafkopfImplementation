package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CardDeck implements IDeck{

    private final List<Card> deck;
    private Card playedCard = null;
    private Suit trump = Suit.HERZ;
    private GameType type = GameType.NORMAL;

    public CardDeck() {
        this.deck = new ArrayList<>();

        for(Suit s : Suit.values()) {
            for(Rank r : Rank.values()) {
                deck.add(new Card(s, r));
            }
        }
    }

    public CardDeck(List<Card> cards) {
        this.deck = cards;
    }

    public void add(Card card) {
        this.deck.add(card);
    }

    public boolean remove(Card card) {
        return this.deck.remove(card);
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    public Card getPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(Card playedCard) {
        this.playedCard = playedCard;
    }

    public Suit getTrump() {
        return trump;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public void setTrump(Suit trump) {
        this.trump = trump;
    }

    protected String printCards() {
        AtomicReference<String> out = new AtomicReference<>("");
        this.deck.forEach(c -> out.updateAndGet(v -> v + c));
        return out.toString();
    }

    protected void setPlayable(Card firstPlayed) {
        long numCardWithSameSuitAsFirstPlayer = this.deck.stream()
                .filter(c -> c.getRank() != Rank.QUEEN && c.getRank() != Rank.JACK)
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

    private void setPlayableNormalRamschSolo(long numCardWithSameSuitAsFirstPlayer, Card firstPlayed) {
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
            if(c.getRank() == Rank.QUEEN || c.getRank() == Rank.JACK) {
                c.setPlayable(true);
            }
        });
    }

    private void setPlayableRuf(long numCardsWithSameSuitAsFirstPlayer, Card firstPlayed) {

        boolean containsAceOfRuf = this.deck.stream()
                .filter(card -> card.getSuit() == firstPlayed.getSuit() && card.getRank() == Rank.ACE)
                .count() == 1;

        if(containsAceOfRuf) {
            this.deck.forEach(c -> c.setPlayable(c.getSuit() == firstPlayed.getSuit() && c.getRank() == Rank.ACE));
        } else {
            setPlayableNormalRamschSolo(numCardsWithSameSuitAsFirstPlayer, firstPlayed);
        }
    }

    private void setPlayableWenz(long numCardWithSameSuitAsFirstPlayer, Card firstPlayed) {
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
            if(c.getRank() == Rank.JACK) {
                c.setPlayable(true);
            }
        });
    }

    private void setPlayableGeier(long numCardWithSameSuitAsFirstPlayer, Card firstPlayed) {
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
            if(c.getRank() == Rank.QUEEN) {
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
        Comparator<Card> comp = getComparator(this.type);
        this.deck.sort(comp);
    }

    private Comparator<Card> getComparator(GameType type) {
        if(GameType.NORMAL == type || GameType.RAMSCH == type || GameType.RUF == type) {
            return getComparatorNormalRamschRuf();
        } else if(GameType.SOLO == type) {
            //TODO für solo den comparator festlegen --> man braucht von player was der trumpf sein soll
            return new Comparator<>() {
                @Override
                public int compare(Card o1, Card o2) {
                    return 0;
                }
            };
        } else if(GameType.WENZ == type) {
            return getComparatorWenz();
        } else {
            return getComparatorQueen();
        }
    }

    private Comparator<Card> getComparatorNormalRamschRuf() {
        return (o1, o2) -> {
            //dealing with queen
            if (o1.rank == Rank.QUEEN || o2.rank == Rank.QUEEN) {
                if (o1.rank == Rank.QUEEN && o2.rank == Rank.QUEEN) {
                    return o1.suit.compareTo(o2.suit);
                } else if (o1.rank == Rank.QUEEN) {
                    return 1;
                }
                return -1;
                //handling jack
            } else if (o1.rank == Rank.JACK || o2.rank == Rank.JACK) {
                if (o1.rank == Rank.JACK && o2.rank == Rank.JACK) {
                    return o1.suit.compareTo(o2.suit);
                } else if (o1.rank == Rank.JACK) {
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

            //card is played Suit
            else if (playedCard != null) {
                if (o1.suit == playedCard.suit || o2.suit == playedCard.suit) {
                    if (o1.suit == playedCard.suit && o2.suit == playedCard.suit) {
                        return o1.rank.compareTo(o2.rank);
                    } else if (o1.suit == playedCard.suit) {
                        return 1;
                    }
                    return -1;
                }
            }
            //doesn't matter both cards are worth nothing
            return 0;
        };
    }

    private Comparator<Card> getComparatorWenz() {
        return (o1, o2) -> {

            //handling jack
            if (o1.rank == Rank.JACK || o2.rank == Rank.JACK) {
                if (o1.rank == Rank.JACK && o2.rank == Rank.JACK) {
                    return o1.suit.compareTo(o2.suit);
                } else if (o1.rank == Rank.JACK) {
                    return 1;
                }
                return -1;
            }

            //card is played Suit
            else if (playedCard != null) {
                if (o1.suit == playedCard.suit || o2.suit == playedCard.suit) {
                    if (o1.suit == playedCard.suit && o2.suit == playedCard.suit) {
                        return o1.rank.compareTo(o2.rank);
                    } else if (o1.suit == playedCard.suit) {
                        return 1;
                    }
                    return -1;
                }
            }
            //doesn't matter both cards are worth nothing
            return 0;
        };
    }

    private Comparator<Card> getComparatorQueen() {
        return (o1, o2) -> {

            //handling jack
            if (o1.rank == Rank.QUEEN || o2.rank == Rank.QUEEN) {
                if (o1.rank == Rank.QUEEN && o2.rank == Rank.QUEEN) {
                    return o1.suit.compareTo(o2.suit);
                } else if (o1.rank == Rank.QUEEN) {
                    return 1;
                }
                return -1;
            }

            //card is played Suit
            else if (playedCard != null) {
                if (o1.suit == playedCard.suit || o2.suit == playedCard.suit) {
                    if (o1.suit == playedCard.suit && o2.suit == playedCard.suit) {
                        return o1.rank.compareTo(o2.rank);
                    } else if (o1.suit == playedCard.suit) {
                        return 1;
                    }
                    return -1;
                }
            }
            //doesn't matter both cards are worth nothing
            return 0;
        };
    }


}
