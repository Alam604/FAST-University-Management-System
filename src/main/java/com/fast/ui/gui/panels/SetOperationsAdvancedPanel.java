package com.fast.ui.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import com.fast.data.DataManager;
import com.fast.discrete.SetOperationsModule;

public class SetOperationsAdvancedPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    
    private JTextArea setAInput, setBInput;
    private JTextArea resultArea;
    private JComboBox<String> operationCombo;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    
    public SetOperationsAdvancedPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Set Operations & Analysis");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(PRIMARY_COLOR);
        add(titleLabel, BorderLayout.NORTH);
        
        // Main content
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Left panel - Input
        contentPanel.add(createInputPanel());
        
        // Right panel - Results
        contentPanel.add(createResultsPanel());
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            "Input Sets", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR));
        
        // Set A
        JPanel setAPanel = new JPanel(new BorderLayout(5, 5));
        setAPanel.setBackground(Color.WHITE);
        JLabel setALabel = new JLabel("Set A (comma-separated):");
        setALabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        setAInput = new JTextArea(3, 20);
        setAInput.setFont(new Font("Consolas", Font.PLAIN, 11));
        setAInput.setLineWrap(true);
        setAInput.setText("1,2,3,4,5");
        setAPanel.add(setALabel, BorderLayout.NORTH);
        setAPanel.add(new JScrollPane(setAInput), BorderLayout.CENTER);
        
        // Set B
        JPanel setBPanel = new JPanel(new BorderLayout(5, 5));
        setBPanel.setBackground(Color.WHITE);
        JLabel setBLabel = new JLabel("Set B (comma-separated):");
        setBLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        setBInput = new JTextArea(3, 20);
        setBInput.setFont(new Font("Consolas", Font.PLAIN, 11));
        setBInput.setLineWrap(true);
        setBInput.setText("3,4,5,6,7");
        setBPanel.add(setBLabel, BorderLayout.NORTH);
        setBPanel.add(new JScrollPane(setBInput), BorderLayout.CENTER);
        
        // Operations
        JPanel opPanel = new JPanel(new BorderLayout(5, 5));
        opPanel.setBackground(Color.WHITE);
        JLabel opLabel = new JLabel("Select Operation:");
        opLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        operationCombo = new JComboBox<>(new String[]{
            "Union (A ∪ B)",
            "Intersection (A ∩ B)",
            "Difference (A - B)",
            "Symmetric Difference",
            "Cartesian Product",
            "Power Set A",
            "Power Set B",
            "Subset Check (A ⊆ B)",
            "Superset Check (A ⊇ B)",
            "Equal Sets Check"
        });
        operationCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        opPanel.add(opLabel, BorderLayout.NORTH);
        opPanel.add(operationCombo, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton computeBtn = createButton("Compute", PRIMARY_COLOR);
        computeBtn.addActionListener(e -> computeOperation());
        
        JButton clearBtn = createButton("Clear", SECONDARY_COLOR);
        clearBtn.addActionListener(e -> {
            setAInput.setText("");
            setBInput.setText("");
            resultArea.setText("");
        });
        
        JButton sampleBtn = createButton("Load Sample", new Color(76, 175, 80));
        sampleBtn.addActionListener(e -> loadSampleData());
        
        buttonPanel.add(computeBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(sampleBtn);
        
        // Combine panels
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.setBackground(Color.WHITE);
        inputsPanel.add(setAPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(setBPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(opPanel);
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
            "Results & Analysis", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), SECONDARY_COLOR));
        
        // Results text area
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(250, 250, 250));
        resultArea.setText("Results will appear here...");
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private void computeOperation() {
        try {
            java.util.Set<String> setA = parseSet(setAInput.getText());
            java.util.Set<String> setB = parseSet(setBInput.getText());
            
            String operation = (String) operationCombo.getSelectedItem();
            SetOperationsModule module = new SetOperationsModule();
            
            StringBuilder result = new StringBuilder();
            result.append("Set A: ").append(setA).append("\n");
            result.append("Set B: ").append(setB).append("\n");
            result.append("Operation: ").append(operation).append("\n\n");
            result.append("═══════════════════════════════\n\n");
            
            if (operation.contains("Union")) {
                java.util.Set<String> union = module.union(setA, setB);
                result.append("Result: ").append(union).append("\n");
                result.append("Cardinality: |A ∪ B| = ").append(union.size()).append("\n");
            } else if (operation.contains("Intersection")) {
                java.util.Set<String> intersection = module.intersection(setA, setB);
                result.append("Result: ").append(intersection).append("\n");
                result.append("Cardinality: |A ∩ B| = ").append(intersection.size()).append("\n");
                if (intersection.isEmpty()) {
                    result.append("→ Sets are disjoint (no common elements)\n");
                }
            } else if (operation.contains("Difference")) {
                java.util.Set<String> difference = module.difference(setA, setB);
                result.append("Result (A - B): ").append(difference).append("\n");
                result.append("Cardinality: |A - B| = ").append(difference.size()).append("\n");
            } else if (operation.contains("Symmetric")) {
                java.util.Set<String> symDiff = new java.util.HashSet<>(module.difference(setA, setB));
                symDiff.addAll(module.difference(setB, setA));
                result.append("Result (A △ B): ").append(symDiff).append("\n");
                result.append("Cardinality: |A △ B| = ").append(symDiff.size()).append("\n");
            } else if (operation.contains("Cartesian")) {
                result.append("Cartesian Product A × B:\n");
                int count = 0;
                for (String a : setA) {
                    for (String b : setB) {
                        result.append("(").append(a).append(", ").append(b).append(")");
                        count++;
                        if (count < setA.size() * setB.size()) result.append(", ");
                    }
                }
                result.append("\n\nCardinality: |A × B| = ").append(setA.size() * setB.size()).append("\n");
            } else if (operation.contains("Power Set A")) {
                java.util.Set<java.util.Set<String>> powerSet = module.powerSet(setA);
                result.append("Power Set of A: ℘(A)\n");
                int count = 0;
                for (java.util.Set<String> subset : powerSet) {
                    result.append(subset.isEmpty() ? "∅" : subset.toString());
                    count++;
                    if (count < powerSet.size()) result.append(", ");
                }
                result.append("\n\nCardinality: |℘(A)| = 2^").append(setA.size()).append(" = ").append(powerSet.size()).append("\n");
            } else if (operation.contains("Power Set B")) {
                java.util.Set<java.util.Set<String>> powerSet = module.powerSet(setB);
                result.append("Power Set of B: ℘(B)\n");
                int count = 0;
                for (java.util.Set<String> subset : powerSet) {
                    result.append(subset.isEmpty() ? "∅" : subset.toString());
                    count++;
                    if (count < powerSet.size()) result.append(", ");
                }
                result.append("\n\nCardinality: |℘(B)| = 2^").append(setB.size()).append(" = ").append(powerSet.size()).append("\n");
            } else if (operation.contains("Subset")) {
                boolean isSubset = module.isSubset(setA, setB);
                result.append("Is A ⊆ B? ").append(isSubset ? "YES" : "NO").append("\n");
                if (isSubset) {
                    result.append("→ Every element of A is in B\n");
                } else {
                    result.append("→ Some elements of A are not in B\n");
                }
            } else if (operation.contains("Superset")) {
                boolean isSuperset = module.isSubset(setB, setA);
                result.append("Is A ⊇ B? ").append(isSuperset ? "YES" : "NO").append("\n");
                if (isSuperset) {
                    result.append("→ Every element of B is in A\n");
                } else {
                    result.append("→ Some elements of B are not in A\n");
                }
            } else if (operation.contains("Equal")) {
                boolean isEqual = setA.equals(setB);
                result.append("Are sets equal? ").append(isEqual ? "YES" : "NO").append("\n");
                if (!isEqual) {
                    java.util.Set<String> onlyInA = new java.util.HashSet<>(setA);
                    onlyInA.removeAll(setB);
                    java.util.Set<String> onlyInB = new java.util.HashSet<>(setB);
                    onlyInB.removeAll(setA);
                    if (!onlyInA.isEmpty()) result.append("Only in A: ").append(onlyInA).append("\n");
                    if (!onlyInB.isEmpty()) result.append("Only in B: ").append(onlyInB).append("\n");
                }
            }
            
            resultArea.setText(result.toString());
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    private void loadSampleData() {
        setAInput.setText("1,2,3,4,5");
        setBInput.setText("3,4,5,6,7");
        operationCombo.setSelectedIndex(0);
    }
    
    private java.util.Set<String> parseSet(String text) {
        java.util.Set<String> set = new java.util.HashSet<>();
        if (text.trim().isEmpty()) return set;
        
        String[] elements = text.split(",");
        for (String elem : elements) {
            String trimmed = elem.trim();
            if (!trimmed.isEmpty()) {
                set.add(trimmed);
            }
        }
        return set;
    }
    
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(
                    Math.max(0, color.getRed() - 20),
                    Math.max(0, color.getGreen() - 20),
                    Math.max(0, color.getBlue() - 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        return btn;
    }
}
