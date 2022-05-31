package com.company.schafkopf.game;

/**
 * GameType
 *
 * @author Vladimir Bauer
 * @since 2022-05-31
 */
public enum GameType {
    NORMAL(0), RAMSCH(1), RUF(2), SOLO(3), WENZ(4), GEIER(5);

    private final int num;

    GameType(int num) {
        this.num = num;
    }

    private int getNum() {
        return this.num;
    }
}
