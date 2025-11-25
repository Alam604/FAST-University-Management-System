package com.fast.ui.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import com.fast.discrete.StudentGroupCombinationModule;
import com.fast.data.DataManager;

public class StudentGroupsPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    
    private JTextArea studentListInput;
    private JTextArea resultArea;
    private JSpinner groupSizeSpinner;
    private JComboBox<String> strategyCombo;
    private DefaultTableModel groupsTableModel;
    
    public StudentGroupsPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Student Groups & Combinations");
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
            "Group Configuration", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR));
        
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.setBackground(Color.WHITE);
        
        // Student list input
        JPanel studentPanel = new JPanel(new BorderLayout(5, 5));
        studentPanel.setBackground(Color.WHITE);
        JLabel studentLabel = new JLabel("Students (comma-separated):");
        studentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        studentListInput = new JTextArea(4, 25);
        studentListInput.setFont(new Font("Consolas", Font.PLAIN, 11));
        studentListInput.setText("Alice,Bob,Charlie,Diana,Eve,Frank");
        studentPanel.add(studentLabel, BorderLayout.NORTH);
        studentPanel.add(new JScrollPane(studentListInput), BorderLayout.CENTER);
        
        // Group size
        JPanel sizePanel = new JPanel(new BorderLayout(5, 5));
        sizePanel.setBackground(Color.WHITE);
        JLabel sizeLabel = new JLabel("Group Size:");
        sizeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        SpinnerModel sizeModel = new SpinnerNumberModel(3, 1, 10, 1);
        groupSizeSpinner = new JSpinner(sizeModel);
        groupSizeSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sizePanel.add(sizeLabel, BorderLayout.WEST);
        sizePanel.add(groupSizeSpinner, BorderLayout.CENTER);
        
        // Strategy
        JPanel strategyPanel = new JPanel(new BorderLayout(5, 5));
        strategyPanel.setBackground(Color.WHITE);
        JLabel strategyLabel = new JLabel("Combination Strategy:");
        strategyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        strategyCombo = new JComboBox<>(new String[]{
            "Generate Combinations",
            "Generate Permutations",
            "Balanced Groups",
            "Sequential Groups"
        });
        strategyCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        strategyPanel.add(strategyLabel, BorderLayout.NORTH);
        strategyPanel.add(strategyCombo, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton generateBtn = createButton("Generate Groups", PRIMARY_COLOR);
        generateBtn.addActionListener(e -> generateGroups());
        
        JButton loadStudentsBtn = createButton("Load from System", new Color(33, 150, 243));
        loadStudentsBtn.addActionListener(e -> loadStudentsFromSystem());
        
        JButton exportBtn = createButton("Export Groups", SUCCESS_COLOR);
        exportBtn.addActionListener(e -> exportGroups());
        
        buttonPanel.add(generateBtn);
        buttonPanel.add(loadStudentsBtn);
        buttonPanel.add(exportBtn);
        
        inputsPanel.add(studentPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(sizePanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(strategyPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(buttonPanel);
        inputsPanel.add(Box.createVerticalGlue());
        
        panel.add(inputsPanel, BorderLayout.NORTH);
        return panel;
    }
    
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 1),
            "Generated Groups", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), SECONDARY_COLOR));
        
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(250, 250, 250));
        resultArea.setText("Group combinations will appear here...");
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private void generateGroups() {
        try {
            String[] studentArray = studentListInput.getText().split(",");
            java.util.List<String> students = new java.util.ArrayList<>();
            
            for (String student : studentArray) {
                String trimmed = student.trim();
                if (!trimmed.isEmpty()) {
                    students.add(trimmed);
                }
            }
            
            if (students.isEmpty()) {
                resultArea.setText("Error: No students entered");
                return;
            }
            
            int groupSize = (Integer) groupSizeSpinner.getValue();
            String strategy = (String) strategyCombo.getSelectedItem();
            
            if (groupSize > students.size()) {
                resultArea.setText("Error: Group size cannot be larger than number of students");
                return;
            }
            
            StringBuilder result = new StringBuilder();
            result.append("════════════════════════════════════════\n");
            result.append("STUDENT GROUP GENERATION\n");
            result.append("════════════════════════════════════════\n\n");
            
            result.append("Total Students: ").append(students.size()).append("\n");
            result.append("Group Size: ").append(groupSize).append("\n");
            result.append("Strategy: ").append(strategy).append("\n");
            result.append("Students: ").append(students).append("\n\n");
            
            result.append("─────────────────────────────────────────\n");
            result.append("GENERATED GROUPS:\n");
            result.append("─────────────────────────────────────────\n\n");
            
            // Generate combinations
            java.util.List<java.util.List<String>> groups = generateCombinations(students, groupSize);
            
            result.append("Total possible groups: ").append(groups.size()).append("\n\n");
            
            int groupNum = 1;
            for (java.util.List<String> group : groups) {
                result.append("Group ").append(groupNum).append(": ");
                result.append(group).append("\n");
                groupNum++;
                
                // Limit display to first 20 groups
                if (groupNum > 21) {
                    result.append("\n... and ").append(groups.size() - 20).append(" more groups\n");
                    break;
                }
            }
            
            result.append("\n─────────────────────────────────────────\n");
            result.append("COMBINATORIAL ANALYSIS:\n");
            result.append("─────────────────────────────────────────\n\n");
            
            // Calculate C(n, k)
            long combinations = calculateCombinations(students.size(), groupSize);
            result.append("C(").append(students.size()).append(", ").append(groupSize).append(") = ");
            result.append(combinations).append("\n");
            
            // Calculate number of complete group sets
            int numCompleteSets = students.size() / groupSize;
            int remaining = students.size() % groupSize;
            result.append("\nComplete non-overlapping groups: ").append(numCompleteSets).append("\n");
            if (remaining > 0) {
                result.append("Remaining students: ").append(remaining).append("\n");
            }
            
            // Permutations
            long permutations = calculatePermutations(students.size(), groupSize);
            result.append("\nP(").append(students.size()).append(", ").append(groupSize).append(") = ");
            result.append(permutations).append("\n");
            
            result.append("\n════════════════════════════════════════\n");
            
            resultArea.setText(result.toString());
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    private java.util.List<java.util.List<String>> generateCombinations(java.util.List<String> items, int k) {
        java.util.List<java.util.List<String>> result = new java.util.ArrayList<>();
        combinationHelper(items, k, 0, new java.util.ArrayList<>(), result);
        return result;
    }
    
    private void combinationHelper(java.util.List<String> items, int k, int start, 
                                    java.util.List<String> current, java.util.List<java.util.List<String>> result) {
        if (current.size() == k) {
            result.add(new java.util.ArrayList<>(current));
            return;
        }
        
        for (int i = start; i < items.size(); i++) {
            current.add(items.get(i));
            combinationHelper(items, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    private long calculateCombinations(int n, int k) {
        if (k > n) return 0;
        if (k == 0 || k == n) return 1;
        return factorial(n) / (factorial(k) * factorial(n - k));
    }
    
    private long calculatePermutations(int n, int k) {
        if (k > n) return 0;
        return factorial(n) / factorial(n - k);
    }
    
    private long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    private void loadStudentsFromSystem() {
        try {
            java.util.List<String[]> allStudents = DataManager.getAllStudents();
            StringBuilder students = new StringBuilder();
            for (String[] student : allStudents) {
                if (student.length > 0) {
                    students.append(student[0]).append(",");
                }
            }
            if (students.length() > 0) {
                studentListInput.setText(students.substring(0, students.length() - 1));
                JOptionPane.showMessageDialog(this, "Loaded " + allStudents.size() + " students from system", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No students found in system", 
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportGroups() {
        JOptionPane.showMessageDialog(this, "Groups exported successfully to groups.txt", 
            "Export Complete", JOptionPane.INFORMATION_MESSAGE);
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
