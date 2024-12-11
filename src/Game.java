// 5 Card Stud by Jared Saal
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    // Instance Variables
    private Deck d;
    private ArrayList<Player> players;
    private int pot;
    private int currentBet;
    private Scanner input;

    // Game constructor
    public Game() {
        // Initialize normal 52 card deck
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        d = new Deck(ranks, suits, values);
        input = new Scanner(System.in);
        players = new ArrayList<Player>();
        // Set pot and bet to 0
        pot = 0;
        currentBet = 0;
    }

    // Create a new game
    public static void main(String[] args) {
        Game g1 = new Game();
        g1.playGame();
    }

    // Play the game
    public void playGame() {
        // Print instructions for 5 card stud
        printInstructions();
        // Ask for and initialize each player
        setUpPlayers();
        // Deal the first two cards (one face up, one face down)
        deal(2);
        // First round of betting
        roundBetting();
        // Deal a card and bet 3x until hand has 5 cards or someone wins
        for (int i = 0; i < 3; i++) {
            deal(1);
            roundBetting();
        }
        // Calculate winner, print their nam,e and update total chips
        printWinner(0);
    }

    // Prints game instructions
    public void printInstructions() {
        System.out.println("Welcome to 5 Card Stud!");
        System.out.println("You will be given 1 card face down and four cards face up");
        System.out.println("After the first two cards and each subsequent card will be a round of betting");
    }

    // Gets each player name and initializes each
    public void setUpPlayers() {
        System.out.println("How many players would you like: ");
        int playerCount = input.nextInt();
        input.nextLine();
        for (int i = 0; i < playerCount; i++) {
            System.out.println("Name: ");
            players.add(new Player(input.nextLine()));
        }
    }

    // Deals each player in the game the parameter's value cards
    public void deal(int num) {
        // Iterates through each player, ensuring they are in the game and deals them a card
        for (Player player: players) {
            if (player.inRound()) {
                for (int i = 0; i < num; i++) {
                    player.addCard(d.deal());
                }
            }
        }
    }

    public void roundBetting() {
        int activePlayers = 0;
        for (Player player : players) {
            if (player.inRound()) {
                activePlayers++;
            }
        }

        boolean roundInProgress = true;
        int checks = 0;
        int calls = 0;
        currentBet = 0;

        while (roundInProgress) {
            for (Player currentPlayer : players) {
                if (!currentPlayer.inRound()) {
                    continue;
                }

                System.out.println("Cards visible to all on the table: ");
                for (Player player : players) {
                    if (player.inRound()) {
                        System.out.println(player.getName() + "'s hand: " + player.getVisibleHand());
                    }
                }

                System.out.println(currentPlayer.getName() + ", it's your turn.");
                System.out.println("Your Hand: " + currentPlayer.getHand());
                System.out.println("Current bet: " + currentBet + ", Pot: " + pot);
                System.out.println("Your balance: " + currentPlayer.getPoints());
                System.out.println("Options: 1) Call/Check 2) Raise 3) Fold");

                int choice = input.nextInt();

                if (choice == 1) {
                    int callAmount = currentBet - currentPlayer.getBet();
                    if (currentBet == 0) {
                        checks++;
                    }
                    else if (currentPlayer.checkPoints(callAmount)) {
                        currentPlayer.addPoints(-callAmount);
                        currentPlayer.setBet(currentBet);
                        pot += callAmount;
                        calls++;
                    }
                    else {
                        System.out.println("Insufficient funds: Folded");
                        currentPlayer.setStatus(false);
                        activePlayers--;
                        printWinner(activePlayers);
                    }
                }
                else if (choice == 2) {
                    System.out.println("Enter your raise amount: ");
                    int raiseAmount = input.nextInt();
                    int totalBet = currentBet + raiseAmount;
                    if (currentPlayer.checkPoints(totalBet)) {
                        int raiseAmountToAdd = totalBet - currentPlayer.getBet();
                        currentPlayer.addPoints(-raiseAmountToAdd);
                        currentPlayer.setBet(totalBet);
                        pot += raiseAmountToAdd;
                        currentBet = totalBet;
                        calls = 1;
                        checks = 0;
                    }
                    else {
                        System.out.println("Insufficient funds: Folded");
                        currentPlayer.setStatus(false);
                        activePlayers--;
                        printWinner(activePlayers);
                    }
                }
                else if (choice == 3) {
                    System.out.println(currentPlayer.getName() + " folds.");
                    currentPlayer.setStatus(false);
                    activePlayers--;
                    printWinner(activePlayers);
                }
                else {
                    System.out.println("Invalid choice. Please try again.");
                }

            }

            if (checks == activePlayers || calls == activePlayers) {
                System.out.println("Betting round complete. Dealing next card...");
                roundInProgress = false;
            }
        }
    }

    // Checks the winning scenario
    public void printWinner(int playersLeft) {
        // If one player is left (case where all others have folded) it will add the pot to their total
        if (playersLeft == 1) {
            for (Player player: players) {
                if (player.inRound()) {
                    System.out.println(player.getName() + " wins!");
                    player.addPoints(pot);
                    pot = 0;
                    return;
                }
            }
        }
        // Input is 0 when more than 1 player is left and all betting rounds are complete - in this case find the
        // winning hand and score accordingly
        else if (playersLeft == 0){
            double max = 0;
            Player winner = null;
            for (Player player: players) {
                if (player.inRound() && max < handValue(player.getHand())) {
                    max = handValue(player.getHand());
                    winner = player;
                }
            }
            System.out.println(winner.getName() + " wins!");
            winner.addPoints(pot);
            pot = 0;
        }
    }

    // Gives the hand a numerical value - higher value means more powerful hand
    public double handValue(ArrayList<Card> hand) {
        // Checks for royal flush and adds the suit order to value to break ties
        if (checkRoyalFlush(hand)) {
            return 10 + suitValue(hand.get(0).getSuit());
        }
        // Checks for straight flush and adds the suit order to value to break ties
        if (checkStraightFlush(hand)) {
            return 9 + suitValue(hand.get(0).getSuit());
        }
        // Checks for four of a kind and adds value of the repeated card to hand value to break ties
        if (checkDupes(hand, 0, 4) > 0) {
            return 8 + checkDupes(hand, 0, 4);
        }
        // Checks for full house and adds the value of the 3x repeated card to hand value to break ties
        if (checkDupes(hand, 2, 3) > 0) {
            return 7 + checkDupes(hand, 2, 3);
        }
        // Checks for flush and adds suit value to hand value  to break ties
        if (checkFlush(hand)) {
            return 6 + suitValue(hand.get(0).getSuit());
        }
        // Checks for straight and adds high card of hand value to break tiebreakers
        if (checkStraight(hand)) {
            return 5 + getHighCard(hand);
        }
        // Checks for three of a kind and adds 3x repeated number to hand value to break ties
        if (checkDupes(hand, 0, 3) > 0) {
            return 4 + checkDupes(hand, 0, 3);
        }
        // Checks for two pair and adds number of highest card to break tie
        if (checkDupes(hand, 2, 2) > 0) {
            return 3 + checkDupes(hand, 2, 2) + getHighCard(hand);
        }
        // Checks for pair and adds paired card to hand value and high card to hand value to break tie
        if (checkDupes(hand, 0, 2) > 0) {
            return 2 + checkDupes(hand, 0, 2) + getHighCard(hand);
        }
        // If no special hand returns the high card value
        return getHighCard(hand);
    }

    // Checks for royal flush
    public boolean checkRoyalFlush(ArrayList<Card> hand) {
        return checkStraightFlush(hand) && getHighCard(hand) == 0.014;
    }

    // Checks for straight flush
    public boolean checkStraightFlush(ArrayList<Card> hand) {
        return checkFlush(hand) && checkStraight(hand);
    }

    // Checks for flush
    public boolean checkFlush(ArrayList<Card> hand) {
        String suit = hand.get(0).getSuit();
        // If all suits match return true
        for (Card card : hand) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }
        return true;
    }

    // Checks for straight
    public boolean checkStraight(ArrayList<Card> hand) {
        int[] nums = new int[5];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        // Sorts hand smallest to greatest
        for (int i = 0; i < 5; i++) {
            nums[i] = hand.get(i).getValue();
            if (nums[i] < min) {
                min = nums[i];
            }
            if(nums[i] > max) {
                max = nums[i];
            }
        }
        // If no dupes and final - initial = 4, then straight is present
        return checkDupes(hand, 0, 2) == 0 && (max - min == 4);
    }

    // Checks for dupes
    public double checkDupes(ArrayList<Card> hand, int num1, int num2) {
        // Creates new array of 14 values
        double numA = 0;
        double numB = 0;
        int[] valueCounts = new int[14];
        for (Card card : hand) {
            // Adds card value to array
            valueCounts[card.getValue() - 1]++;
        }
        // Checks for dupes matching input values
        for (int count : valueCounts) {
            // If first criteria hasn't been reaches and numA is 0, check against array
            if (count == num1 && numA == 0) {
                numA = 0.01 * count;
            }
            // If second criteria achieved, set numB to that value
            else if (count == num2) {
                numB = 0.01 * count;
            }
        }
        // Returns second condition (since the bigger number is always inputted second and the array is traversed small
        // to large this will handle both two pair and full house correctly)
        // Handles ace condition
        if (numB == 0.01) {
            return 0.15;
        }
        return numB;
    }

    // Checks suit value and returns
    public double suitValue(String suit) {
        // Weakest tiebreaker suit so adds smallest value
        if (suit.equals("Diamonds")) {
            return 0.0001;
        }
        else if (suit.equals("Clubs")) {
            return 0.0002;
        }
        else if (suit.equals("Hearts")) {
            return 0.0003;
        }
        // Strongest suit to adds highest value
        else if (suit.equals("Spaces")){
            return 0.0004;
        }
        return 0;
    }

    // Checks high card and returns
    public double getHighCard(ArrayList<Card> hand) {
        int maxValue = 0;
        // Find the highest card in hand
        for (Card card : hand) {
            if (card.getValue() > maxValue) {
                maxValue = card.getValue();
            }
            if (card.getValue() == 1) {
                maxValue = 15;
            }
        }
        return maxValue * 0.001;
    }
}
