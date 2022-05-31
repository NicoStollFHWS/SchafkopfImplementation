package com.company.template.game;

import com.company.template.Player;
import com.company.template.cards.ICard;

public interface IGame {
    void setTrick(Player player, int trick);
    void playCard(Player player, ICard card);
}
