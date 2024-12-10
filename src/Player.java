import java.util.ArrayList;

public class Player {
    private int points;
    private ArrayList<Card> hand;
    private String name;
    private boolean inRound;
    private int bet;

    public Player(String name) {
        this.points = 1000;
        this.name = name;
        this.hand = new ArrayList<Card>();
        inRound = true;
        bet = 0;
    }

    public Player(ArrayList<Card> hand, String name) {
        this.hand = hand;
        this.name = name;
        this.points = 1000;
        inRound = true;
        bet = 0;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getVisibleHand() {
        ArrayList<Card> visibleHand = new ArrayList<Card>(hand);
        visibleHand.remove(0);
        return visibleHand;
    }

    public boolean inRound() {
        return inRound;
    }

    public void setStatus(boolean input) {
        this.inRound = input;
    }

    public String getName() {
        return name;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public boolean checkPoints(int bet) {
        return bet < points;
    }

    public void addCard(Card toAdd) {
        hand.add(toAdd);
    }

    @Override
    public String toString() {
        return name + " has " + points + "\n" + name + "'s cards:" + hand;
    }
}
