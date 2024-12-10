public class Card {
    // Instance Variables
    private String rank;
    private int value;
    private String suit;

    // Card Constructor
    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.value = value;
        this.suit = suit;
    }

    // Return card name
    public String getRank() {
        return rank;
    }

    // Change card name
    public void setRank(String rank) {
        this.rank = rank;
    }

    // Return card value
    public int getValue() {
        return value;
    }

    // Change card value
    public void setValue(int value) {
        this.value = value;
    }

    // Return card suit
    public String getSuit() {
        return suit;
    }

    // Change card suit
    public void setSuit(String suit) {
        this.suit = suit;
    }

    // Print out card
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}