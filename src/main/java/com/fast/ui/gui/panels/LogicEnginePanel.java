package com.fast.ui.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.fast.discrete.LogicInferenceEngine;

public class LogicEnginePanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color WARNING_COLOR = new Color(255, 152, 0);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    
    private JTextArea factsInput, rulesInput, queryInput;
    private JTextArea resultArea;
    private JComboBox<String> strategyCombo;
    private LogicInferenceEngine engine;
    
    public LogicEnginePanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        engine = new LogicInferenceEngine();
        
        JLabel titleLabel = new JLabel("Logic Inference Engine");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(PRIMARY_COLOR);
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.add(createInputPanel());
        contentPanel.add(createResultsPanel());
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            "Knowledge Base & Query", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR));
        
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.setBackground(Color.WHITE);
        
        // Facts input
        JPanel factsPanel = new JPanel(new BorderLayout(5, 5));
        factsPanel.setBackground(Color.WHITE);
        JLabel factsLabel = new JLabel("Facts (one per line):");
        factsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        factsInput = new JTextArea(3, 25);
        factsInput.setFont(new Font("Consolas", Font.PLAIN, 11));
        factsInput.setText("John is a student\nComputer Science is a course\nJohn enrolled in Computer Science");
        factsPanel.add(factsLabel, BorderLayout.NORTH);
        factsPanel.add(new JScrollPane(factsInput), BorderLayout.CENTER);
        
        // Rules input
        JPanel rulesPanel = new JPanel(new BorderLayout(5, 5));
        rulesPanel.setBackground(Color.WHITE);
        JLabel rulesLabel = new JLabel("Rules (format: if condition then conclusion):");
        rulesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        rulesInput = new JTextArea(3, 25);
        rulesInput.setFont(new Font("Consolas", Font.PLAIN, 11));
        rulesInput.setText("if student and enrolled then studying\nif studying then attending\nif attending then making progress");
        rulesPanel.add(rulesLabel, BorderLayout.NORTH);
        rulesPanel.add(new JScrollPane(rulesInput), BorderLayout.CENTER);
        
        // Query input
        JPanel queryPanel = new JPanel(new BorderLayout(5, 5));
        queryPanel.setBackground(Color.WHITE);
        JLabel queryLabel = new JLabel("Query:");
        queryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        queryInput = new JTextArea(2, 25);
        queryInput.setFont(new Font("Consolas", Font.PLAIN, 11));
        queryInput.setText("John is making progress");
        queryPanel.add(queryLabel, BorderLayout.NORTH);
        queryPanel.add(new JScrollPane(queryInput), BorderLayout.CENTER);
        
        // Strategy selection
        JPanel strategyPanel = new JPanel(new BorderLayout(5, 5));
        strategyPanel.setBackground(Color.WHITE);
        JLabel strategyLabel = new JLabel("Inference Strategy:");
        strategyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        strategyCombo = new JComboBox<>(new String[]{
            "Forward Chaining",
            "Backward Chaining",
            "Bidirectional"
        });
        strategyCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        strategyPanel.add(strategyLabel, BorderLayout.NORTH);
        strategyPanel.add(strategyCombo, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton inferBtn = createButton("Run Inference", PRIMARY_COLOR);
        inferBtn.addActionListener(e -> runInference());
        
        JButton clearBtn = createButton("Clear", SECONDARY_COLOR);
        clearBtn.addActionListener(e -> {
            factsInput.setText("");
            rulesInput.setText("");
            queryInput.setText("");
            resultArea.setText("");
        });
        
        JButton sampleBtn = createButton("Load Sample", SUCCESS_COLOR);
        sampleBtn.addActionListener(e -> loadSampleData());
        
        buttonPanel.add(inferBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(sampleBtn);
        
        inputsPanel.add(factsPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(rulesPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(queryPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(strategyPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(buttonPanel);
        
        panel.add(inputsPanel, BorderLayout.NORTH);
        return panel;
    }
    
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 1),
            "Inference Results", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), SECONDARY_COLOR));
        
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(250, 250, 250));
        resultArea.setText("Inference results will appear here...");
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private void runInference() {
        try {
            String strategy = (String) strategyCombo.getSelectedItem();
            String query = queryInput.getText().trim();
            
            if (query.isEmpty()) {
                resultArea.setText("Error: Please enter a query");
                return;
            }
            
            StringBuilder result = new StringBuilder();
            result.append("═══════════════════════════════════════\n");
            result.append("LOGIC INFERENCE ENGINE EXECUTION\n");
            result.append("═══════════════════════════════════════\n\n");
            
            result.append("Strategy: ").append(strategy).append("\n");
            result.append("Query: ").append(query).append("\n\n");
            
            // Parse and display facts
            String[] facts = factsInput.getText().split("\n");
            result.append("Facts Loaded: ").append(facts.length).append("\n");
            for (String fact : facts) {
                if (!fact.trim().isEmpty()) {
                    result.append("  • ").append(fact.trim()).append("\n");
                }
            }
            result.append("\n");
            
            // Parse and display rules
            String[] rules = rulesInput.getText().split("\n");
            result.append("Rules Loaded: ").append(rules.length).append("\n");
            for (String rule : rules) {
                if (!rule.trim().isEmpty()) {
                    result.append("  • ").append(rule.trim()).append("\n");
                }
            }
            result.append("\n");
            
            result.append("─────────────────────────────────────────\n");
            result.append("INFERENCE PROCESS:\n");
            result.append("─────────────────────────────────────────\n\n");
            
            // Simulate inference
            java.util.Set<String> derivedFacts = new java.util.HashSet<>();
            for (String fact : facts) {
                if (!fact.trim().isEmpty()) {
                    derivedFacts.add(fact.trim());
                }
            }
            
            // Apply rules multiple times (forward chaining simulation)
            int iterations = 0;
            int maxIterations = 10;
            boolean changed = true;
            
            while (changed && iterations < maxIterations) {
                changed = false;
                iterations++;
                result.append("Iteration ").append(iterations).append(":\n");
                
                for (String rule : rules) {
                    if (rule.trim().isEmpty()) continue;
                    
                    // Simple pattern matching for "if...then..." rules
                    if (rule.contains("if") && rule.contains("then")) {
                        String[] parts = rule.split("then");
                        if (parts.length == 2) {
                            String condition = parts[0].replace("if", "").trim();
                            String conclusion = parts[1].trim();
                            
                            // Check if condition matches any fact
                            for (String fact : derivedFacts) {
                                if (fact.toLowerCase().contains(condition.toLowerCase())) {
                                    if (!derivedFacts.contains(conclusion)) {
                                        derivedFacts.add(conclusion);
                                        result.append("  → Applied rule: derived '").append(conclusion).append("'\n");
                                        changed = true;
                                    }
                                }
                            }
                        }
                    }
                }
                result.append("\n");
            }
            
            result.append("─────────────────────────────────────────\n");
            result.append("QUERY VERIFICATION:\n");
            result.append("─────────────────────────────────────────\n\n");
            
            // Check if query is in derived facts
            boolean queryFound = false;
            for (String fact : derivedFacts) {
                if (fact.equalsIgnoreCase(query)) {
                    queryFound = true;
                    break;
                }
            }
            
            if (queryFound) {
                result.append("✓ QUERY PROVEN TRUE\n");
                result.append("The statement '").append(query).append("' can be derived from the facts and rules.\n");
            } else {
                result.append("✗ QUERY CANNOT BE PROVEN\n");
                result.append("The statement '").append(query).append("' cannot be derived from the facts and rules.\n");
            }
            
            result.append("\nDerived Facts:\n");
            int count = 1;
            for (String fact : derivedFacts) {
                result.append(count).append(". ").append(fact).append("\n");
                count++;
            }
            
            result.append("\n═══════════════════════════════════════\n");
            result.append("Total facts: ").append(derivedFacts.size()).append("\n");
            result.append("Total iterations: ").append(iterations).append("\n");
            
            resultArea.setText(result.toString());
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    private void loadSampleData() {
        factsInput.setText("John is a student\nComputer Science is a course\nJohn enrolled in Computer Science\nAlice is a student\nDatabase is a course");
        rulesInput.setText("if student and enrolled then studying\nif studying then attending\nif attending then making progress");
        queryInput.setText("John is making progress");
    }
    
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(
                    Math.min(255, color.getRed() + 30),
                    Math.min(255, color.getGreen() + 30),
                    Math.min(255, color.getBlue() + 30)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(color);
            }
        });
        return btn;
    }
}
