import java.util.ArrayList;

public class Game {
    private Deck d;
    private Player p1;
    private Player p2;
    private Player p3;

    public Game() {
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        d = new Deck(ranks, suits, values);
        p1 = new Player("Jared");
        p2 = new Player("Amay");
        p3 = new Player("Susan");
    }
    public static void main(String[] args) {
        Game g1 = new Game();
        g1.playGame();
    }

    public void playGame() {
        printInstructions();
        deal(2);
        System.out.println(p1.getHand());
        System.out.println(p2.getHand());
        System.out.println(p3.getHand());
        deal(1);
        System.out.println(p1.getHand());
        System.out.println(p2.getHand());
        System.out.println(p3.getHand());
        deal(1);
        System.out.println(p1.getHand());
        System.out.println(p2.getHand());
        System.out.println(p3.getHand());
        deal(1);
        System.out.println(p1.getHand());
        System.out.println(p2.getHand());
        System.out.println(p3.getHand());
        printWinner(p1.getHand(), p2.getHand(), p3.getHand());
    }

    public void printInstructions() {
        System.out.println("Welcome to 5 Card Stud!");
        System.out.println("You will be given 1 card face down and four cards face up");
    }

    public void deal(int num) {
        for (int i = 0; i < num; i++) {
            p1.addCard(d.deal());
            p2.addCard(d.deal());
            p3.addCard(d.deal());
        }
    }

    public void printWinner(ArrayList<Card> hand1, ArrayList<Card> hand2, ArrayList<Card> hand3) {
        double score1 = handValue(hand1);
        double score2 = handValue(hand2);
        double score3 = handValue(hand3);

        if (score1 > score2 && score1 > score3) {
            System.out.println(p1.getName() + " wins");
        }
        else if (score2 > score3 && score2 > score1) {
            System.out.println(p2.getName() + " wins");
        }
        else {
            System.out.println(p3.getName() + " wins");
        }
    }

    public double handValue(ArrayList<Card> hand) {
        if (checkRoyalFlush(hand)) {
            return 10 + suitValue(hand.get(0).getSuit());
        }
        if (checkStraightFlush(hand)) {
            return 9 + suitValue(hand.get(0).getSuit());
        }
        if (checkDupes(hand, 4, 0)) {
            return 8 + getDupeCard(hand);
        }
        if (checkDupes(hand, 3, 2)) {
            return 7 + getDupeCard(hand);
        }
        if (checkFlush(hand)) {
            return 6 + suitValue(hand.get(0).getSuit());
        }
        if (checkStraight(hand)) {
            return 5 + getHighCard(hand);
        }
        if (checkDupes(hand, 3, 0)) {
            return 4 + getDupeCard(hand);
        }
        if (checkDupes(hand, 2, 2)) {
            return 3 + getHighCard(hand);
        }
        if (checkDupes(hand, 2, 0)) {
            return 2 + getHighCard(hand);
        }
        return getHighCard(hand);
    }

    public boolean checkRoyalFlush(ArrayList<Card> hand) {
        return checkStraightFlush(hand) && getHighCard(hand) == 14;
    }

    public boolean checkStraightFlush(ArrayList<Card> hand) {
        return checkFlush(hand) && checkStraight(hand);
    }

    public boolean checkFlush(ArrayList<Card> hand) {
        String suit = hand.get(0).getSuit();
        for (Card card : hand) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkStraight(ArrayList<Card> hand) {
        int[] nums = new int[5];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 5; i++) {
            nums[i] = hand.get(i).getValue();
            if (nums[i] < min) {
                min = nums[i];
            }
            if(nums[i] > max) {
                max = nums[i];
            }
        }
        return !checkDupes(hand, 2, 0) && (max - min == 5);
    }

    public boolean checkDupes(ArrayList<Card> hand, int num1, int num2) {
        boolean criteria1 = false;
        boolean criteria2 = false;
        for (int i = 0; i < hand.size(); i++) {
            int count = 1;
            for (int j = i + 1; j < hand.size(); j++) {
                if (hand.get(i).getValue() == hand.get(j).getValue()) {
                    count++;
                }
            }
            if (count == num1) {
                criteria1 = true;
            } else if (count == num2) {
                criteria2 = true;
            }
        }
        return criteria1 && criteria2;
    }

    public double suitValue(String suit) {
        if (suit == "Diamonds") {
            return 0.01;
        }
        if (suit == "Clubs") {
            return 0.02;
        }
        if (suit == "Hearts") {
            return 0.03;
        }
        if (suit == "Spades") {
            return 0.04;
        }
        return 0;
    }

    public double getHighCard(ArrayList<Card> hand) {
        int maxValue = 0;
        for (Card card : hand) {
            if (card.getValue() > maxValue) {
                maxValue = card.getValue();
            }
        }
        return maxValue * 0.1;
    }

    public double getDupeCard(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            for (int j = i + 1; j < hand.size(); j++) {
                if (hand.get(i).getValue() == hand.get(j).getValue()) {
                    return 0.1 * hand.get(i).getValue();
                }
            }
        }
        return 0;
    }
}
