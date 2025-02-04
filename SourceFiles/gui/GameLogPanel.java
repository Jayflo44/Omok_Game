package gui;

import javax.swing.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameLogPanel {
    private static final int MAX_LOG_ENTRIES = 1000;
    private JTextArea logTextArea;
    private JDialog logDialog;
    private BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private ExecutorService logExecutor = Executors.newSingleThreadExecutor();

    public GameLogPanel(JFrame parentFrame) {
        logTextArea = new JTextArea(20, 40);
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        logDialog = new JDialog(parentFrame, "Game Log");
        logDialog.add(scrollPane);
        logDialog.pack();
        logDialog.setLocationRelativeTo(parentFrame);

        startLogUpdater();
    }

    public void addLogEntry(String logEntry) {
        if (logQueue.size() >= MAX_LOG_ENTRIES) {
            logQueue.poll(); // Remove oldest entry to make space
        }
        logQueue.offer(logEntry);
    }

    private void updateLogTextArea() {
        if (logDialog.isVisible()) {
            SwingUtilities.invokeLater(() -> {
                while (!logQueue.isEmpty()) {
                    logTextArea.append(logQueue.poll() + "\n");
                }
            });
        }
    }

    private void startLogUpdater() {
        logExecutor.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    updateLogTextArea();
                    Thread.sleep(500); // Update every 500 milliseconds
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    public void clearLog() {
    	SwingUtilities.invokeLater(() -> {
            logTextArea.setText(""); // Clears the log
        });
    }
    public void showLog() {
        logDialog.setVisible(true);
    }

    public void shutdown() {
        logExecutor.shutdownNow();
    }
}
