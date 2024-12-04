import java.sql.SQLOutput;

public class Game {
    public static void main(String[] args) {
        playGame();
    }

    public static void playGame() {
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        Deck d = new Deck(ranks, suits, values);
        Player p1 = new Player("Jared");
        Player p2 = new Player("Amay");
        for (int i = 0; i < 10; i++) {
            p1.addCard(d.deal());
            p2.addCard(d.deal());
        }
        System.out.println(p1.getHand());
        System.out.println(p2.getHand());
    }

    public void turn() {

    }
}
