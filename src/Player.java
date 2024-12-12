import java.util.ArrayList;

public class Player {
    // Instance Variables
    private int points;
    private ArrayList<Card> hand;
    private String name;
    private boolean inRound;
    private int bet;

    // Player constructor
    public Player(String name) {
        this.points = 1000;
        this.name = name;
        this.hand = new ArrayList<Card>();
        inRound = true;
        bet = 0;
    }

    // Constructor overloading
    public Player(ArrayList<Card> hand, String name) {
        this.hand = hand;
        this.name = name;
        this.points = 1000;
        inRound = true;
        bet = 0;
    }

    // Return current player's bet
    public int getBet() {
        return bet;
    }

    // Change current player's bet
    public void setBet(int bet) {
        this.bet = bet;
    }

    // Return chips left
    public int getPoints() {
        return points;
    }

    // Return hand
    public ArrayList<Card> getHand() {
        return hand;
    }

    // Return hand (minus first card)
    public ArrayList<Card> getVisibleHand() {
        ArrayList<Card> visibleHand = new ArrayList<Card>(hand);
        visibleHand.remove(0);
        return visibleHand;
    }

    // Return status of player
    public boolean inRound() {
        return inRound;
    }

    public void resetHand() {
        while (hand.size() > 0) {
            hand.remove(0);
        }
    }

    // Change status of player
    public void setStatus(boolean input) {
        this.inRound = input;
    }

    // Return player name
    public String getName() {
        return name;
    }

    // Change chip count
    public void addPoints(int points) {
        this.points += points;
    }

    // Check is bet is valid
    public boolean checkPoints(int bet) {
        return bet < points;
    }

    // Add card to hand
    public void addCard(Card toAdd) {
        hand.add(toAdd);
    }

    // Print player's chips left, name, and hand
    @Override
    public String toString() {
        return name + " has " + points + "\n" + name + "'s cards:" + hand;
    }
}
