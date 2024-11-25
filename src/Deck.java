import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values) {
        deck = new ArrayList<Card>();
        for (int i = 0; i < ranks.length; i++) {
            deck.add(new Card(ranks[i], suits[i], values[i]));
        }
        cardsLeft = deck.size();
        shuffle();
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
        return deck.get(--cardsLeft);
    }

    public void shuffle() {
        for (int i = cardsLeft - 1; i > 0; i--) {
            Card placeholder = deck.remove(i);
            int random = (int) (i * Math.random());
            deck.add(deck.get(random));
            deck.set(random, placeholder);
        }
    }
}
