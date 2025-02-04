package console;
import java.util.Scanner;

import model.Player;

public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI() {
        scanner = new Scanner(System.in);
    }

    public void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public int[] getMove() {
        int[] move = new int[2];
        System.out.print("Enter row (0-14): ");
        move[0] = scanner.nextInt();
        System.out.print("Enter column (0-14): ");
        move[1] = scanner.nextInt();
        scanner.nextLine(); // Consume the rest of the line
        return move;
    }
    public void updateCurrentPlayer(Player currentPlayer) {
        System.out.println("It is now " + currentPlayer.name() + "'s turn.");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public boolean confirmRestart() {
        System.out.print("Play again? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.startsWith("y");
    }
    public boolean askToEndGame() {
        System.out.println("Do you want to end the game? (yes/no): ");
        String input = scanner.nextLine();
        return input.trim().equalsIgnoreCase("yes");
    }

    public void close() {
        scanner.close();
    }
}
