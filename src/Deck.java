import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values) {
        this.cardsLeft = 52;
        for (int i = 0; i < ranks.length; i++) {
            deck.add(new Card(ranks[i], suits[i], values[i]));
        }
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }
    public int getCardsLeft() {
        return cardsLeft;
    }

    public Card deal() {
        if (isEmpty()) {
            return null;
        }
    }
}
