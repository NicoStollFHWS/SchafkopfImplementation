package main.java.com.company.template.cards;

/**
 * IRank
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
public interface IRank {

    int getValue();
    int ordinal();
    int compareTo(IRank rank);
}
