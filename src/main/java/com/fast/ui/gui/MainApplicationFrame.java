package com.fast.ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.fast.data.DataManager;
import com.fast.ui.gui.panels.*;

/**
 * Main Application Frame with Functional Discrete Math Features
 */
public class MainApplicationFrame extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    public MainApplicationFrame() {
        setTitle("FAST University Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 900);
        setLocationRelativeTo(null);
        
        // Initialize sample data
        DataManager.initializeSampleData();
        
        // Create sidebar
        add(createSidebar(), BorderLayout.WEST);
        
        // Create content panel with all functional panels
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Add all functional panels
        contentPanel.add(createDashboardPanel(), "dashboard");
        contentPanel.add(new StudentManagementPanel(), "students");
        contentPanel.add(new CourseSelectionPanel(), "courses");
        contentPanel.add(new SetOperationsRealWorldPanel(), "sets");
        contentPanel.add(new RelationsAdvancedPanel(), "relations");
        contentPanel.add(new FunctionsAnalysisRealWorldPanel(), "functions");
        contentPanel.add(createLogicEnginePanel(), "logic");
        contentPanel.add(createGroupCombinationPanel(), "groups");
        contentPanel.add(new GraphicalReportsPanel(), "reports");
        
        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, "dashboard");
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(PRIMARY_COLOR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        
        JLabel logoLabel = new JLabel("FAST UMS");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        sidebar.add(logoLabel);
        
        String[] menuItems = {
            "Dashboard", "Students", "Courses", "Set Operations",
            "Relations", "Functions", "Logic Engine", "Groups", "Reports", "Logout"
        };
        
        String[] panels = {
            "dashboard", "students", "courses", "sets",
            "relations", "functions", "logic", "groups", "reports", "logout"
        };
        
        for (int i = 0; i < menuItems.length; i++) {
            final String panelName = panels[i];
            JButton btn = createMenuButton(menuItems[i]);
            btn.addActionListener(e -> {
                if ("logout".equals(panelName)) {
                    logout();
                } else {
                    cardLayout.show(contentPanel, panelName);
                }
            });
            sidebar.add(btn);
        }
        
        sidebar.add(Box.createVerticalGlue());
        
        JLabel footerLabel = new JLabel("v2.0 - Discrete Math");
        footerLabel.setForeground(new Color(150, 150, 150));
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        sidebar.add(footerLabel);
        
        return sidebar;
    }
    
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMargin(new Insets(10, 10, 10, 10));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(30, 120, 230));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(PRIMARY_COLOR);
            }
        });
        
        return btn;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        card.setMaximumSize(new Dimension(250, 100));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titleLabel.setForeground(new Color(120, 120, 120));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        
        return card;
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(PRIMARY_COLOR);
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        
        JPanel statsPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        statsPanel.setBackground(BACKGROUND_COLOR);
        statsPanel.setMaximumSize(new Dimension(1200, 220));
        
        int studentCount = DataManager.getAllStudents().size();
        int courseCount = DataManager.getAllCourses().size();
        
        statsPanel.add(createStatCard("Students", String.valueOf(studentCount), PRIMARY_COLOR));
        statsPanel.add(createStatCard("Courses", String.valueOf(courseCount), SECONDARY_COLOR));
        statsPanel.add(createStatCard("Features", "9", new Color(76, 175, 80)));
        statsPanel.add(createStatCard("Status", "Active", new Color(255, 152, 0)));
        
        panel.add(statsPanel);
        panel.add(Box.createVerticalStrut(40));
        
        JLabel info = new JLabel("Welcome to FAST University Management System with Discrete Mathematics");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        info.setForeground(new Color(100, 100, 100));
        panel.add(info);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JPanel createStudentManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Header
        JLabel title = new JLabel("Student Management");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        // Content
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(BACKGROUND_COLOR);
        contentArea.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));
        formPanel.setLayout(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField majorField = new JTextField();
        
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Major:"));
        formPanel.add(majorField);
        
        JButton addBtn = new JButton("Add Student");
        addBtn.setBackground(PRIMARY_COLOR);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        contentArea.add(formPanel, BorderLayout.NORTH);
        contentArea.add(addBtn, BorderLayout.SOUTH);
        
        panel.add(contentArea, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCourseManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel title = new JLabel("Course Management");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JLabel content = new JLabel("Course management features coming soon...");
        content.setFont(LABEL_FONT);
        panel.add(content, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCourseSchedulingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel title = new JLabel("Course Scheduling & Prerequisites");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createTitledBorder("Add Course Prerequisites"));
        contentPanel.setLayout(new GridLayout(3, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JTextField courseField = new JTextField("CS102");
        JTextField prereqField = new JTextField("CS101");
        
        contentPanel.add(new JLabel("Course Code:"));
        contentPanel.add(courseField);
        contentPanel.add(new JLabel("Prerequisite:"));
        contentPanel.add(prereqField);
        
        JButton addBtn = new JButton("Add Prerequisite");
        addBtn.setBackground(PRIMARY_COLOR);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        contentPanel.add(addBtn);
        
        JButton generateBtn = new JButton("Generate Sequence");
        generateBtn.setBackground(SECONDARY_COLOR);
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        contentPanel.add(generateBtn);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSetOperationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel title = new JLabel("Set Operations");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JLabel content = new JLabel("Set theory operations: Union, Intersection, Power Sets, Combinations...");
        content.setFont(LABEL_FONT);
        panel.add(content, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createRelationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel title = new JLabel("Relations Analysis");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JLabel content = new JLabel("Analyze relation properties: Reflexive, Symmetric, Transitive, Equivalence...");
        content.setFont(LABEL_FONT);
        panel.add(content, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFunctionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel title = new JLabel("Functions & Mappings");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JLabel content = new JLabel("Verify function properties: Injective, Surjective, Bijective...");
        content.setFont(LABEL_FONT);
        panel.add(content, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createLogicEnginePanel() {
        return new LogicEnginePanel();
    }
    
    private JPanel createGroupCombinationPanel() {
        return new StudentGroupsPanel();
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel title = new JLabel("Reports & Analytics");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JLabel content = new JLabel("View system statistics and analytics...");
        content.setFont(LABEL_FONT);
        panel.add(content, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                WelcomeLoginFrame loginFrame = new WelcomeLoginFrame();
                loginFrame.setVisible(true);
                dispose();
            });
        }
    }
}
