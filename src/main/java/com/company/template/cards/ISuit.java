package main.java.com.company.template.cards;

/**
 * ISuit
 *
 * @author Vladimir Bauer
 * @author Nicolas Stoll
 * @since 2022-05-31
 */
public interface ISuit {

    int ordinal();
    int compareTo(ISuit suit);
}
