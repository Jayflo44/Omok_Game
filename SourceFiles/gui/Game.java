package gui;

import java.io.IOException;

import javax.swing.JOptionPane;
import org.json.JSONObject;
import console.ConsoleUI;
import model.Board;
import model.Player;
import model.WebServiceClient;

public class Game {
	private Board board;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private Player winner;
    private boolean gameOver;
    private BoardPanel boardPanel;
    private ConsoleUI consoleUI;
    private String currentStrategy;
    private String gameId;
	private static WebServiceClient webServiceClient = new WebServiceClient();
    
    public Game() {

    	player1 = new Player("Player 1", 'X', Constants.Player1_Color);
        player2 = new Player("Player 2", 'O', Constants.Player2_Color);
        board = new Board(15); // Assuming a board size of 15x15
        currentPlayer = player1; // Player 1 starts
        consoleUI = new ConsoleUI();
        currentStrategy = "Smart";
        boardPanel = new BoardPanel(this);
    }
    //setters and getters 
    public void setBoardPanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }
    public char[][] getBoardArray() {
        return board.getBoardArray();
    }
    public String getStrategy() {
        return currentStrategy;
    }
    public void setStrategy(String strategy) {
        this.currentStrategy = strategy;
    }
    
    public Board getBoard() {
        return board;
    }
    public Player getPlayer1() {
    	return player1;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    //starting a new game
    public void startNewGame() {
    	
        board.clear();
        currentPlayer = player1;
        gameOver = false;
        winner = null;
        boardPanel.clearWinningRow(null);
        
    }
    private void endGame(Player winner) {
        if (winner != null) {
            // Highlight the winning row
        	boardPanel.highlightWinningRow(board.winningRow());
            // Announce winner
            JOptionPane.showMessageDialog(boardPanel, winner.name() + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            gameOver = true;
            startNewGame();
        } else if (board.isFull()) {
            // Announce draw
            JOptionPane.showMessageDialog(boardPanel, "The game is a draw.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            gameOver = true;
            startNewGame();
        }
        // Offer to start a new game or exit
        int response = JOptionPane.showConfirmDialog(boardPanel, "Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            restart(); // This should reset the board and start a new game
        }if (response == JOptionPane.NO_OPTION) {
            System.exit(0); // Exit the application
        }
    }
    
    public void makeMove(int x, int y) {
        if (board.isEmpty(x, y)) {
            board.placeStone(x, y, currentPlayer);
            if (board.isWonBy(currentPlayer)) {
                // Set the winning row in BoardPanel before announcing the win
                 // Ensure the board is repainted with the winning stone
                // Handle win
                endGame(currentPlayer);
            } else if (board.isFull()) {
                // passing null to indicate a draw
                endGame(null);
            } else {
                // Switch to the next player
                switchPlayers();
            }
        } 
    }
    
    private void switchPlayers() {
        // Switch players
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        consoleUI.updateCurrentPlayer(currentPlayer);
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public void restart() {
        board.clear();
        currentPlayer = player1; // Player 1 starts the new game
        winner = null;
        gameOver = false;
        boardPanel.repaint();
    }
    //method used for webClient.
    public void playerMove(int x, int y) {
        try {
            if (gameId == null) {
                throw new IllegalStateException("Game ID is not set. Cannot make a move.");
            }

            if (board.isEmpty(x, y)) {
                // First, make the move on the local board
                board.placeStone(x, y, currentPlayer);
                boardPanel.repaint();

                // Then, send the move to the web service
                JSONObject response = webServiceClient.makeMove(gameId, x, y);

                if (response.getBoolean("response")) {
                    JSONObject ackMove = response.getJSONObject("ack_move");
                    JSONObject computerMove = response.getJSONObject("move");

                    // Handle the response for the player's move
                    handleMoveResponse(ackMove);

                    // Place the computer's move on the board
                    int compX = computerMove.getInt("x");
                    int compY = computerMove.getInt("y");
                    board.placeStone(compX, compY, player2); // Assuming player2 is the computer
                    boardPanel.repaint();

                    // Check if the computer's move resulted in a win or draw
                    handleMoveResponse(computerMove);

                } else {
                    String reason = response.getString("reason");
                    JOptionPane.showMessageDialog(null, "Move rejected: " + reason, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to make move", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleMoveResponse(JSONObject moveResponse) {
        if (moveResponse.getBoolean("isWin")) {
            // Handle win
            endGame(currentPlayer); // currentPlayer might need to be set based on the move response
        } else if (moveResponse.getBoolean("isDraw")) {
            // Handle draw
            endGame(null);
        } else {
            // Continue game
            switchPlayers();
        }
    }

}