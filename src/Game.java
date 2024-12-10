import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Deck d;
    private ArrayList<Player> players;
    private int pot;
    private int currentBet;
    private Scanner input;

    public Game() {
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        d = new Deck(ranks, suits, values);
        input = new Scanner(System.in);
        players = new ArrayList<Player>();
        pot = 0;
        currentBet = 0;
    }

    public static void main(String[] args) {
        Game g1 = new Game();
        g1.playGame();
    }

    public void playGame() {
        printInstructions();
        System.out.println("How many players would you like: ");
        int playerCount = input.nextInt();
        input.nextLine();
        for (int i = 0; i < playerCount; i++) {
            System.out.println("Name: ");
            players.add(new Player(input.nextLine()));
        }
        deal(2);
        roundBetting();
        for (int i = 0; i < 3; i++) {
            for (Player player: players) {
                player.setStatus(true);
            }
            deal(1);
            roundBetting();
        }
        double winner = printWinner();
        for (Player player: players) {
            if (winner == handValue(player.getHand())) {
                System.out.println(player.getName() + " wins!");
                player.addPoints(pot);
                pot = 0;
            }
        }
    }

    public void printInstructions() {
        System.out.println("Welcome to 5 Card Stud!");
        System.out.println("You will be given 1 card face down and four cards face up");
        System.out.println("After the first two cards and each subsequent card will be a round of betting");
    }

    public void deal(int num) {
        for (Player player: players) {
            player.addCard(d.deal());
        }
    }

    public void roundBetting() {
        int activePlayers = players.size();
        boolean roundInProgress = true;
        int checks = 0;
        while (roundInProgress) {
            roundInProgress = false;
            checks = 0;
            for (Player currentPlayer : players) {
                if (currentPlayer.inRound()) {
                    System.out.println(currentPlayer.getName() + ", it's your turn.");
                    System.out.println(currentPlayer.getHand());
                    System.out.println("Current bet: " + currentBet + ", Pot: " + currentBet);
                    System.out.println("Your balance: " + currentPlayer.getPoints());
                    System.out.println("Options: 1) Call 2) Raise 3) Fold 4) Check");
                    int choice = input.nextInt();
                    if (choice == 1) {
                        if (currentPlayer.checkPoints(currentBet)) {
                            currentBet += currentBet;
                            currentPlayer.addPoints(-currentBet);
                        }
                    }
                    else if (choice == 2) {
                        System.out.println("Enter your raise amount: ");
                        int raiseAmount = input.nextInt();
                        int totalBet = currentBet + raiseAmount;
                        if (currentPlayer.checkPoints(totalBet)) {
                            currentBet = totalBet;
                            currentPlayer.addPoints(-currentBet);
                            pot += currentBet;
                            roundInProgress = true;
                        }
                        else {
                            System.out.println("Insufficient Funds :(");
                        }
                    }
                    else if (choice == 3) {
                        System.out.println(currentPlayer.getName() + " folds.");
                        currentPlayer.setStatus(false);
                        activePlayers--;
                    }
                    else if (choice == 4) {
                        System.out.println(currentPlayer.getName() + " checks.");
                        checks++;
                    }
                }
            }
            if (activePlayers == 1 || checks == activePlayers) {
                roundInProgress = false;
            }
        }
    }

    public double printWinner() {
        double max = 0;
        for (Player player: players) {
            if (max < handValue(player.getHand())) {
                max = handValue(player.getHand());
            }
        }
        return max;
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
        return 0.01 * getHighCard(hand);
    }

    public boolean checkRoyalFlush(ArrayList<Card> hand) {
        return checkStraightFlush(hand) && getHighCard(hand) == 0.14;
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
        return !checkDupes(hand, 2, 0) && (max - min == 4);
    }

    public boolean checkDupes(ArrayList<Card> hand, int num1, int num2) {
        int[] valueCounts = new int[14];
        for (Card card : hand) {
            valueCounts[card.getValue() - 1]++;
        }
        boolean criteria1 = false;
        boolean criteria2 = false;
        for (int count : valueCounts) {
            if (count == num1) {
                criteria1 = true;
            } else if (count == num2) {
                criteria2 = true;
            }
        }
        return criteria1 && criteria2;
    }

    public double suitValue(String suit) {
        if (suit.equals("Diamonds")) {
            return 0.001;
        }
        else if (suit.equals("Clubs")) {
            return 0.002;
        }
        else if (suit.equals("Hearts")) {
            return 0.003;
        }
        else {
            return 0.004;
        }
    }

    public double getHighCard(ArrayList<Card> hand) {
        int maxValue = 0;
        for (Card card : hand) {
            if (card.getValue() > maxValue) {
                maxValue = card.getValue();
            }
            if (card.getValue() == 1) {
                maxValue = 15;
            }
        }
        return maxValue * 0.01;
    }

    public double getDupeCard(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            for (int j = i + 1; j < hand.size(); j++) {
                if (hand.get(i).getValue() == hand.get(j).getValue()) {
                    return 0.01 * hand.get(i).getValue();
                }
            }
        }
        return 0;
    }
}
