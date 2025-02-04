package model;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
*@author andres chavira
*@author Joshua Flores
*BoardTest class that contains JUnit tests for the Board class.
*
*/

public class BoardTest {
    private Board board;
    private Player player1;
    private Player player2;
    /**
     *setUp method is to initialize test enviorment before each test
     */

    @BeforeEach
    public void setUp() {
        board = new Board(15);
        player1 = new Player("Player 1", 'X', Color.BLACK);
        player2 = new Player("Player 2", 'O', Color.WHITE);
        board.initializeBoard();
    }

    /**
     * Here we test and verify the initialization of the board as well as stonePlacement.
     *
     */
    @Test
    public void testInitializeBoard() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertFalse(board.isOccupied(i, j));
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertTrue(board.isOccupied(i, j));
            }
        }
        //Multiple attempts after clearing board.
        board.clear();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertFalse(board.isOccupied(i, j));
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertTrue(board.isOccupied(i, j));
            }
        }
        board.clear();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertFalse(board.isOccupied(i, j));
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertTrue(board.isOccupied(i, j));
            }
        }
        board.clear();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertFalse(board.isOccupied(i, j));
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertTrue(board.isOccupied(i, j));
            }
        }

    }

    /**
     * Here we verify the whole board occupation of one players stone.
     */
    @Test
    public void testOccupyWholeWithOnePlayer(){
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertTrue(board.board[i][j] == 'X');
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertFalse(board.board[i][j] == 'O');
            }
        }
        board.clear();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player2);
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertTrue(board.board[i][j] == 'O');
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertFalse(board.board[i][j] == 'X');
            }
        }
    }

    /**
     * Here we test and verify that an occupied spot on the board cannot be overwritten by the opposing player.
     */
    @Test
    public void testOccupiedPositionCannotBeOverwritten() {
        int occupiedRow = 2;
        int occupiedCol = 2;

        // Place the first stone for player1
        board.placeStone(occupiedRow, occupiedCol, player1);
        assertTrue(board.isOccupiedBy(occupiedRow, occupiedCol, player1));

        // Attempt to place another stone (from player2) at the same position
        try {

        } catch (IllegalStateException e) {
            // This block will be executed if an IllegalStateException is thrown.
            // If not, the fail() method call will cause the test to fail.
            assertEquals("Position is already occupied.", e.getMessage());
        }

        // Additional assertions to ensure the position remains occupied by player1
        assertTrue(board.isOccupiedBy(occupiedRow, occupiedCol, player1));
        assertFalse(board.isOccupiedBy(occupiedRow, occupiedCol, player2));
    }

    /**
     * Here we are testing that only player ones position is filled and the rest of the board is empty.
     */
    @Test
    public void testSinglePositionOccupiedByPlayerOne() {
        int occupiedRow = 1;
        int occupiedCol = 1;
        board.placeStone(occupiedRow, occupiedCol, player1);
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (i == occupiedRow && j == occupiedCol) {
                    assertTrue(board.isOccupiedBy(i, j, player1));
                } else {
                    assertFalse(board.isOccupied(i, j));
                }
            }
        }
        board.clear();
        board.placeStone(occupiedRow, occupiedCol, player2);
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (i == occupiedRow && j == occupiedCol) {
                    assertTrue(board.isOccupiedBy(i, j, player2));
                } else {
                    assertFalse(board.isOccupied(i, j));
                }
            }
        }
    }

    /**
     * Here we are testing the occupation or position of a each player on the board.
     */
    @Test
    public void testOccupyPostionByEachPlayer() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (i % 2 == 0) {
                    board.placeStone(i, j, player1);
                } else {
                    board.placeStone(i, j, player2);
                }
            }
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (i % 2 == 0) {
                    assertTrue(board.board[i][j] == 'X');
                } else {
                    assertTrue(board.board[i][j] == 'O');
                }
            }
        }
    }

    /**
     * Verification test of occupation of a single position taken by a player on the board.
     */
    @Test
    public void testOccupyOnePosition(){
        board.placeStone(0, 0, player1);
        assertTrue(board.isOccupied(0,0));
        assertTrue(board.board[0][0] == 'X');
        for (int i = 1; i < board.size(); i++) {
            for (int j = 1; j < board.size(); j++) {
                assertTrue(board.isEmpty(i, j));
            }
        }//clear board occupy one position by player 2
        board.clear();
        board.placeStone(0, 0, player2);
        assertTrue(board.isOccupied(0,0));
        assertTrue(board.board[0][0] == 'O');
        for (int i = 1; i < board.size(); i++) {
            for (int j = 1; j < board.size(); j++) {
                assertTrue(board.isEmpty(i, j));
            }
        }
    }

    /**
     * Here we are testing the size of the Board.
     */
    @Test
    public void testSize() {
        assertEquals(15, board.size());
        assertFalse(board.size() == 14);
        assertFalse(board.size() == 1);
        assertFalse(board.size() == 16);
    }

    /**
     * Testing to make sure the players are valid and implemented correctly.
     */
    @Test
    public void testPlayerIsValid() {
        assertEquals("Player 1", player1.name());
        assertEquals('X', player1.getStone());
        assertEquals("Player 2", player2.name());
        assertEquals('O', player2.getStone());
        assertFalse("Player 1" == player2.name());
        assertFalse('X' == player2.getStone());
        assertFalse("Player 2" == player1.name());
        assertFalse('O'== player1.getStone());
    }

    /**
     * Testing to ensure .clear() is clearing the board.
     */
    @Test
    public void testClear() {
        board.placeStone(0, 0, player1);
        board.clear();
        assertTrue(board.isEmpty(0, 0));
        board.placeStone(1, 0, player2);
        board.clear();

        assertTrue(board.isEmpty(1, 0));
        assertTrue(board.isEmpty(0, 1));
        assertTrue(board.isEmpty(0, 0));
        assertTrue(board.isEmpty(1, 1));

    }

    /**
     *Test multiple GameBoards to ensure and check fullness of the GameBoard.
     */
    @Test
    public void testIsFull() {
        assertFalse(board.isFull());
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        assertTrue(board.isFull());
        board.clear();
        assertFalse(board.isFull());
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player2);
            }
        }
        assertTrue(board.isFull());
    }

    /**
     * Test to ensrue the correct exception message is raised when a player chooses an occupied spot.
     */
    @Test
    public void testMultipleStonesSameSpotExceptionMessage() {
        board.placeStone(0, 0, player1);
        Exception exception = assertThrows(IllegalStateException.class, () -> board.placeStone(0, 0, player2));
        assertTrue(exception.getMessage().contains("Position is already occupied."));
    }

    /**
     * Test how the Board reacts if we place a stone on every corner.
     */
    @Test
    public void testAllCorners() {
        board.placeStone(0, 0, player1);
        board.placeStone(0, board.size() - 1, player1);
        board.placeStone(board.size() - 1, 0, player1);
        board.placeStone(board.size() - 1, board.size() - 1, player1);

        assertEquals(player1, board.playerAt(0, 0));
        assertEquals(player1, board.playerAt(0, board.size() - 1));
        assertEquals(player1, board.playerAt(board.size() - 1, 0));
        assertEquals(player1, board.playerAt(board.size() - 1, board.size() - 1));
    }

    /**
     * test how the game board behaves after it has been initialized again.
     */
    @Test
    public void testReInitialization() {
        board.placeStone(0, 0, player1);
        board.initializeBoard();
        assertFalse(board.isOccupied(0, 0));
        board.placeStone(0, 0, player2);
        board.initializeBoard();
        assertFalse(board.isOccupied(0, 0));
        board.placeStone(1, 0, player1);
        board.initializeBoard();
        assertFalse(board.isOccupied(1, 0));
        board.placeStone(1, 0, player2);
        board.initializeBoard();
        assertFalse(board.isOccupied(1, 0));
    }

    /**
     * Test to see the board deosnt declare a winner on a emtpy board.
     */
    @Test
    public void testNoWinForEmptyBoard() {
        assertFalse(board.isWonBy(player1));
        assertFalse(board.isWonBy(player2));
    }

    /**
     * Test to see when the board is full and there is no winning col,row or diag that no winner should be
     * declared.
     */
    @Test
    public void testBoardFullWithoutWin() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        assertTrue(board.isFull());
        board.clear();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (i % 2 == 0) {
                    board.placeStone(i, j, player1);
                } else {
                    board.placeStone(i, j, player2);
                }
            }
        }
        assertTrue(board.isFull());
    }

    /**
     * TEst to check for non-Winning conditions when stones are placed all in a sequential manner.
     */
    @Test
    public void testInvaildWinCondition() {
        for (int i = 0; i< 4; i++){
            board.placeStone(i,0,player1);
        }
        assertFalse(board.isWonBy(player1));
    }

    /**
     * Test how the board behaves after being cleared and there has been a winner declared before the clearing.
     */
    @Test
    public void testBoardStateAfterClearWithWin() {
        for (int i = 0; i < 5; i++) {
            board.placeStone(i, 0, player1);
        }
        assertTrue(board.isWonBy(player1));
        board.clear();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                assertFalse(board.isOccupied(i, j));
            }
        }
        assertFalse(board.isWonBy(player1));
    }

    /**
     * Here we are testing when a null player trys to place stone.
     */
    @Test
    public void testPlaceNullPlayer() {
        assertThrows(NullPointerException.class, () -> board.placeStone(0, 0, null));
    }

    /**
     * Test the Board on specific rows after the board has been cleared.
     */
    @Test
    public void testStateAfterClearSpecific() {
        for(int i =0 ;i < board.size(); i++) {
            board.placeStone(0, i, player1);
        }
        board.clear();
        for(int i = 0; i < board.size(); i++) {
            assertTrue(board.isEmpty(0, i));
        }
        board.placeStone(0, 0, player1);
        board.placeStone(0, 1, player1);
        board.placeStone(0, 2, player1);
        board.clear();
        assertTrue(board.isEmpty(0, 0));
        assertTrue(board.isEmpty(0, 1));
        assertTrue(board.isEmpty(0, 2));
    }

    /**
     * Here we are validating the players by .equals.
     */
    @Test
    public void testPlayerEquality() {
        Player anotherPlayer1 = new Player("Player 1", 'X', Color.BLACK);
        assertTrue(player1.equals(anotherPlayer1));
        assertFalse(player1.equals(player2));
        assertFalse(player2.equals(player1));
        assertTrue(anotherPlayer1.equals(player1));
        assertFalse(anotherPlayer1.equals(player2));
    }
    /**
     * Here we are validating the players of different classes using .equals.
     */
    @Test
    public void testEqualsWithDifferentClass() {
        Player player = new Player("Player 1", 'X', Color.BLACK);
        Object differentObject = new Object();
        assertFalse(player.equals(differentObject));
        assertTrue(player.equals(player));
    }
    /**
     * Test the board size with different sizes on initialization.
     */
    @Test
    public void testBoardInitializationDifferentSizes() {
        for(int i = 0; i < 15; i++) {
            Board smallBoard = new Board(i);
            assertEquals(i, smallBoard.size());
        }
        Board smallBoard = new Board(5);
        assertEquals(5, smallBoard.size());
        Board bboard = new Board(15);
        assertEquals(15, bboard.size());
        Board largeBoard = new Board(14);
        assertEquals(14, largeBoard.size());
    }

    /**
     * Test to see the hashcode for player is consistent.
     */
    @Test
    public void testPlayerHashCode() {
        Player anotherPlayer1 = new Player("Player 1", 'X', Color.BLACK);
        assertEquals(player1.hashCode(), anotherPlayer1.hashCode());
        assertNotEquals(player1.hashCode(), player2.hashCode());
        assertEquals(anotherPlayer1.hashCode(), player1.hashCode());
        assertNotEquals(anotherPlayer1.hashCode(), player2.hashCode());
    }

    /**
     * Here we test the boards behavior with sequential like placement.
     */
    @Test
    public void testSequentialPlacements() {
        // Example: Place a stone, check, remove it, check again, place another stone, and check
        for(int i = 0; i < 5; i++) {
            board.placeStone(i, 0, player1);
            assertTrue(board.isOccupiedBy(i, 0, player1));
        }
        board.clear();
        for(int i = 0; i < 5; i++) {
            assertFalse(board.isOccupied(0, 0));
        }
        board.placeStone(0, 0, player2);
        assertTrue(board.isOccupiedBy(0, 0, player2));
        assertTrue(board.isOccupied(0, 0));
        board.clear();
        for(int i = 0; i < 5; i++) {
            board.placeStone(i, 0, player2);
            assertTrue(board.isOccupiedBy(i, 0, player2));
        }
        board.clear();
        for(int i = 0; i < 5; i++) {
            assertFalse(board.isOccupied(0, 0));
        }
    }

    /**
     * Here we are checking for non winning conditions.
     */
    @Test
    public void testPartialRows() {
        for (int i = 0; i < 4; i++) {
            board.placeStone(i, 0, player1);
        }
        assertFalse(board.isWonBy(player1));
        for (int i = 4; i < 8; i++) {
            board.placeStone(i, 0, player2);
        }
        assertFalse(board.isWonBy(player2));
        board.clear();
        for (int i = 0; i< 4; i++) {
            board.placeStone(i, i, player1);
        }
        assertFalse(board.isWonBy(player1));
        for (int i = 4; i < 8; i++) {
            board.placeStone(i, i, player2);
        }
        assertFalse(board.isWonBy(player2));
        board.clear();
        for (int i = 0; i < 4; i++) {
            board.placeStone(0, i, player1);
        }
        assertFalse(board.isWonBy(player1));
        for (int i = 4; i < 8; i++) {
            board.placeStone(0, i, player2);
        }
        assertFalse(board.isWonBy(player2));
    }
    /**
     * Test the games behavior after there is a win by a player.
     */
    @Test
    public void testAfterWinCondition() {
        for(int i = 0; i < 5; i++) {
            board.placeStone(i, 0, player1);
        }
        assertTrue(board.isWonBy(player1));
        board.placeStone(5,0, player1);
        assertTrue(board.isWonBy(player1));
    }

    /**
     * test the placement of the stone is accurate in terms of what player and position.
     */
    @Test
    public void testPlaceStone() {
        board.placeStone(0, 0, player1);
        assertTrue(board.isOccupiedBy(0, 0, player1));
        board.placeStone(0,1, player2);
        assertTrue(board.isOccupiedBy(0, 1, player2));
        assertFalse(board.isOccupiedBy(0,1, player1));
        board.placeStone(1, 0, player1);
        assertTrue(board.isOccupiedBy(1, 0, player1));
    }
    //Check out of bounds.

    /**
     * Test for out of bounds placement of stone and make sure the exception is thrown.
     */
    @Test
    public void testPlaceStoneOutOfBounds() {
        int n = board.size();
        assertThrows(IndexOutOfBoundsException.class, () -> board.placeStone(-1, 0, player1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.placeStone(0, -1, player1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.placeStone(n, 0, player1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.placeStone(0, n, player1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.placeStone(-1, n, player1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.placeStone(n, n, player1));
    }

    /**
     * Test whether a position comes back with the correct state it is in. (empty, X, O)
     */
    @Test
    public void testIsEmpty() {
        assertTrue(board.isEmpty(0, 0));
        board.placeStone(0, 0, player1);
        assertFalse(board.isEmpty(0, 0));
        board.clear();
        //initialize board then clear check if empty.
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                assertFalse(board.isEmpty(i, j));
            }
        }
        board.clear();
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                assertTrue(board.isEmpty(i, j));
            }
        }
    }

    /**
     * Here we test if a player occupys a specific cell.
     */
    @Test
    public void testIsOccupied() {
        assertFalse(board.isOccupied(0, 0));
        board.placeStone(0, 0, player1);
        assertTrue(board.isOccupied(0, 0));
        board.clear();
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                assertTrue(board.isOccupied(i, j));
            }
        }


    }

    /**
     * checks what player is in a specific cell on the Game baord.
     */
    @Test
    public void testPlayerAt() {
        assertNull(board.playerAt(0, 0));
        board.placeStone(0, 0, player1);
        printBoardState();
        System.out.println(player1);
        System.out.println(board.playerAt(0, 0));
        assertEquals(player1, board.playerAt(0, 0));
        for(int i = 1; i < 5 ; i++) {
            board.placeStone(i, 0, player1);
        }
        for(int i = 1; i < 5 ; i++) {
            assertEquals(player1, board.playerAt(i, 0));
        }
        for(int i = 1; i < 5 ; i++) {
            assertNotEquals(player2, board.playerAt(i, 0));
        }
        for(int i = 1; i < 5 ; i++) {
            board.placeStone(0, i, player2);
        }
        for(int i = 1; i < 5 ; i++) {
            assertEquals(player2, board.playerAt(0, i));
        }
        for(int i = 1; i < 5 ; i++) {
            assertNotEquals(player1, board.playerAt(0, i));
        }
    }

    /**
     * Not neccesarily a test but instead a Helper function.
     * To print the current state of the GameBoard.
     */
    @Test
    private void printBoardState() {
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j< board.size(); j++) {
                System.out.print(board.board[i][j] +" ");
            }
            System.out.println();
        }
    }

    /**
     * Ensure Board is catching the exception when initializing a board of negative size.
     */
    @Test
    public void testNegativeBoardSize() {
        assertThrows(IllegalArgumentException.class, () -> new Board(-5));
    }

    /**
     * Here we test the win conditons (horizontal, vertical and diagnol).
     */
    @Test
    public void testWinConditions() {
        // checking horizontal win.
        for(int i = 0; i< 5; i++) {
            board.placeStone(i, 0, player1);
        }
        assertTrue(board.isWonBy(player1));
        board.clear();
        //clear the board then check player2 wins horizontal
        for (int i = 0; i<5; i++) {
            board.placeStone(i,0, player2);
        }
        assertTrue(board.isWonBy(player2));
        board.clear();

        //check vertical win by both players.
        for(int i =0; i<5; i++) {
            board.placeStone(0, i, player1);
        }
        assertTrue(board.isWonBy(player1));
        board.clear();
        for(int i =0; i<5; i++) {
            board.placeStone(0, i, player2);
        }
        assertTrue(board.isWonBy(player2));
        board.clear();

        //check diagonal(top-left bottom-right) win by both players.
        for(int i =0; i<5; i++) {
            board.placeStone(i, i, player1);
        }
        assertTrue(board.isWonBy(player1));
        board.clear();
        for(int i =0; i<5; i++) {
            board.placeStone(i, i, player2);
        }
        assertTrue(board.isWonBy(player2));
        board.clear();

        //check diagonal(bottom-left top-right) win by both players
        for(int i =0; i<5; i++) {
            board.placeStone(i, 4-i, player1);
        }
        assertTrue(board.isWonBy(player1));
        board.clear();

        for(int i =0; i<5; i++) {
            board.placeStone(i,4 - i, player2);
        }
        assertTrue(board.isWonBy(player2));
        board.clear();

    }

    /**
     * Test the Board gives back the correct winning player.
     */
    @Test
    public void testIsWonBy() {
        assertFalse(board.isWonBy(player1));
        //horizontal win by both players
        for(int i = 0; i < 5; i++) {
            board.placeStone(i, 0, player1);
        }
        assertTrue(board.isWonBy(player1));

        board.clear();

        for(int i = 0; i < 5; i++) {
            board.placeStone(i, 0, player2);
        }
        assertTrue(board.isWonBy(player2));

        board.clear();

        // Vertical win by both players
        for(int i = 0; i < 5; i++) {
            board.placeStone(0, i, player1);
        }
        assertTrue(board.isWonBy(player1));

        board.clear();
        for(int i = 0; i < 5; i++) {
            board.placeStone(0, i, player2);
        }
        assertTrue(board.isWonBy(player2));

        board.clear();

        //reverse diagonal win by both
        for(int i = 0; i < 5; i++) {
            board.placeStone(i, board.size() - 1- i, player1);
        }
        assertTrue(board.isWonBy(player1));

        board.clear();

        for(int i = 0; i < 5; i++) {
            board.placeStone(i, board.size() - 1- i, player2);
        }
        assertTrue(board.isWonBy(player2));

        board.clear();

    }

    /**
     * Test when a board is cleared of a previous game with a sepcific cell.
     */
    @Test
    public void testClearSpecificCell() {
        board.placeStone(0, 0, player1);
        assertTrue(board.isOccupied(0, 0));
        board.clear();
        assertFalse(board.isOccupied(0, 0));
    }

    /**
     * Test the boards ability to return the winning row.
     */
    @Test
    public void testWinningRow() {
        assertNull(board.winningRow());
        for(int i = 0; i < 5; i++) {
            board.placeStone(i, 0, player1);
        }
        printBoardState();
        assertTrue(board.isWonBy(player1));
        Iterable<Board.Place> winningRow = board.winningRow();
        for(Board.Place place : winningRow) {
            System.out.println("Winning Place: x=" + place.x + ", y=" + place.y);
            assertEquals(0, place.y);
        }
    }
}

