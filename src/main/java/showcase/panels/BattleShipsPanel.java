package showcase.panels;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BattleShipsPanel extends JPanel {
    private static final int GRID_SIZE = 10;
    private static final int NUM_SHIPS = 5;
    
    private JButton[][] playerGrid = new JButton[GRID_SIZE][GRID_SIZE];
    private JButton[][] computerGrid = new JButton[GRID_SIZE][GRID_SIZE];
    private boolean[][] playerShips = new boolean[GRID_SIZE][GRID_SIZE];
    private boolean[][] computerShips = new boolean[GRID_SIZE][GRID_SIZE];
    private boolean[][] playerHits = new boolean[GRID_SIZE][GRID_SIZE];
    private boolean[][] computerHits = new boolean[GRID_SIZE][GRID_SIZE];
    
    private int playerShipsRemaining = NUM_SHIPS;
    private int computerShipsRemaining = NUM_SHIPS;
    private int shipsPlaced = 0;
    private boolean gameStarted = false;
    
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private Random random = new Random();
    
    public BattleShipsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel title = new JLabel("BattleShips", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        
        // Game area
        JPanel gameArea = new JPanel(new GridLayout(1, 2, 30, 0));
        gameArea.setOpaque(false);
        
        // Player grid
        JPanel playerPanel = createGridPanel("Your Fleet", playerGrid, true);
        gameArea.add(playerPanel);
        
        // Computer grid
        JPanel computerPanel = createGridPanel("Enemy Waters", computerGrid, false);
        gameArea.add(computerPanel);
        
        add(gameArea, BorderLayout.CENTER);
        
        // Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        
        statusLabel = new JLabel("Click on your grid to place " + NUM_SHIPS + " ships", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        
        scoreLabel = new JLabel("Your ships: " + playerShipsRemaining + " | Enemy ships: " + computerShipsRemaining, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        bottomPanel.add(scoreLabel, BorderLayout.SOUTH);
        
        JButton resetButton = new JButton("New Game");
        resetButton.addActionListener(e -> resetGame());
        bottomPanel.add(resetButton, BorderLayout.EAST);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        placeComputerShips();
    }
    
    private JPanel createGridPanel(String title, JButton[][] grid, boolean isPlayer) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(label, BorderLayout.NORTH);
        
        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 2, 2));
        gridPanel.setBackground(new Color(30, 60, 90));
        gridPanel.setBorder(BorderFactory.createLineBorder(new Color(30, 60, 90), 2));
        
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton cell = new JButton();
                cell.setPreferredSize(new Dimension(30, 30));
                cell.setBackground(new Color(100, 150, 200));
                cell.setFocusPainted(false);
                cell.setBorderPainted(false);
                cell.setMargin(new Insets(0, 0, 0, 0));
                
                final int r = row, c = col;
                
                if (isPlayer) {
                    cell.addActionListener(e -> handlePlayerGridClick(r, c));
                } else {
                    cell.addActionListener(e -> handleComputerGridClick(r, c));
                }
                
                grid[row][col] = cell;
                gridPanel.add(cell);
            }
        }
        
        panel.add(gridPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private void handlePlayerGridClick(int row, int col) {
        if (gameStarted) return;
        if (shipsPlaced >= NUM_SHIPS) return;
        if (playerShips[row][col]) return;
        
        playerShips[row][col] = true;
        playerGrid[row][col].setBackground(new Color(50, 50, 60));
        playerGrid[row][col].setText("⚓");
        shipsPlaced++;
        
        if (shipsPlaced == NUM_SHIPS) {
            gameStarted = true;
            statusLabel.setText("All ships placed! Click enemy waters to attack");
        } else {
            statusLabel.setText("Place ship " + (shipsPlaced + 1) + " of " + NUM_SHIPS);
        }
    }
    
    private void handleComputerGridClick(int row, int col) {
        if (!gameStarted) {
            statusLabel.setText("Place your ships first!");
            return;
        }
        if (computerHits[row][col]) {
            statusLabel.setText("Already attacked there!");
            return;
        }
        
        computerHits[row][col] = true;
        
        if (computerShips[row][col]) {
            computerGrid[row][col].setBackground(new Color(200, 50, 50));
            computerGrid[row][col].setText("💥");
            computerShipsRemaining--;
            statusLabel.setText("Hit! Enemy ship destroyed!");
        } else {
            computerGrid[row][col].setBackground(new Color(150, 180, 200));
            computerGrid[row][col].setText("○");
            statusLabel.setText("Miss!");
        }
        
        updateScore();
        
        if (computerShipsRemaining == 0) {
            endGame(true);
            return;
        }
        
        // Computer's turn
        SwingUtilities.invokeLater(this::computerTurn);
    }
    
    private void computerTurn() {
        int row, col;
        do {
            row = random.nextInt(GRID_SIZE);
            col = random.nextInt(GRID_SIZE);
        } while (playerHits[row][col]);
        
        playerHits[row][col] = true;
        
        if (playerShips[row][col]) {
            playerGrid[row][col].setBackground(new Color(200, 50, 50));
            playerGrid[row][col].setText("💥");
            playerShipsRemaining--;
            statusLabel.setText("Computer hit your ship!");
        } else {
            playerGrid[row][col].setBackground(new Color(150, 180, 200));
            playerGrid[row][col].setText("○");
        }
        
        updateScore();
        
        if (playerShipsRemaining == 0) {
            endGame(false);
        }
    }
    
    private void placeComputerShips() {
        int placed = 0;
        while (placed < NUM_SHIPS) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            if (!computerShips[row][col]) {
                computerShips[row][col] = true;
                placed++;
            }
        }
    }
    
    private void updateScore() {
        scoreLabel.setText("Your ships: " + playerShipsRemaining + " | Enemy ships: " + computerShipsRemaining);
    }
    
    private void endGame(boolean playerWon) {
        gameStarted = false;
        if (playerWon) {
            statusLabel.setText("🎉 Victory! You sunk all enemy ships!");
        } else {
            statusLabel.setText("💀 Defeat! The enemy destroyed your fleet!");
        }
        
        // Reveal remaining computer ships
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                if (computerShips[r][c] && !computerHits[r][c]) {
                    computerGrid[r][c].setBackground(new Color(100, 100, 110));
                    computerGrid[r][c].setText("⚓");
                }
            }
        }
    }
    
    private void resetGame() {
        playerShipsRemaining = NUM_SHIPS;
        computerShipsRemaining = NUM_SHIPS;
        shipsPlaced = 0;
        gameStarted = false;
        
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                playerShips[r][c] = false;
                computerShips[r][c] = false;
                playerHits[r][c] = false;
                computerHits[r][c] = false;
                
                playerGrid[r][c].setBackground(new Color(100, 150, 200));
                playerGrid[r][c].setText("");
                computerGrid[r][c].setBackground(new Color(100, 150, 200));
                computerGrid[r][c].setText("");
            }
        }
        
        placeComputerShips();
        updateScore();
        statusLabel.setText("Click on your grid to place " + NUM_SHIPS + " ships");
    }
}
