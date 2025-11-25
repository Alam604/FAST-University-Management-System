package com.fast.ui.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.fast.discrete.RelationsModule;

public class RelationsPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private JTextArea relationArea, resultArea;
    
    public RelationsPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(BACKGROUND_COLOR);
        
        JLabel title = new JLabel("Relations Analysis (Discrete Mathematics)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY_COLOR);
        
        panel.add(title);
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Input
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Define Relation\n(Format: (a,b) (c,d) ...)"));
        
        relationArea = new JTextArea(10, 25);
        relationArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        relationArea.setText("(1,1) (1,2) (2,2) (3,3) (1,3)");
        inputPanel.add(new JScrollPane(relationArea), BorderLayout.CENTER);
        
        // Output
        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.setBackground(BACKGROUND_COLOR);
        outputPanel.setBorder(BorderFactory.createTitledBorder("Properties"));
        
        resultArea = new JTextArea(10, 25);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        resultArea.setBackground(new Color(240, 240, 240));
        outputPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        
        panel.add(inputPanel);
        panel.add(outputPanel);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JButton analyzeBtn = new JButton("Analyze Relation");
        analyzeBtn.setBackground(PRIMARY_COLOR);
        analyzeBtn.setForeground(Color.WHITE);
        analyzeBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        analyzeBtn.addActionListener(e -> analyzeRelation());
        
        panel.add(analyzeBtn);
        
        return panel;
    }
    
    private void analyzeRelation() {
        try {
            String relationText = relationArea.getText();
            RelationsModule module = new RelationsModule();
            
            // Parse relations and add to module
            String[] pairs = relationText.split("\\)");
            java.util.Set<String> parsedPairs = new java.util.HashSet<>();
            
            for (String pair : pairs) {
                pair = pair.trim();
                if (pair.startsWith("(")) {
                    pair = pair.substring(1);
                }
                if (pair.isEmpty()) continue;
                
                String[] parts = pair.split(",");
                if (parts.length == 2) {
                    try {
                        String a = parts[0].trim();
                        String b = parts[1].trim();
                        module.addRelation(a, b);
                        parsedPairs.add(a + "," + b);
                    } catch (Exception ignored) {}
                }
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Relation: ").append(parsedPairs).append("\n\n");
            
            boolean isReflexive = module.isReflexive();
            boolean isSymmetric = module.isSymmetric();
            boolean isTransitive = module.isTransitive();
            
            sb.append("✓ Reflexive: ").append(isReflexive ? "YES" : "NO").append("\n");
            sb.append("✓ Symmetric: ").append(isSymmetric ? "YES" : "NO").append("\n");
            sb.append("✓ Transitive: ").append(isTransitive ? "YES" : "NO").append("\n");
            
            if (isReflexive && isSymmetric && isTransitive) {
                sb.append("\n⭐ This is an EQUIVALENCE RELATION!");
            } else if (isReflexive && isTransitive) {
                sb.append("\n⭐ This is a PARTIAL ORDER!");
            }
            
            resultArea.setText(sb.toString());
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }
}
