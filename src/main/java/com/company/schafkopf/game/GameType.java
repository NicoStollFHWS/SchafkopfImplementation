package main.java.com.company.schafkopf.game;

/**
 * GameType
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
public enum GameType {
    NORMAL(0), RAMSCH(1),  SOLO(2), WENZ(3), GEIER(4);

    private final int num;

    GameType(int num) {
        this.num = num;
    }

    private int getNum() {
        return this.num;
    }
}
