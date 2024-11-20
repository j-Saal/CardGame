import java.util.ArrayList;

public class Player {
    private int points;
    private ArrayList<Card> hand;
    private String name;

    public Player(String name) {
        this.points = 0;
        this.hand = new ArrayList<Card>();
    }

    public Player(ArrayList<Card> hand, String name) {
        this.hand = hand;
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void addCard(Card toAdd) {
        hand.add(toAdd);
    }

    @Override
    public String toString() {
        return name + " has " + points + "\n" + name + "'s cards:" + hand;
    }
}
