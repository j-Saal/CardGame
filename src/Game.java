import java.sql.SQLOutput;

public class Game {
    public static void main(String[] args) {
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds", "Hearts", "Clubs", "Spades", "Diamonds", "Hearts", "Clubs", "Spades", "Diamonds", "Clubs"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        Deck d = new Deck(ranks, suits, values);
        Player p1 = new Player("Jared");
        for (int i = 0; i < 13; i++) {
            p1.addCard(d.deal());
        }
        System.out.println(d.getCardsLeft());
        System.out.println(p1.getHand());
    }
}
