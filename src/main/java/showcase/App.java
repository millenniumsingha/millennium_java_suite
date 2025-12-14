package showcase;

import showcase.panels.*;
import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    public App() {
        setTitle("Java Playground");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        // Main layout
        setLayout(new BorderLayout());
        
        // Sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);
        
        // Content area with CardLayout for switching panels
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 245, 245));
        
        // Add all panels
        contentPanel.add(createWelcomePanel(), "welcome");
        contentPanel.add(new BattleShipsPanel(), "battleships");
        contentPanel.add(new CipherPanel(), "cipher");
        contentPanel.add(new TripPlannerPanel(), "trip");
        contentPanel.add(new OddEvenPanel(), "oddeven");
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Status bar
        JLabel statusBar = new JLabel("  Select a module from the sidebar to begin");
        statusBar.setPreferredSize(new Dimension(getWidth(), 25));
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        add(statusBar, BorderLayout.SOUTH);
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(50, 50, 60));
        sidebar.setPreferredSize(new Dimension(160, getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel title = new JLabel("Java Playground");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(20));
        
        // Section: Games
        JLabel gamesLabel = new JLabel("GAMES");
        gamesLabel.setForeground(new Color(150, 150, 160));
        gamesLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        gamesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(gamesLabel);
        sidebar.add(Box.createVerticalStrut(5));
        
        sidebar.add(createNavButton("🎮 BattleShips", "battleships"));
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(createNavButton("🎲 Odd/Even", "oddeven"));
        sidebar.add(Box.createVerticalStrut(15));
        
        // Section: Tools
        JLabel toolsLabel = new JLabel("TOOLS");
        toolsLabel.setForeground(new Color(150, 150, 160));
        toolsLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        toolsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(toolsLabel);
        sidebar.add(Box.createVerticalStrut(5));
        
        sidebar.add(createNavButton("🔐 Cipher Tool", "cipher"));
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(createNavButton("✈️ Trip Planner", "trip"));
        
        sidebar.add(Box.createVerticalGlue());
        
        return sidebar;
    }
    
    private JButton createNavButton(String text, String panelName) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(140, 35));
        button.setBackground(new Color(70, 70, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        
        button.addActionListener(e -> cardLayout.show(contentPanel, panelName));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(90, 90, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 70, 80));
            }
        });
        
        return button;
    }
    
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        
        JLabel welcomeTitle = new JLabel("Welcome to Java Playground");
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("A collection of Java mini-applications");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instruction = new JLabel("← Select a module from the sidebar");
        instruction.setFont(new Font("SansSerif", Font.ITALIC, 12));
        instruction.setForeground(new Color(100, 100, 100));
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        content.add(welcomeTitle);
        content.add(Box.createVerticalStrut(10));
        content.add(subtitle);
        content.add(Box.createVerticalStrut(30));
        content.add(instruction);
        
        panel.add(content);
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Use default look and feel
            }
            new App().setVisible(true);
        });
    }
}
