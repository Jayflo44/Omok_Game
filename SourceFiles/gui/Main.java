package gui;
import model.WebServiceClient;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.json.JSONObject;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;


public class Main {
	 private static WebServiceClient webServiceClient = new WebServiceClient();
	 private static String gameId = null;
	 private static String selectedStrategy = null; // Class-level variable to store the selected strategy
	 private static GameLogPanel gameLogPanel;
	 private static JButton webServiceButton;
	 private static JButton playButton;
	 private static JComboBox<String> strategyComboBox = new JComboBox<>();

	 public static void main(String[] args) {
	        try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
	            e.printStackTrace();
	        }

	        SwingUtilities.invokeLater(() -> {
	        	
	            JFrame frame = new JFrame(Constants.Game_TITLE);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(false); // Initially set the frame as not visible
	            
	            Game game = new Game();
	            BoardPanel boardPanel = new BoardPanel(game);
	            game.setBoardPanel(boardPanel);
	            
	            new OmokGUI(playerChoice -> {
	                SwingUtilities.invokeLater(() -> {
	                    if ("Computer".equals(playerChoice)) {
	                    	
	                        webServiceButton.setEnabled(true);
	                    } else if ("Human".equals(playerChoice)) {
	                    	
	                        webServiceButton.setVisible(false);
	                        playButton.setEnabled(false);
	                    }
	                    // Now that the selection is made, make the main game frame visible

	                    frame.setVisible(true);
	                });
	            });
	            
	            JMenuBar menuBar = new JMenuBar();
	            frame.setJMenuBar(menuBar);
	            addMenuItems(menuBar, game);

	            JToolBar toolBar = new JToolBar();
	            setupToolBar(toolBar, frame, new Game(), new BoardPanel(new Game())); // Initialize toolbar
	            frame.add(toolBar, BorderLayout.PAGE_START);
	            
	            
	            frame.add(boardPanel, BorderLayout.CENTER);
	            frame.setPreferredSize(new Dimension(530, 600));
	            frame.pack();
	            frame.setLocationRelativeTo(null);
	            

	            gameLogPanel = new GameLogPanel(frame); // Initialize GameLogPanel
	            boardPanel.addMoveListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                    if (gameId == null) {
	                        return;
	                    }

	                    int cellSize = Math.min(boardPanel.getWidth(), boardPanel.getHeight()) / game.getBoard().size();
	                    int x = e.getX() / cellSize;
	                    int y = e.getY() / cellSize;

	                    try {
	                        JSONObject response = webServiceClient.makeMove(gameId, y, x);
	                        processMoveResponse(response, game, boardPanel);
	                    } catch (IOException | InterruptedException ex) {
	                       
	                    }
	                }
	            });
	        });
	    }
	       
	
	    private static void addMenuItems(JMenuBar menuBar, Game game) {
	        JMenu menu = new JMenu("Game");
	        menuBar.add(menu);

	        JMenuItem menuItem = new JMenuItem("Play", KeyEvent.VK_P);
	        menuItem.addActionListener(e -> game.startNewGame());
	        menu.add(menuItem);

	        JMenuItem aboutItem = new JMenuItem("About");
	        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "About this game."));
	        menu.add(aboutItem);

	        JMenuItem exitItem = new JMenuItem("Exit");
	        exitItem.addActionListener(e -> System.exit(0));
	        menu.add(exitItem);
	    }

	    private static void setupToolBar(JToolBar toolBar, JFrame frame, Game game, BoardPanel boardPanel) {
	        //Button development
	    	playButton = new JButton("Play");
	        webServiceButton = new JButton("Web Client");
	        JButton settingsButton = new JButton("Settings");
	        JButton logButton = new JButton("Show Game Log");
	        playButton.setEnabled(false);
	        //tool bar Tip 
	        playButton.setToolTipText("Start a new game");
            webServiceButton.setToolTipText("play a game using web services!");
            settingsButton.setToolTipText("Settings.");
	        //tool bar
	        
	        toolBar.add(new JLabel("Strategies: "));
	        toolBar.add(strategyComboBox);
	        //action listeners 
	        strategyComboBox.addActionListener(e -> {
                selectedStrategy = (String) strategyComboBox.getSelectedItem();
                game.setStrategy(selectedStrategy); // Update the game's strategy
            });
	        playButton.addActionListener(e -> {
	            logButton.setEnabled(true);
	            gameLogPanel.clearLog();
	            strategyComboBox.setEnabled(false);
	            try {
	                gameId = webServiceClient.createNewGame(selectedStrategy);
	                game.setGameId(gameId);
	                game.restart();
	                boardPanel.repaint();
	                JOptionPane.showMessageDialog(frame, "New game started with ID: " + gameId);

	            } catch (IOException | InterruptedException ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(frame, "Failed to start a new game", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        });
	        webServiceButton.addActionListener(e -> {	
	            retrieveStrategies(strategyComboBox, game); 
	            playButton.setEnabled(true);
                strategyComboBox.setEnabled(true);

	            
            });
	        logButton.addActionListener(e -> gameLogPanel.showLog());

	        toolBar.add(playButton);
	        toolBar.add(webServiceButton);
	        toolBar.add(settingsButton);
	        toolBar.add(logButton);
	    }

	    private static void retrieveStrategies(JComboBox<String> comboBox, Game game) {
	        try {
	            List<String> strategies = webServiceClient.getStrategies();
	            comboBox.setModel(new DefaultComboBoxModel<>(strategies.toArray(new String[0])));
	            if (!strategies.isEmpty()) {
	                game.setStrategy(strategies.get(0));
	                comboBox.setSelectedItem(strategies.get(0));
	            }
	        } catch (IOException | InterruptedException ex) {
	            JOptionPane.showMessageDialog(null, "Failed to retrieve strategies", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    static class BoardMouseListener extends MouseAdapter {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            if (gameId == null) {
	                return;
	            }
	            BoardPanel boardPanel = (BoardPanel) e.getSource();
	            Game game = boardPanel.getGame();

	            int cellSize = Math.min(boardPanel.getWidth(), boardPanel.getHeight()) / game.getBoard().size();
	            int x = e.getX() / cellSize;
	            int y = e.getY() / cellSize;

	            try {
	                JSONObject response = webServiceClient.makeMove(gameId, y, x);
	                processMoveResponse(response, game, boardPanel);
	            } catch (IOException | InterruptedException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

	    
    private static void processMoveResponse(JSONObject response, Game game, BoardPanel boardPanel) {
        if (response.getBoolean("response")) {
            JSONObject ackMove = response.getJSONObject("ack_move");
            JSONObject move = response.getJSONObject("move");
            game.makeMove(ackMove.getInt("x"), ackMove.getInt("y"));
            game.makeMove(move.getInt("x"), move.getInt("y"));

            // Add log entries using gameLogPanel
            gameLogPanel.addLogEntry("Player move: [" + ackMove.getInt("x") + ", " + ackMove.getInt("y") + "]");
            gameLogPanel.addLogEntry("Computer move: [" + move.getInt("x") + ", " + move.getInt("y") + "]");

            // Check for win or draw
            if (ackMove.getBoolean("isWin") || move.getBoolean("isWin")) {
                // Handle win
                gameLogPanel.addLogEntry((ackMove.getBoolean("isWin") ? "Player" : "Computer") + " Wins!");
                JOptionPane.showMessageDialog(null, "Game Over. Winner: " + (ackMove.getBoolean("isWin") ? "Player" : "Computer"));
                game.restart(); // Reset the game state
            } else if (ackMove.getBoolean("isDraw") || move.getBoolean("isDraw")) {
                // Handle draw
                JOptionPane.showMessageDialog(null, "Game Over. It's a draw!");
                game.restart(); // Reset the game state
                
            }

            // Redraw the board
            boardPanel.repaint();
        } else {
            // Handle error
            String reason = response.getString("reason");
            JOptionPane.showMessageDialog(null, "Error: " + reason);
        }
    }
}
