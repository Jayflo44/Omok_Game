package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Player;
import model.Board.Place;

public class BoardPanel extends JPanel {

    private Game game;
    private JLabel statusLabel = new JLabel("Turn: ");
    private Iterable<Place> winningRow;
    private Player currentPlayer;
    
    public BoardPanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(Constants.Board_Panel_Size, Constants.Board_Panel_Size));
        setLayout(new BorderLayout());
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(Constants.MAIN_FONT); // Set the font
        add(statusLabel, BorderLayout.SOUTH);
        addMouseListener(new BoardMouseListener());
    }
    public void addMoveListener(MouseAdapter mouseListener) {
        this.addMouseListener(mouseListener);
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public Game getGame() {
        return this.game;
    }
    private class BoardMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        	if (game.isGameOver()) {
                return;
            }

            int cellSize = getCellSize();
            int col = e.getX() / cellSize;
            int row = e.getY() / cellSize;
            
            currentPlayer = game.getCurrentPlayer();
           
			statusLabel.setText(getColorName(currentPlayer.getColor()) + "'s turn");

            game.makeMove(row, col);
            repaint();
        }
    }
    public int getCellSize() {
        return Math.min(getWidth(), getHeight()) / game.getBoard().size(); // Assuming getBoard().size() is correct
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoardGrid(g);
        drawStones(g);
        if(winningRow != null){
            highlightWinningStones(g);
        }   
    }
    private void drawBoardGrid(Graphics g) {
    	g.setColor(Constants.Board_Color);
    	g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        // Grid color
        int cellSize = getCellSize();
        for (int i = 0; i < game.getBoard().size(); i++) {
            // Draw the vertical lines
            g.drawLine(i * cellSize, 0, i * cellSize, getHeight());
            // Draw the horizontal lines
            g.drawLine(0, i * cellSize, getWidth(), i * cellSize);
        }
    }
    private String getColorName(Color color) {
		if (color.equals(Color.RED)) {
		    return "Red";
		} else {
		    return "Black";
		}
	}
    public void highlightWinningRow(Iterable<Place> winningRow) {
        this.winningRow = winningRow;
        repaint();
    }
    // New method to highlight the winning stones
    private void highlightWinningStones(Graphics g) {
        if (winningRow == null) {
            return; // No winning row to highlight
        }

        int cellSize = getCellSize();
        g.setColor(Constants.Winning_Row_Color); // Use the color defined for the winning row
        for (Place place : winningRow) {
            int x = place.x * cellSize;
            int y = place.y * cellSize;
            g.fillRect(y, x, cellSize, cellSize); // Draw a filled rectangle over the winning stones
        }
        repaint();
    }
    public void clearWinningRow(Iterable<Place> winningRow) {
        this.winningRow = winningRow;
        repaint();
    }
    private void drawStones(Graphics g) {
        int cellSize = getCellSize();
        for (int row = 0; row < game.getBoard().size(); row++) {
            for (int col = 0; col < game.getBoard().size(); col++) {
                Player player = game.getBoard().playerAt(row, col);
                if (player != null) {
                    g.setColor(player.getColor()); // Use the player's color
                    int x = col * cellSize;
                    int y = row * cellSize;
                    g.fillOval(x + 2, y + 2, cellSize - 4, cellSize - 4);
                }
            }
        }
    }
}