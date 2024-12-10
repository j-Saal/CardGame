import java.util.ArrayList;

public class Deck {
    // Instance Variables
    private ArrayList<Card> deck;
    private int cardsLeft;

    // Deck constructor
    public Deck(String[] ranks, String[] suits, int[] values) {
        deck = new ArrayList<Card>();
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                deck.add(new Card(ranks[i], suits[j], values[i]));
            }
        }
        cardsLeft = deck.size();
        shuffle();
    }

    // Check if deck is empty
    public boolean isEmpty() {
        return cardsLeft == 0;
    }

    // Return num cards left
    public int getCardsLeft() {
        return cardsLeft;
    }

    // Deal a card
    public Card deal() {
        if (isEmpty()) {
            return null;
        }
        return deck.get(--cardsLeft);
    }

    // Shuffle cards
    public void shuffle() {
        for (int i = cardsLeft - 1; i > 0; i--) {
            Card placeholder = deck.remove(i);
            int random = (int) (i * Math.random());
            deck.add(deck.get(random));
            deck.set(random, placeholder);
        }
    }
}
