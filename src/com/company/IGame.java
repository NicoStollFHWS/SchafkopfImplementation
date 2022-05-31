package com.company;

public interface IGame {
    void setTrick(Player player, int trick);
    void playCard(Player player, ICard card);
}
