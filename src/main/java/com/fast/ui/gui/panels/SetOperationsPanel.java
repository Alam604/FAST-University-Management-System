package com.fast.ui.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.fast.discrete.SetOperationsModule;

public class SetOperationsPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private JTextArea set1Area, set2Area, resultArea;
    private JComboBox<String> operationCombo;
    
    public SetOperationsPanel() {
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
        
        JLabel title = new JLabel("Set Operations (Discrete Mathematics)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY_COLOR);
        
        panel.add(title);
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Set 1
        JPanel set1Panel = new JPanel(new BorderLayout(5, 5));
        set1Panel.setBackground(BACKGROUND_COLOR);
        set1Panel.setBorder(BorderFactory.createTitledBorder("Set A (comma-separated)"));
        set1Area = new JTextArea(8, 20);
        set1Area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        set1Area.setText("1, 2, 3, 4, 5");
        set1Panel.add(new JScrollPane(set1Area), BorderLayout.CENTER);
        
        // Set 2
        JPanel set2Panel = new JPanel(new BorderLayout(5, 5));
        set2Panel.setBackground(BACKGROUND_COLOR);
        set2Panel.setBorder(BorderFactory.createTitledBorder("Set B (comma-separated)"));
        set2Area = new JTextArea(8, 20);
        set2Area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        set2Area.setText("3, 4, 5, 6, 7");
        set2Panel.add(new JScrollPane(set2Area), BorderLayout.CENTER);
        
        // Result
        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBackground(BACKGROUND_COLOR);
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        resultArea = new JTextArea(8, 20);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBackground(new Color(240, 240, 240));
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        
        panel.add(set1Panel);
        panel.add(set2Panel);
        panel.add(resultPanel);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JLabel opLabel = new JLabel("Operation:");
        operationCombo = new JComboBox<>(new String[]{
            "Union", "Intersection", "Difference", "Power Set A", "Power Set B"
        });
        operationCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JButton executeBtn = new JButton("Execute");
        executeBtn.setBackground(PRIMARY_COLOR);
        executeBtn.setForeground(Color.WHITE);
        executeBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        executeBtn.addActionListener(e -> executeOperation());
        
        panel.add(opLabel);
        panel.add(operationCombo);
        panel.add(executeBtn);
        
        return panel;
    }
    
    private void executeOperation() {
        try {
            Set<Integer> setA = parseSet(set1Area.getText());
            Set<Integer> setB = parseSet(set2Area.getText());
            
            SetOperationsModule module = new SetOperationsModule();
            String operation = (String) operationCombo.getSelectedItem();
            Object result = null;
            
            switch (operation) {
                case "Union":
                    result = module.union(setA, setB);
                    break;
                case "Intersection":
                    result = module.intersection(setA, setB);
                    break;
                case "Difference":
                    result = module.difference(setA, setB);
                    break;
                case "Power Set A":
                    result = module.powerSet(setA);
                    break;
                case "Power Set B":
                    result = module.powerSet(setB);
                    break;
            }
            
            resultArea.setText(formatResult(operation, result));
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }
    
    private Set<Integer> parseSet(String text) {
        Set<Integer> set = new HashSet<>();
        String[] parts = text.split(",");
        for (String part : parts) {
            try {
                set.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {}
        }
        return set;
    }
    
    private String formatResult(String operation, Object result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Operation: ").append(operation).append("\n\n");
        
        if (result instanceof Set) {
            sb.append("Result Set: ").append(result).append("\n");
            Set<?> resSet = (Set<?>) result;
            sb.append("Cardinality: ").append(resSet.size());
        } else if (result instanceof Collection) {
            sb.append("Result:\n");
            Collection<?> coll = (Collection<?>) result;
            int count = 1;
            for (Object item : coll) {
                sb.append(count++).append(". ").append(item).append("\n");
            }
            sb.append("\nTotal: ").append(coll.size()).append(" elements");
        }
        
        return sb.toString();
    }
}
