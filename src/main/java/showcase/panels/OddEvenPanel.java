package showcase.panels;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class OddEvenPanel extends JPanel {
    private String playerName = "Player";
    private boolean playerChoseOdd = true;
    private int playerWins = 0;
    private int computerWins = 0;
    
    private JTextField nameField;
    private JToggleButton oddButton, evenButton;
    private JSlider fingerSlider;
    private JLabel fingerLabel;
    private JTextArea gameLog;
    private JLabel scoreLabel;
    private JButton playButton;
    
    private Random random = new Random();
    
    public OddEvenPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel title = new JLabel("Odds and Evens", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        
        // Main content
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setOpaque(false);
        
        // Left panel - Controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setOpaque(false);
        
        // Name input
        JPanel namePanel = createSection("Player Setup");
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Your name:"));
        nameField = new JTextField(12);
        nameField.setText("Player");
        namePanel.add(nameField);
        controlPanel.add(namePanel);
        
        controlPanel.add(Box.createVerticalStrut(10));
        
        // Choice selection
        JPanel choicePanel = createSection("Choose Your Side");
        choicePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        ButtonGroup choiceGroup = new ButtonGroup();
        oddButton = new JToggleButton("ODD", true);
        oddButton.setPreferredSize(new Dimension(100, 40));
        oddButton.addActionListener(e -> playerChoseOdd = true);
        choiceGroup.add(oddButton);
        
        evenButton = new JToggleButton("EVEN");
        evenButton.setPreferredSize(new Dimension(100, 40));
        evenButton.addActionListener(e -> playerChoseOdd = false);
        choiceGroup.add(evenButton);
        
        choicePanel.add(oddButton);
        choicePanel.add(evenButton);
        controlPanel.add(choicePanel);
        
        controlPanel.add(Box.createVerticalStrut(10));
        
        // Finger selection
        JPanel fingerPanel = createSection("How Many Fingers?");
        fingerPanel.setLayout(new BorderLayout(5, 5));
        
        fingerSlider = new JSlider(0, 5, 2);
        fingerSlider.setMajorTickSpacing(1);
        fingerSlider.setPaintTicks(true);
        fingerSlider.setPaintLabels(true);
        fingerSlider.setOpaque(false);
        fingerSlider.addChangeListener(e -> {
            int val = fingerSlider.getValue();
            fingerLabel.setText(getFingerEmoji(val));
        });
        fingerPanel.add(fingerSlider, BorderLayout.CENTER);
        
        fingerLabel = new JLabel(getFingerEmoji(2), SwingConstants.CENTER);
        fingerLabel.setFont(new Font("SansSerif", Font.PLAIN, 40));
        fingerPanel.add(fingerLabel, BorderLayout.SOUTH);
        
        controlPanel.add(fingerPanel);
        
        controlPanel.add(Box.createVerticalStrut(10));
        
        // Play button
        playButton = new JButton("PLAY!");
        playButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setMaximumSize(new Dimension(200, 50));
        playButton.addActionListener(e -> playRound());
        controlPanel.add(playButton);
        
        controlPanel.add(Box.createVerticalStrut(10));
        
        // Score
        scoreLabel = new JLabel("Score: You 0 - 0 Computer", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlPanel.add(scoreLabel);
        
        controlPanel.add(Box.createVerticalStrut(10));
        
        // Reset button
        JButton resetButton = new JButton("Reset Score");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(e -> resetScore());
        controlPanel.add(resetButton);
        
        mainPanel.add(controlPanel);
        
        // Right panel - Game log
        JPanel logPanel = new JPanel(new BorderLayout(5, 5));
        logPanel.setOpaque(false);
        JLabel logLabel = new JLabel("Game History:");
        logLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        logPanel.add(logLabel, BorderLayout.NORTH);
        
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gameLog.setBackground(new Color(250, 250, 250));
        gameLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(gameLog);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(logPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Welcome message
        gameLog.setText("Welcome to Odds and Evens!\n\n" +
                       "Rules:\n" +
                       "• Choose ODD or EVEN\n" +
                       "• Pick 0-5 fingers\n" +
                       "• Computer picks randomly\n" +
                       "• Sum determines winner\n\n" +
                       "Good luck!\n" +
                       "─".repeat(30) + "\n");
    }
    
    private JPanel createSection(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(350, 150));
        return panel;
    }
    
    private String getFingerEmoji(int count) {
        String[] fingers = {"✊", "☝️", "✌️", "🤟", "🖖", "🖐️"};
        return fingers[Math.min(count, 5)];
    }
    
    private void playRound() {
        playerName = nameField.getText().trim();
        if (playerName.isEmpty()) playerName = "Player";
        
        int playerFingers = fingerSlider.getValue();
        int computerFingers = random.nextInt(6);
        int sum = playerFingers + computerFingers;
        boolean sumIsOdd = sum % 2 == 1;
        
        StringBuilder result = new StringBuilder();
        result.append("\n").append(playerName).append(" shows: ").append(playerFingers);
        result.append(" ").append(getFingerEmoji(playerFingers)).append("\n");
        result.append("Computer shows: ").append(computerFingers);
        result.append(" ").append(getFingerEmoji(computerFingers)).append("\n");
        result.append("\n").append(playerFingers).append(" + ").append(computerFingers);
        result.append(" = ").append(sum);
        result.append(" (").append(sumIsOdd ? "ODD" : "EVEN").append(")\n\n");
        
        boolean playerWon = (playerChoseOdd && sumIsOdd) || (!playerChoseOdd && !sumIsOdd);
        
        if (playerWon) {
            result.append("🎉 ").append(playerName).append(" WINS! 🎉\n");
            playerWins++;
        } else {
            result.append("💻 Computer wins!\n");
            computerWins++;
        }
        
        result.append("─".repeat(30));
        
        gameLog.append(result.toString() + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
        
        updateScore();
    }
    
    private void updateScore() {
        scoreLabel.setText("Score: You " + playerWins + " - " + computerWins + " Computer");
    }
    
    private void resetScore() {
        playerWins = 0;
        computerWins = 0;
        updateScore();
        gameLog.append("\n*** Score reset ***\n" + "─".repeat(30) + "\n");
    }
}
