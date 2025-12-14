package showcase.panels;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class CipherPanel extends JPanel {
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JSlider shiftSlider;
    private JLabel shiftLabel;
    private JComboBox<String> modeCombo;
    private JSpinner groupSpinner;
    
    public CipherPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel title = new JLabel("Caesar Cipher Tool", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        
        // Main content
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setOpaque(false);
        
        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setOpaque(false);
        JLabel inputLabel = new JLabel("Input Text:");
        inputLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        
        inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateOutput(); }
            public void removeUpdate(DocumentEvent e) { updateOutput(); }
            public void changedUpdate(DocumentEvent e) { updateOutput(); }
        });
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setPreferredSize(new Dimension(300, 200));
        inputPanel.add(inputScroll, BorderLayout.CENTER);
        
        mainPanel.add(inputPanel);
        
        // Output panel
        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.setOpaque(false);
        JLabel outputLabel = new JLabel("Output:");
        outputLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        outputPanel.add(outputLabel, BorderLayout.NORTH);
        
        outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(250, 250, 250));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setPreferredSize(new Dimension(300, 200));
        outputPanel.add(outputScroll, BorderLayout.CENTER);
        
        JButton copyButton = new JButton("Copy to Clipboard");
        copyButton.addActionListener(e -> {
            outputArea.selectAll();
            outputArea.copy();
            outputArea.setCaretPosition(0);
        });
        outputPanel.add(copyButton, BorderLayout.SOUTH);
        
        mainPanel.add(outputPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Controls panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlsPanel.setOpaque(false);
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
        
        // Mode selector
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        modePanel.setOpaque(false);
        modePanel.add(new JLabel("Mode:"));
        modeCombo = new JComboBox<>(new String[]{"Encrypt", "Decrypt"});
        modeCombo.addActionListener(e -> updateOutput());
        modePanel.add(modeCombo);
        controlsPanel.add(modePanel);
        
        // Shift slider
        JPanel shiftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        shiftPanel.setOpaque(false);
        shiftPanel.add(new JLabel("Shift:"));
        shiftSlider = new JSlider(1, 25, 3);
        shiftSlider.setOpaque(false);
        shiftSlider.setPreferredSize(new Dimension(150, 30));
        shiftSlider.addChangeListener(e -> {
            shiftLabel.setText(String.valueOf(shiftSlider.getValue()));
            updateOutput();
        });
        shiftPanel.add(shiftSlider);
        shiftLabel = new JLabel("3");
        shiftLabel.setPreferredSize(new Dimension(25, 20));
        shiftPanel.add(shiftLabel);
        controlsPanel.add(shiftPanel);
        
        // Group size
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        groupPanel.setOpaque(false);
        groupPanel.add(new JLabel("Group size:"));
        groupSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        groupSpinner.addChangeListener(e -> updateOutput());
        groupPanel.add(groupSpinner);
        controlsPanel.add(groupPanel);
        
        add(controlsPanel, BorderLayout.SOUTH);
    }
    
    private void updateOutput() {
        String input = inputArea.getText();
        if (input.isEmpty()) {
            outputArea.setText("");
            return;
        }
        
        int shift = shiftSlider.getValue();
        int groupSize = (Integer) groupSpinner.getValue();
        boolean encrypt = modeCombo.getSelectedIndex() == 0;
        
        String result;
        if (encrypt) {
            result = encrypt(input, shift, groupSize);
        } else {
            result = decrypt(input, shift);
        }
        
        outputArea.setText(result);
    }
    
    private String normalize(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }
    
    private String caesar(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                int shifted = c + shift;
                if (shifted > 'Z') {
                    shifted = 'A' + (shifted - 'Z' - 1);
                }
                result.append((char) shifted);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    private String group(String text, int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (i > 0 && i % size == 0) {
                result.append(" ");
            }
            result.append(text.charAt(i));
        }
        // Pad last group if needed
        int remainder = text.length() % size;
        if (remainder != 0) {
            for (int i = 0; i < size - remainder; i++) {
                result.append("X");
            }
        }
        return result.toString();
    }
    
    private String encrypt(String text, int shift, int groupSize) {
        String normalized = normalize(text);
        String shifted = caesar(normalized, shift);
        return group(shifted, groupSize);
    }
    
    private String decrypt(String text, int shift) {
        // Remove spaces and padding
        String cleaned = text.replace(" ", "").replace("X", "").replace("x", "");
        cleaned = normalize(cleaned);
        
        StringBuilder result = new StringBuilder();
        for (char c : cleaned.toCharArray()) {
            if (Character.isUpperCase(c)) {
                int shifted = c - shift;
                if (shifted < 'A') {
                    shifted = 'Z' - ('A' - shifted - 1);
                }
                result.append((char) shifted);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
