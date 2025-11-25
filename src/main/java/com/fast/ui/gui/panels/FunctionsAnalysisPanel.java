package com.fast.ui.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import com.fast.discrete.FunctionsModule;

public class FunctionsAnalysisPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    
    private JTextArea domainInput, codomainInput, mappingInput;
    private JTextArea resultArea;
    private JComboBox<String> propertyCombo;
    private DefaultTableModel tableModel;
    
    public FunctionsAnalysisPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Functions & Mapping Analysis");
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
            "Function Definition", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR));
        
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.setBackground(Color.WHITE);
        
        // Domain input
        JPanel domainPanel = new JPanel(new BorderLayout(5, 5));
        domainPanel.setBackground(Color.WHITE);
        JLabel domainLabel = new JLabel("Domain (comma-separated):");
        domainLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        domainInput = new JTextArea(3, 20);
        domainInput.setFont(new Font("Consolas", Font.PLAIN, 11));
        domainInput.setText("s1,s2,s3");
        domainPanel.add(domainLabel, BorderLayout.NORTH);
        domainPanel.add(new JScrollPane(domainInput), BorderLayout.CENTER);
        
        // Codomain input
        JPanel codomainPanel = new JPanel(new BorderLayout(5, 5));
        codomainPanel.setBackground(Color.WHITE);
        JLabel codomainLabel = new JLabel("Codomain (comma-separated):");
        codomainLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        codomainInput = new JTextArea(3, 20);
        codomainInput.setFont(new Font("Consolas", Font.PLAIN, 11));
        codomainInput.setText("c1,c2,c3,c4");
        codomainPanel.add(codomainLabel, BorderLayout.NORTH);
        codomainPanel.add(new JScrollPane(codomainInput), BorderLayout.CENTER);
        
        // Mapping input (format: s1->c1, s2->c2, s3->c3)
        JPanel mappingPanel = new JPanel(new BorderLayout(5, 5));
        mappingPanel.setBackground(Color.WHITE);
        JLabel mappingLabel = new JLabel("Mappings (format: a->b, c->d, ...):");
        mappingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        mappingInput = new JTextArea(3, 20);
        mappingInput.setFont(new Font("Consolas", Font.PLAIN, 11));
        mappingInput.setText("s1->c1,s2->c2,s3->c3");
        mappingPanel.add(mappingLabel, BorderLayout.NORTH);
        mappingPanel.add(new JScrollPane(mappingInput), BorderLayout.CENTER);
        
        // Property selection
        JPanel propPanel = new JPanel(new BorderLayout(5, 5));
        propPanel.setBackground(Color.WHITE);
        JLabel propLabel = new JLabel("Test Property:");
        propLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        propertyCombo = new JComboBox<>(new String[]{
            "Check All Properties",
            "Injective (One-to-One)",
            "Surjective (Onto)",
            "Bijective (One-to-One & Onto)",
            "Function Validity",
            "Count Injective Functions",
            "Count Surjective Functions"
        });
        propertyCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        propPanel.add(propLabel, BorderLayout.NORTH);
        propPanel.add(propertyCombo, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton analyzeBtn = createButton("Analyze Function", PRIMARY_COLOR);
        analyzeBtn.addActionListener(e -> analyzeFunction());
        
        JButton sampleBtn = createButton("Load Sample", SUCCESS_COLOR);
        sampleBtn.addActionListener(e -> loadSampleData());
        
        buttonPanel.add(analyzeBtn);
        buttonPanel.add(sampleBtn);
        
        inputsPanel.add(domainPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(codomainPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(mappingPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(propPanel);
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
            "Analysis Results", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), SECONDARY_COLOR));
        
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(250, 250, 250));
        resultArea.setText("Analysis results will appear here...");
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private void analyzeFunction() {
        try {
            java.util.Set<String> domain = parseSet(domainInput.getText());
            java.util.Set<String> codomain = parseSet(codomainInput.getText());
            java.util.Map<String, String> mapping = parseMapping(mappingInput.getText());
            
            StringBuilder result = new StringBuilder();
            result.append("Domain: ").append(domain).append(" |D| = ").append(domain.size()).append("\n");
            result.append("Codomain: ").append(codomain).append(" |C| = ").append(codomain.size()).append("\n");
            result.append("Mappings: ").append(mapping).append("\n\n");
            result.append("════════════════════════════════════\n\n");
            
            String property = (String) propertyCombo.getSelectedItem();
            
            // Check if valid function first
            boolean isValidFunction = isValidFunction(domain, mapping);
            result.append("Valid Function? ").append(isValidFunction ? "✓ YES" : "✗ NO").append("\n\n");
            
            if (!isValidFunction) {
                result.append("→ Not all domain elements have exactly one mapping!\n");
                resultArea.setText(result.toString());
                return;
            }
            
            java.util.Set<String> range = new java.util.HashSet<>(mapping.values());
            boolean isInjective = isInjective(mapping);
            boolean isSurjective = isSurjective(range, codomain);
            boolean isBijective = isInjective && isSurjective;
            
            result.append("Range: ").append(range).append(" |R| = ").append(range.size()).append("\n");
            result.append("Injective (1-to-1)? ").append(isInjective ? "✓ YES" : "✗ NO").append("\n");
            result.append("Surjective (Onto)? ").append(isSurjective ? "✓ YES" : "✗ NO").append("\n");
            result.append("Bijective? ").append(isBijective ? "✓ YES" : "✗ NO").append("\n\n");
            
            result.append("─────────────────────────────────────\n\n");
            
            if (property.contains("Injective")) {
                result.append("INJECTIVE TEST:\n");
                result.append("Every element of Domain maps to different elements in Range.\n");
                result.append("Result: ").append(isInjective ? "✓ PASS" : "✗ FAIL").append("\n");
            } else if (property.contains("Surjective")) {
                result.append("SURJECTIVE TEST:\n");
                result.append("Every element of Codomain is mapped from some element in Domain.\n");
                result.append("Result: ").append(isSurjective ? "✓ PASS" : "✗ FAIL").append("\n");
                if (!isSurjective) {
                    java.util.Set<String> unmapped = new java.util.HashSet<>(codomain);
                    unmapped.removeAll(range);
                    result.append("Unmapped elements: ").append(unmapped).append("\n");
                }
            } else if (property.contains("Bijective")) {
                result.append("BIJECTIVE TEST:\n");
                result.append("Must be both Injective AND Surjective.\n");
                result.append("Result: ").append(isBijective ? "✓ PASS" : "✗ FAIL").append("\n");
            } else if (property.contains("Injective Functions")) {
                result.append("Possible Injective Functions:\n");
                long count = calculateInjectiveFunctions(domain.size(), codomain.size());
                result.append("Count: ").append(count).append("\n");
            } else if (property.contains("Surjective Functions")) {
                result.append("Possible Surjective Functions:\n");
                long count = calculateSurjectiveFunctions(domain.size(), codomain.size());
                result.append("Count: ").append(count).append("\n");
            } else {
                result.append("ALL PROPERTIES:\n\n");
                result.append("✓ Valid Function: YES\n");
                result.append("✓ Injective: ").append(isInjective ? "YES" : "NO").append("\n");
                result.append("✓ Surjective: ").append(isSurjective ? "YES" : "NO").append("\n");
                result.append("✓ Bijective: ").append(isBijective ? "YES" : "NO").append("\n");
                result.append("\nFunction Type: ");
                if (isBijective) {
                    result.append("BIJECTION (Perfect 1-to-1 correspondence)\n");
                } else if (isInjective) {
                    result.append("INJECTION (No two elements map to same output)\n");
                } else if (isSurjective) {
                    result.append("SURJECTION (All outputs are used)\n");
                } else {
                    result.append("GENERAL FUNCTION\n");
                }
            }
            
            resultArea.setText(result.toString());
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    private boolean isValidFunction(java.util.Set<String> domain, java.util.Map<String, String> mapping) {
        for (String elem : domain) {
            if (!mapping.containsKey(elem)) {
                return false;
            }
        }
        return mapping.size() == domain.size();
    }
    
    private boolean isInjective(java.util.Map<String, String> mapping) {
        java.util.Set<String> values = new java.util.HashSet<>(mapping.values());
        return values.size() == mapping.size();
    }
    
    private boolean isSurjective(java.util.Set<String> range, java.util.Set<String> codomain) {
        return range.equals(codomain);
    }
    
    private long calculateInjectiveFunctions(int domainSize, int codomainSize) {
        if (domainSize > codomainSize) return 0;
        long result = 1;
        for (int i = 0; i < domainSize; i++) {
            result *= (codomainSize - i);
        }
        return result;
    }
    
    private long calculateSurjectiveFunctions(int domainSize, int codomainSize) {
        if (domainSize < codomainSize) return 0;
        // Stirling numbers approximation
        return factorial(codomainSize);
    }
    
    private long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }
    
    private void loadSampleData() {
        domainInput.setText("Student1,Student2,Student3");
        codomainInput.setText("Course1,Course2,Course3,Course4");
        mappingInput.setText("Student1->Course1,Student2->Course2,Student3->Course3");
        propertyCombo.setSelectedIndex(0);
    }
    
    private java.util.Set<String> parseSet(String text) {
        java.util.Set<String> set = new java.util.HashSet<>();
        if (text.trim().isEmpty()) return set;
        for (String elem : text.split(",")) {
            String trimmed = elem.trim();
            if (!trimmed.isEmpty()) set.add(trimmed);
        }
        return set;
    }
    
    private java.util.Map<String, String> parseMapping(String text) {
        java.util.Map<String, String> mapping = new java.util.HashMap<>();
        if (text.trim().isEmpty()) return mapping;
        for (String pair : text.split(",")) {
            String[] parts = pair.trim().split("->");
            if (parts.length == 2) {
                mapping.put(parts[0].trim(), parts[1].trim());
            }
        }
        return mapping;
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
