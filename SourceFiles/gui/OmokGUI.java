package gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class OmokGUI extends JFrame {
    public OmokGUI(Consumer<String> onSelectionMade) {
        super("Omok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));

        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel display = new JLabel("", SwingConstants.CENTER);
            panel.add(display, BorderLayout.CENTER);

            JPanel northPanel = new JPanel(new BorderLayout());
            JPanel radioPanel = new JPanel();

            radioPanel.add(new JLabel("Select opponent: "));
            JRadioButton humanButton = new JRadioButton("Human");
            JRadioButton compButton = new JRadioButton("Computer");
            ButtonGroup group = new ButtonGroup();
            group.add(humanButton);
            group.add(compButton);
            radioPanel.add(humanButton);
            radioPanel.add(compButton);
            northPanel.add(radioPanel, BorderLayout.NORTH);

            JButton playButton = new JButton("Play");
            northPanel.add(playButton);
            panel.add(northPanel, BorderLayout.NORTH);

            playButton.addActionListener(e -> {
                String selectedOption;
                if (humanButton.isSelected()) {
                    selectedOption = "Human";
                } else if (compButton.isSelected()) {
                    selectedOption = "Computer";
                } else {
                    JOptionPane.showMessageDialog(this, "Please select an opponent to play against.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                    return; // Do not close the window if no selection was made
                }

                display.setText(selectedOption); // Update the label if needed
                JOptionPane.showMessageDialog(this, "You have selected: " + selectedOption, "Opponent Selected", JOptionPane.INFORMATION_MESSAGE);
                OmokGUI.this.dispose(); // Dispose the selection window
                onSelectionMade.accept(selectedOption); // Notify the main class
            });

            setContentPane(panel);
            pack();
            setVisible(true);
        });
    }
}
