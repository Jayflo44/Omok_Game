package model;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import gui.Constants;
import gui.Game;
import model.Player;

/**
 * Abstraction of an Omok board, which consists of n x n intersections
 * or places where players can place their stones. The board can be 
 * accessed using a pair of 0-based indices (x, y), where x and y 
 * denote the column and row number, respectively. The top-left 
 * intersection is represented by the indices (0, 0), and the
 * bottom-right intersection is represented by the indices (n-1, n-1).
 */
public class Board {
	private final int size;
	final char[][] board;
	private Game game;
	//player1 = new Player("Player 1", 'X', Color.BLACK);
    //player2 = new Player("Player 2", 'O', Color.WHITE);
	private final Player player1 = new Player("Player 1", 'X', Constants.Player1_Color);
    private final Player player2 = new Player("Player 2", 'O', Constants.Player2_Color);
    private List<Place> lastWinningRow = null;
    /** Create a new board of the default size. */
    public Board(int size) {
    	 if (size < 0 || size > 15) {
             throw new IllegalArgumentException("Board size cannot be negative.");
         }
    	this.size =size;
        this.board = new char[size][size];
        initializeBoard(); 
    }

    /** Create a new board of the specified size. */
    public void initializeBoard() {
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
    	}
    }
    public char[][] getBoardArray() {
        return board;
    }

    /** Return the size of this board. */
    public int size() {
    	return size;
    }
    
    /** Removes all the stones placed on the board, effectively 
     * resetting the board to its original state. 
     */
    public void clear() {
    	initializeBoard();
    }

    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
    	for(int i =0; i < size; i++) {
    		for(int j = 0; j < size; j++){
    			if(board[i][j] == ' ') {//if there is an empty space.
    				return false;
    			}
    		}
    	}
    	return true;
    }
    /**
     * Place a stone for the specified player at a specified 
     * intersection (x, y) on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
    	if (isOccupied(x, y)) {
            throw new IllegalStateException("Position is already occupied.");
    	}
    	if (isEmpty(x, y)) {  // checks if the position is empty
            board[x][y] = player.getStone();
        }
    	
    }
    
    /**
     * Return a boolean value indicating whether the specified 
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
    	return board[x][y] == ' ';
    }
    
    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
    	return board[x][y] != ' ';
    }

    /**
     * Return a boolean value indicating whether the specified 
     * intersection (x, y) on the board is occupied by the given 
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupiedBy(int x, int y, Player player) {
    	if(board[x][y] == player.getStone()) {
    		return true;
    	}
    	return false;
    }//return board[x][y] == player.getStone();
    

    /**
     * Return the player who occupies the specified intersection (x, y) 
     * on the board. If the place is empty, this method returns null.
     * 
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Player playerAt(int x, int y) {
    	char stonePosition = board[x][y];
    	if(stonePosition == player1.getStone()) {
    		return player1;
    	}else if(stonePosition == player2.getStone()) {
    		return player2;
    	}
    	return null;
    }
    /** 
     * Return a boolean value indicating whether the given player 
     * has a winning row on the board. A winning row is a consecutive 
     * sequence of five or more stones placed by the same player in 
     * a horizontal, vertical, or diagonal direction.
     */
    public Iterable<Place> getWinningRow() {
        if (isWonBy(game.getCurrentPlayer())){
            return winningRow();
        }
        return null;
    }
    public boolean isWonBy(Player player) {
    	char stone = player.getStone();
    	for(int i = 0 ; i < size; i++) {
    		for(int j = 0; j < size; j++) {
    			if(board[i][j] == stone) {
    				//Horizontal check
    				if(j<=size - 4 && board[i][j+1] == stone && board[i][j+2] == stone && board[i][j+3] == stone &&board[i][j+4] == stone){
    				    lastWinningRow = Arrays.asList(new Place(i,j), new Place(i,j+1), new Place(i,j+2), new Place(i,j+3), new Place(i,j+4));
                        return true;
    				}
    				//Vertical check
    				if(i <= size - 4 && board[i+1][j] == stone && board[i+2][j] == stone && board[i+3][j] == stone &&board[i+4][j] == stone){
    					lastWinningRow = Arrays.asList(new Place(i,j), new Place(i+1,j), new Place(i+2,j), new Place(i+3,j), new Place(i+4,j));
    					return true;
    				}
    				//Diagonal form top-left to bottom-right check
    				if(i <= size - 4 && j <= size - 4 && board[i+1][j+1] == stone && board[i+2][j+2] == stone && board[i+3][j+3] == stone && board[i+4][j+4] == stone){
    					lastWinningRow = Arrays.asList(new Place(i,j), new Place(i+1, j+1), new Place(i+2,j+2), new Place(i+3,j+3), new Place(i+4, j+4));
    					return true;
    				}
    				if(i <= size - 4 && j >= 3 && board[i+1][j-1] == stone && board[i+2][j-2] == stone && board[i+3][j-3] == stone && board[i+4][j-4] == stone){
    					lastWinningRow = Arrays.asList(new Place(i,j), new Place(i+1,j-1), new Place(i+2,j-2), new Place(i+3,j-3), new Place(i+4,j-4));
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }

    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     * List<Place>. */
    public Iterable<Place> winningRow() {
    	return lastWinningRow;
    }
    

    /**
     * An intersection on an Omok board identified by its 0-based column
     * index (x) and row index (y). The indices determine the position 
     * of a place on the board, with (0, 0) denoting the top-left 
     * corner and (n-1, n-1) denoting the bottom-right corner, 
     * where n is the size of the board.
     */
    public static class Place {
        /** 0-based column index of this place. */
        public final int x;

        /** 0-based row index of this place. */
        public final int y;

        /** Create a new place of the given indices. 
         * 
         * @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         */
        public Place(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // other methods if needed ...
    }
}

/**
 * A player in an Omok game. It holds the name of the player and
 * can be used to identify a specific player throughout the game. 
 * The Player class helps to keep track of the moves made by each 
 * player during the game.
 */
