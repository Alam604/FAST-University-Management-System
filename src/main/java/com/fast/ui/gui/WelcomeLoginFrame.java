package com.fast.ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * Professional Welcome & Login Screen for FAST University Management System
 */
public class WelcomeLoginFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 36);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    
    public WelcomeLoginFrame() {
        setTitle("FAST University Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Add screens
        mainPanel.add(createWelcomeScreen(), "welcome");
        mainPanel.add(createLoginScreen(), "login");
        mainPanel.add(createSignupScreen(), "signup");
        
        add(mainPanel);
        
        // Show welcome screen first
        cardLayout.show(mainPanel, "welcome");
    }
    
    private JPanel createWelcomeScreen() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_COLOR,
                    getWidth(), getHeight(), SECONDARY_COLOR
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        panel.setLayout(null);
        panel.setBackground(PRIMARY_COLOR);
        
        // Logo/Title
        JLabel titleLabel = new JLabel("FAST University");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(200, 100, 600, 60);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Management System");
        subtitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setBounds(200, 160, 600, 40);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(subtitleLabel);
        
        // Description
        JLabel descLabel = new JLabel(
            "<html><center>Advanced University Management with Discrete Mathematics Framework<br>" +
            "Course Scheduling • Student Management • Faculty Assignment • Consistency Checking</center></html>"
        );
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(220, 220, 220));
        descLabel.setBounds(100, 250, 800, 60);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(descLabel);
        
        // Features grid
        String[] features = {
            "📚 Course Scheduling", "👥 Student Groups", "📊 Analytics",
            "🔗 Relations & Sets", "✓ Verification", "⚡ Performance"
        };
        
        int startX = 100;
        int startY = 330;
        int featureWidth = 140;
        int featureHeight = 100;
        int spacing = 20;
        
        for (int i = 0; i < features.length; i++) {
            JPanel featurePanel = new RoundedPanel(15);
            featurePanel.setBackground(new Color(255, 255, 255, 200));
            featurePanel.setLayout(new BorderLayout());
            
            JLabel featureLabel = new JLabel(features[i]);
            featureLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            featureLabel.setForeground(PRIMARY_COLOR);
            featureLabel.setHorizontalAlignment(SwingConstants.CENTER);
            featureLabel.setVerticalAlignment(SwingConstants.CENTER);
            featurePanel.add(featureLabel);
            
            int col = i % 3;
            int row = i / 3;
            int x = startX + (col * (featureWidth + spacing));
            int y = startY + (row * (featureHeight + spacing));
            
            featurePanel.setBounds(x, y, featureWidth, featureHeight);
            panel.add(featurePanel);
        }
        
        // Login Button
        RoundedButton loginBtn = new RoundedButton("LOGIN");
        loginBtn.setBounds(300, 580, 150, 45);
        loginBtn.setBackground(new Color(255, 255, 255));
        loginBtn.setForeground(PRIMARY_COLOR);
        loginBtn.setFont(BUTTON_FONT);
        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        panel.add(loginBtn);
        
        // Signup Button
        RoundedButton signupBtn = new RoundedButton("SIGN UP");
        signupBtn.setBounds(550, 580, 150, 45);
        signupBtn.setBackground(SECONDARY_COLOR);
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFont(BUTTON_FONT);
        signupBtn.addActionListener(e -> cardLayout.show(mainPanel, "signup"));
        panel.add(signupBtn);
        
        return panel;
    }
    
    private JPanel createLoginScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(null);
        
        // Back button
        JButton backBtn = new JButton("← Back");
        backBtn.setBounds(20, 20, 80, 30);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        panel.add(backBtn);
        
        // Login panel
        RoundedPanel loginPanel = new RoundedPanel(20);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBounds(300, 100, 400, 500);
        loginPanel.setLayout(null);
        
        // Title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBounds(20, 30, 360, 40);
        loginPanel.add(titleLabel);
        
        // Email
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(LABEL_FONT);
        emailLabel.setBounds(20, 90, 360, 20);
        loginPanel.add(emailLabel);
        
        JTextField emailField = new RoundedTextField(15);
        emailField.setBounds(20, 115, 360, 40);
        emailField.setText("student@fast.edu.pk");
        loginPanel.add(emailField);
        
        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(LABEL_FONT);
        passLabel.setBounds(20, 170, 360, 20);
        loginPanel.add(passLabel);
        
        JPasswordField passField = new JPasswordField();
        passField.setBounds(20, 195, 360, 40);
        passField.setText("password123");
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        loginPanel.add(passField);
        
        // Remember me
        JCheckBox rememberBtn = new JCheckBox("Remember me");
        rememberBtn.setBounds(20, 250, 360, 25);
        rememberBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rememberBtn.setBackground(Color.WHITE);
        loginPanel.add(rememberBtn);
        
        // Login button
        RoundedButton loginBtn = new RoundedButton("LOGIN");
        loginBtn.setBounds(20, 310, 360, 45);
        loginBtn.setBackground(PRIMARY_COLOR);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(BUTTON_FONT);
        loginBtn.addActionListener(e -> openMainSystem());
        loginPanel.add(loginBtn);
        
        // Forgot password
        JLabel forgotLabel = new JLabel("Forgot password?");
        forgotLabel.setBounds(20, 380, 360, 20);
        forgotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        forgotLabel.setForeground(SECONDARY_COLOR);
        forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(forgotLabel);
        
        panel.add(loginPanel);
        return panel;
    }
    
    private JPanel createSignupScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(null);
        
        // Back button
        JButton backBtn = new JButton("← Back");
        backBtn.setBounds(20, 20, 80, 30);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        panel.add(backBtn);
        
        // Signup panel
        RoundedPanel signupPanel = new RoundedPanel(20);
        signupPanel.setBackground(Color.WHITE);
        signupPanel.setBounds(250, 80, 500, 580);
        signupPanel.setLayout(null);
        
        // Title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBounds(20, 20, 460, 40);
        signupPanel.add(titleLabel);
        
        // Full Name
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(LABEL_FONT);
        nameLabel.setBounds(20, 75, 460, 20);
        signupPanel.add(nameLabel);
        
        JTextField nameField = new RoundedTextField(15);
        nameField.setBounds(20, 100, 460, 40);
        nameField.setText("Enter full name");
        signupPanel.add(nameField);
        
        // Email
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(LABEL_FONT);
        emailLabel.setBounds(20, 155, 460, 20);
        signupPanel.add(emailLabel);
        
        JTextField emailField = new RoundedTextField(15);
        emailField.setBounds(20, 180, 460, 40);
        emailField.setText("example@fast.edu.pk");
        signupPanel.add(emailField);
        
        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(LABEL_FONT);
        passLabel.setBounds(20, 235, 460, 20);
        signupPanel.add(passLabel);
        
        JPasswordField passField = new JPasswordField();
        passField.setBounds(20, 260, 460, 40);
        passField.setText("password123");
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        signupPanel.add(passField);
        
        // Confirm Password
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(LABEL_FONT);
        confirmLabel.setBounds(20, 315, 460, 20);
        signupPanel.add(confirmLabel);
        
        JPasswordField confirmField = new JPasswordField();
        confirmField.setBounds(20, 340, 460, 40);
        confirmField.setText("password123");
        confirmField.setFont(new Font("Arial", Font.PLAIN, 16));
        signupPanel.add(confirmField);
        
        // Terms checkbox
        JCheckBox termsBtn = new JCheckBox("I agree to terms and conditions");
        termsBtn.setBounds(20, 400, 460, 25);
        termsBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        termsBtn.setBackground(Color.WHITE);
        termsBtn.setSelected(true);
        signupPanel.add(termsBtn);
        
        // Signup button
        RoundedButton signupBtn = new RoundedButton("CREATE ACCOUNT");
        signupBtn.setBounds(20, 450, 460, 45);
        signupBtn.setBackground(SECONDARY_COLOR);
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFont(BUTTON_FONT);
        signupBtn.addActionListener(e -> openMainSystem());
        signupPanel.add(signupBtn);
        
        panel.add(signupPanel);
        return panel;
    }
    
    private void openMainSystem() {
        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame mainApp = new MainApplicationFrame();
            mainApp.setVisible(true);
            dispose();
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeLoginFrame frame = new WelcomeLoginFrame();
            frame.setVisible(true);
        });
    }
}

// Custom rounded panel
class RoundedPanel extends JPanel {
    private int radius;
    
    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        super.paintComponent(g);
    }
}

// Custom rounded button
class RoundedButton extends JButton {
    private int radius = 15;
    
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isPressed()) {
            g2d.setColor(new Color(
                Math.max(0, getBackground().getRed() - 20),
                Math.max(0, getBackground().getGreen() - 20),
                Math.max(0, getBackground().getBlue() - 20)
            ));
        } else {
            g2d.setColor(getBackground());
        }
        
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        
        super.paintComponent(g);
    }
}

// Custom rounded text field
class RoundedTextField extends JTextField {
    private int radius = 15;
    
    public RoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        
        g2d.setColor(new Color(200, 200, 200));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        
        super.paintComponent(g);
    }
}
