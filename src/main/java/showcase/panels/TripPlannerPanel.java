package showcase.panels;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.text.DecimalFormat;

public class TripPlannerPanel extends JPanel {
    private JTextField daysField, budgetField, currencyField, rateField;
    private JTextField homeLat, homeLon, destLat, destLon;
    private JTextField timeDiffField, areaField;
    private JTextArea resultsArea;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    public TripPlannerPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel title = new JLabel("Trip Planner", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        
        // Main content - two columns
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setOpaque(false);
        
        // Left: Input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);
        
        // Budget section
        JPanel budgetSection = createSection("Budget Calculator");
        budgetSection.setLayout(new GridLayout(4, 2, 5, 5));
        budgetSection.add(new JLabel("Trip days:"));
        daysField = new JTextField("7");
        budgetSection.add(daysField);
        budgetSection.add(new JLabel("Budget (USD):"));
        budgetField = new JTextField("1000");
        budgetSection.add(budgetField);
        budgetSection.add(new JLabel("Currency code:"));
        currencyField = new JTextField("EUR");
        budgetSection.add(currencyField);
        budgetSection.add(new JLabel("Exchange rate:"));
        rateField = new JTextField("0.92");
        budgetSection.add(rateField);
        inputPanel.add(budgetSection);
        
        inputPanel.add(Box.createVerticalStrut(10));
        
        // Time section
        JPanel timeSection = createSection("Time Zone");
        timeSection.setLayout(new GridLayout(1, 2, 5, 5));
        timeSection.add(new JLabel("Hour difference:"));
        timeDiffField = new JTextField("6");
        timeSection.add(timeDiffField);
        inputPanel.add(timeSection);
        
        inputPanel.add(Box.createVerticalStrut(10));
        
        // Area section
        JPanel areaSection = createSection("Country Area");
        areaSection.setLayout(new GridLayout(1, 2, 5, 5));
        areaSection.add(new JLabel("Area (km²):"));
        areaField = new JTextField("357386");
        areaSection.add(areaField);
        inputPanel.add(areaSection);
        
        inputPanel.add(Box.createVerticalStrut(10));
        
        // Distance section
        JPanel distSection = createSection("Distance Calculator");
        distSection.setLayout(new GridLayout(4, 2, 5, 5));
        distSection.add(new JLabel("Home latitude:"));
        homeLat = new JTextField("-27.4698");
        distSection.add(homeLat);
        distSection.add(new JLabel("Home longitude:"));
        homeLon = new JTextField("153.0251");
        distSection.add(homeLon);
        distSection.add(new JLabel("Dest latitude:"));
        destLat = new JTextField("51.5074");
        distSection.add(destLat);
        distSection.add(new JLabel("Dest longitude:"));
        destLon = new JTextField("-0.1278");
        distSection.add(destLon);
        inputPanel.add(distSection);
        
        inputPanel.add(Box.createVerticalStrut(10));
        
        // Calculate button
        JButton calcButton = new JButton("Calculate All");
        calcButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        calcButton.addActionListener(e -> calculate());
        inputPanel.add(calcButton);
        
        mainPanel.add(inputPanel);
        
        // Right: Results
        JPanel resultsPanel = new JPanel(new BorderLayout(5, 5));
        resultsPanel.setOpaque(false);
        JLabel resultsLabel = new JLabel("Results:");
        resultsLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        resultsPanel.add(resultsLabel, BorderLayout.NORTH);
        
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultsArea.setBackground(new Color(250, 250, 250));
        resultsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(resultsPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Initial calculation
        calculate();
    }
    
    private JPanel createSection(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(350, 150));
        return panel;
    }
    
    private void calculate() {
        StringBuilder sb = new StringBuilder();
        
        try {
            // Budget calculations
            int days = Integer.parseInt(daysField.getText().trim());
            double budget = Double.parseDouble(budgetField.getText().trim());
            String currency = currencyField.getText().trim().toUpperCase();
            double rate = Double.parseDouble(rateField.getText().trim());
            
            int hours = days * 24;
            int minutes = hours * 60;
            double perDay = budget / days;
            double converted = budget * rate;
            double convertedPerDay = converted / days;
            
            sb.append("═══ BUDGET ═══\n\n");
            sb.append("Duration:\n");
            sb.append("  • ").append(days).append(" days\n");
            sb.append("  • ").append(hours).append(" hours\n");
            sb.append("  • ").append(String.format("%,d", minutes)).append(" minutes\n\n");
            sb.append("Budget:\n");
            sb.append("  • $").append(df.format(budget)).append(" USD total\n");
            sb.append("  • $").append(df.format(perDay)).append(" USD/day\n");
            sb.append("  • ").append(df.format(converted)).append(" ").append(currency).append(" total\n");
            sb.append("  • ").append(df.format(convertedPerDay)).append(" ").append(currency).append("/day\n");
            
        } catch (NumberFormatException e) {
            sb.append("Budget: Invalid input\n");
        }
        
        sb.append("\n═══ TIME ZONE ═══\n\n");
        try {
            int diff = Integer.parseInt(timeDiffField.getText().trim());
            int midnight = (0 + diff + 24) % 24;
            int noon = (12 + diff) % 24;
            
            sb.append("When it's midnight at home:\n");
            sb.append("  → ").append(String.format("%02d:00", midnight)).append(" at destination\n\n");
            sb.append("When it's noon at home:\n");
            sb.append("  → ").append(String.format("%02d:00", noon)).append(" at destination\n");
        } catch (NumberFormatException e) {
            sb.append("Time zone: Invalid input\n");
        }
        
        sb.append("\n═══ COUNTRY AREA ═══\n\n");
        try {
            double areakm = Double.parseDouble(areaField.getText().trim());
            double areaMiles = areakm * 0.386102;
            
            sb.append("  • ").append(String.format("%,.0f", areakm)).append(" km²\n");
            sb.append("  • ").append(String.format("%,.0f", areaMiles)).append(" mi²\n");
        } catch (NumberFormatException e) {
            sb.append("Area: Invalid input\n");
        }
        
        sb.append("\n═══ DISTANCE ═══\n\n");
        try {
            double lat1 = Double.parseDouble(homeLat.getText().trim());
            double lon1 = Double.parseDouble(homeLon.getText().trim());
            double lat2 = Double.parseDouble(destLat.getText().trim());
            double lon2 = Double.parseDouble(destLon.getText().trim());
            
            double distance = haversine(lat1, lon1, lat2, lon2);
            double miles = distance * 0.621371;
            
            sb.append("From: ").append(lat1).append("°, ").append(lon1).append("°\n");
            sb.append("To:   ").append(lat2).append("°, ").append(lon2).append("°\n\n");
            sb.append("Distance:\n");
            sb.append("  • ").append(String.format("%,.0f", distance)).append(" km\n");
            sb.append("  • ").append(String.format("%,.0f", miles)).append(" miles\n");
        } catch (NumberFormatException e) {
            sb.append("Distance: Invalid input\n");
        }
        
        resultsArea.setText(sb.toString());
        resultsArea.setCaretPosition(0);
    }
    
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Earth's radius in km
        
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c;
    }
}
