package console;

import gui.Game;

public class ConsoleMain {
    private Game game;
    private ConsoleUI ui;

    public ConsoleMain() {
    	
        game = new Game(); // Initialize your game logic here
        ui = new ConsoleUI();

    }

    public void startGame() {
        boolean running = true;
        while (running) {
            ui.printBoard(game.getBoard().getBoardArray()); // Assuming getBoardArray() returns the board matrix
            ui.showMessage("Current player: " + game.getCurrentPlayer().name());
            boolean validMove = false;
            while (!validMove) {
                int[] move = ui.getMove();
                try {
                    game.makeMove(move[0], move[1]);
                    ui.showMessage("Move accepted. It is now " + game.getCurrentPlayer().name() + "'s turn.");
                    validMove = true;
                } catch (IllegalArgumentException e) {
                    ui.showMessage("Invalid move. Try again.");
                }
            }

            if (game.isGameOver()) {
                ui.printBoard(game.getBoard().getBoardArray());
                if (game.getWinner() != null) {
                    ui.showMessage(game.getWinner().name() + " wins!");
                } else {
                    ui.showMessage("The game is a draw.");
                }
                
                running = ui.confirmRestart();
                if (running) {
                    game.restart();
                }
            }
        }
        ui.close();
    }

   
}
