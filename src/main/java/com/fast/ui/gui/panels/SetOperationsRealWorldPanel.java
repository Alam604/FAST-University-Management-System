package com.fast.ui.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.fast.data.DataManager;
import com.fast.discrete.SetOperationsModule;

public class SetOperationsRealWorldPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    
    private JComboBox<String> scenarioCombo, setACombo, setBCombo, operationCombo;
    private JTextArea setADisplay, setBDisplay, resultArea;
    private JLabel statsLabel;
    
    public SetOperationsRealWorldPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Set Operations - Real-World University System");
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
            "System Scenarios", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR));
        
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.setBackground(Color.WHITE);
        
        // Scenario selection
        JPanel scenarioPanel = new JPanel(new BorderLayout(5, 5));
        scenarioPanel.setBackground(Color.WHITE);
        JLabel scenarioLabel = new JLabel("Select Scenario:");
        scenarioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        scenarioCombo = new JComboBox<>(new String[]{
            "Student Enrollment Analysis",
            "Course Offering Analysis",
            "Department Management",
            "Faculty Assignment",
            "Prerequisite Chains"
        });
        scenarioCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        scenarioCombo.addActionListener(e -> updateScenario());
        scenarioPanel.add(scenarioLabel, BorderLayout.NORTH);
        scenarioPanel.add(scenarioCombo, BorderLayout.CENTER);
        
        // Set A selection
        JPanel setAPanel = new JPanel(new BorderLayout(5, 5));
        setAPanel.setBackground(Color.WHITE);
        JLabel setALabel = new JLabel("Set A (from system):");
        setALabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        setACombo = new JComboBox<>();
        setACombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        setACombo.addActionListener(e -> refreshSetDisplay());
        setAPanel.add(setALabel, BorderLayout.NORTH);
        setAPanel.add(setACombo, BorderLayout.CENTER);
        
        // Set B selection
        JPanel setBPanel = new JPanel(new BorderLayout(5, 5));
        setBPanel.setBackground(Color.WHITE);
        JLabel setBLabel = new JLabel("Set B (from system):");
        setBLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        setBCombo = new JComboBox<>();
        setBCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        setBCombo.addActionListener(e -> refreshSetDisplay());
        setBPanel.add(setBLabel, BorderLayout.NORTH);
        setBPanel.add(setBCombo, BorderLayout.CENTER);
        
        // Set A display
        JPanel displayAPanel = new JPanel(new BorderLayout(5, 5));
        displayAPanel.setBackground(Color.WHITE);
        JLabel displayALabel = new JLabel("Set A Elements:");
        displayALabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        setADisplay = new JTextArea(2, 25);
        setADisplay.setFont(new Font("Consolas", Font.PLAIN, 10));
        setADisplay.setEditable(false);
        setADisplay.setLineWrap(true);
        displayAPanel.add(displayALabel, BorderLayout.NORTH);
        displayAPanel.add(new JScrollPane(setADisplay), BorderLayout.CENTER);
        
        // Set B display
        JPanel displayBPanel = new JPanel(new BorderLayout(5, 5));
        displayBPanel.setBackground(Color.WHITE);
        JLabel displayBLabel = new JLabel("Set B Elements:");
        displayBLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        setBDisplay = new JTextArea(2, 25);
        setBDisplay.setFont(new Font("Consolas", Font.PLAIN, 10));
        setBDisplay.setEditable(false);
        setBDisplay.setLineWrap(true);
        displayBPanel.add(displayBLabel, BorderLayout.NORTH);
        displayBPanel.add(new JScrollPane(setBDisplay), BorderLayout.CENTER);
        
        // Operation selection
        JPanel opPanel = new JPanel(new BorderLayout(5, 5));
        opPanel.setBackground(Color.WHITE);
        JLabel opLabel = new JLabel("Set Operation:");
        opLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        operationCombo = new JComboBox<>(new String[]{
            "Union (A ∪ B) - All in either set",
            "Intersection (A ∩ B) - Common elements",
            "Difference (A - B) - In A but not B",
            "Symmetric Difference - In either but not both",
            "Cartesian Product - All pairs",
            "Power Set of A",
            "Subset Check (A ⊆ B)",
            "Superset Check (A ⊇ B)",
            "Equal Check"
        });
        operationCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        opPanel.add(opLabel, BorderLayout.NORTH);
        opPanel.add(operationCombo, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton computeBtn = createButton("Compute Operation", PRIMARY_COLOR);
        computeBtn.addActionListener(e -> computeOperation());
        
        JButton loadBtn = createButton("Load Scenario Data", SUCCESS_COLOR);
        loadBtn.addActionListener(e -> loadScenarioData());
        
        buttonPanel.add(computeBtn);
        buttonPanel.add(loadBtn);
        
        // Statistics
        statsLabel = new JLabel("Statistics: ");
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        statsLabel.setForeground(new Color(100, 100, 100));
        
        inputsPanel.add(scenarioPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(setAPanel);
        inputsPanel.add(Box.createVerticalStrut(5));
        inputsPanel.add(displayAPanel);
        inputsPanel.add(Box.createVerticalStrut(5));
        inputsPanel.add(setBPanel);
        inputsPanel.add(Box.createVerticalStrut(5));
        inputsPanel.add(displayBPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(opPanel);
        inputsPanel.add(Box.createVerticalStrut(5));
        inputsPanel.add(buttonPanel);
        inputsPanel.add(Box.createVerticalStrut(5));
        inputsPanel.add(statsLabel);
        
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
        
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(250, 250, 250));
        resultArea.setText("Select a scenario and click 'Load Scenario Data' to begin...");
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private void updateScenario() {
        setACombo.removeAllItems();
        setBCombo.removeAllItems();
        String scenario = (String) scenarioCombo.getSelectedItem();
        
        if (scenario.contains("Student")) {
            setACombo.addItem("All Students");
            setACombo.addItem("Active Students");
            setACombo.addItem("By Department");
            setBCombo.addItem("Enrolled in Courses");
            setBCombo.addItem("Have Advisors");
            setBCombo.addItem("On Academic Probation");
        }
        else if (scenario.contains("Course")) {
            setACombo.addItem("All Courses");
            setACombo.addItem("CS Courses");
            setACombo.addItem("Core Courses");
            setBCombo.addItem("With Prerequisites");
            setBCombo.addItem("By Semester");
            setBCombo.addItem("Full Capacity");
        }
        else if (scenario.contains("Department")) {
            setACombo.addItem("All Departments");
            setACombo.addItem("Engineering");
            setACombo.addItem("Sciences");
            setBCombo.addItem("With Active Faculty");
            setBCombo.addItem("Offering Courses");
            setBCombo.addItem("By Budget");
        }
        else if (scenario.contains("Faculty")) {
            setACombo.addItem("All Faculty");
            setACombo.addItem("Full-Time Faculty");
            setACombo.addItem("By Department");
            setBCombo.addItem("Teaching Courses");
            setBCombo.addItem("With Advisees");
            setBCombo.addItem("Active Researchers");
        }
        else if (scenario.contains("Prerequisite")) {
            setACombo.addItem("All Courses");
            setACombo.addItem("Foundation Courses");
            setACombo.addItem("Advanced Courses");
            setBCombo.addItem("Courses with Prerequisites");
            setBCombo.addItem("Required for Others");
            setBCombo.addItem("Electives");
        }
    }
    
    private void refreshSetDisplay() {
        // Placeholder - will be populated by loadScenarioData
    }
    
    private void loadScenarioData() {
        try {
            String scenario = (String) scenarioCombo.getSelectedItem();
            String setAOption = (String) setACombo.getSelectedItem();
            String setBOption = (String) setBCombo.getSelectedItem();
            
            Set<String> setA = new HashSet<>();
            Set<String> setB = new HashSet<>();
            
            if (scenario.contains("Student")) {
                java.util.List<String[]> allStudents = DataManager.getAllStudents();
                for (String[] student : allStudents) {
                    if (student.length > 1) {
                        setA.add(student[1]); // Student name
                    }
                }
                // Create subset for setB (e.g., students with courses)
                setB.addAll(setA);
                setB.remove(setB.iterator().next()); // Remove one for demonstration
            }
            else if (scenario.contains("Course")) {
                java.util.List<String[]> allCourses = DataManager.getAllCourses();
                for (String[] course : allCourses) {
                    if (course.length > 0) {
                        setA.add(course[0]); // Course code
                    }
                }
                // Create subset
                setB.addAll(setA);
                if (!setB.isEmpty()) {
                    Iterator<String> it = setB.iterator();
                    it.next();
                    it.remove();
                }
            }
            else if (scenario.contains("Department")) {
                java.util.List<String[]> allCourses = DataManager.getAllCourses();
                for (String[] course : allCourses) {
                    if (course.length > 0) {
                        setA.add(course[0]); // Course code
                    }
                }
                // Create subset
                setB.addAll(setA);
                if (!setB.isEmpty()) {
                    Iterator<String> it = setB.iterator();
                    it.next();
                    it.remove();
                }
            }
            else if (scenario.contains("Faculty")) {
                java.util.List<String[]> allCourses = DataManager.getAllCourses();
                for (String[] course : allCourses) {
                    if (course.length > 0) {
                        setA.add(course[0]); // Course code
                    }
                }
                setB.addAll(setA);
            }
            else if (scenario.contains("Prerequisite")) {
                java.util.Map<String, java.util.List<String>> prereqs = DataManager.getAllPrerequisites();
                setA.addAll(prereqs.keySet());
                for (java.util.List<String> preqList : prereqs.values()) {
                    setB.addAll(preqList);
                }
            }
            
            setADisplay.setText(setA.toString());
            setBDisplay.setText(setB.toString());
            
            Set<String> common = new HashSet<>(setA);
            common.retainAll(setB);
            
            statsLabel.setText(String.format("Statistics: |A|=%d, |B|=%d, |A∩B|=%d, |A∪B|=%d", 
                setA.size(), setB.size(), common.size(), setA.size() + setB.size() - common.size()));
            
            resultArea.setText("✓ Scenario data loaded successfully\nReady to perform set operations...");
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    private void computeOperation() {
        try {
            String setAText = setADisplay.getText().trim();
            String setBText = setBDisplay.getText().trim();
            
            if (setAText.isEmpty() || setBText.isEmpty()) {
                resultArea.setText("Error: Please load scenario data first");
                return;
            }
            
            Set<String> setA = parseSet(setAText);
            Set<String> setB = parseSet(setBText);
            String operation = (String) operationCombo.getSelectedItem();
            String scenario = (String) scenarioCombo.getSelectedItem();
            
            StringBuilder result = new StringBuilder();
            result.append("═══════════════════════════════════════════════════════════\n");
            result.append("SET OPERATION RESULT - ").append(scenario).append("\n");
            result.append("═══════════════════════════════════════════════════════════\n\n");
            
            result.append("Set A: ").append(setA).append(" (|A| = ").append(setA.size()).append(")\n");
            result.append("Set B: ").append(setB).append(" (|B| = ").append(setB.size()).append(")\n\n");
            result.append("Operation: ").append(operation).append("\n\n");
            result.append("───────────────────────────────────────────────────────────\n\n");
            
            SetOperationsModule module = new SetOperationsModule();
            
            if (operation.contains("Union")) {
                Set<String> union = module.union(setA, setB);
                result.append("RESULT: A ∪ B = ").append(union).append("\n");
                result.append("|A ∪ B| = ").append(union.size()).append("\n\n");
                result.append("Meaning: All unique elements from both sets\n");
                result.append("Formula: |A ∪ B| = |A| + |B| - |A ∩ B| = ")
                    .append(setA.size()).append(" + ").append(setB.size()).append(" - ")
                    .append(computeIntersection(setA, setB).size()).append(" = ").append(union.size()).append("\n");
            }
            else if (operation.contains("Intersection")) {
                Set<String> intersection = computeIntersection(setA, setB);
                result.append("RESULT: A ∩ B = ").append(intersection).append("\n");
                result.append("|A ∩ B| = ").append(intersection.size()).append("\n\n");
                result.append("Meaning: Elements common to both sets\n");
                if (intersection.isEmpty()) {
                    result.append("→ Sets are DISJOINT (no common elements)\n");
                }
            }
            else if (operation.contains("Difference")) {
                Set<String> difference = module.difference(setA, setB);
                result.append("RESULT: A - B = ").append(difference).append("\n");
                result.append("|A - B| = ").append(difference.size()).append("\n\n");
                result.append("Meaning: Elements in A but not in B\n");
            }
            else if (operation.contains("Symmetric")) {
                Set<String> symDiff = new HashSet<>(module.difference(setA, setB));
                symDiff.addAll(module.difference(setB, setA));
                result.append("RESULT: A △ B = ").append(symDiff).append("\n");
                result.append("|A △ B| = ").append(symDiff.size()).append("\n\n");
                result.append("Meaning: Elements in either but not both\n");
            }
            else if (operation.contains("Cartesian")) {
                result.append("RESULT: A × B contains ").append(setA.size() * setB.size()).append(" ordered pairs:\n");
                int count = 0;
                for (String a : setA) {
                    for (String b : setB) {
                        result.append("(").append(a).append(", ").append(b).append(")");
                        count++;
                        if (count < setA.size() * setB.size()) result.append(", ");
                        if (count % 3 == 0) result.append("\n");
                    }
                }
                result.append("\n\n|A × B| = |A| × |B| = ").append(setA.size()).append(" × ").append(setB.size())
                    .append(" = ").append(setA.size() * setB.size()).append("\n");
            }
            else if (operation.contains("Power")) {
                Set<Set<String>> powerSet = module.powerSet(setA);
                result.append("Power Set ℘(A) has ").append(powerSet.size()).append(" subsets\n");
                result.append("|℘(A)| = 2^|A| = 2^").append(setA.size()).append(" = ").append(powerSet.size()).append("\n\n");
            }
            else if (operation.contains("Subset")) {
                boolean isSubset = module.isSubset(setA, setB);
                result.append("Is A ⊆ B? ").append(isSubset ? "YES" : "NO").append("\n\n");
            }
            else if (operation.contains("Superset")) {
                boolean isSuperset = module.isSubset(setB, setA);
                result.append("Is A ⊇ B? ").append(isSuperset ? "YES" : "NO").append("\n\n");
            }
            else if (operation.contains("Equal")) {
                boolean equal = setA.equals(setB);
                result.append("Are A and B equal? ").append(equal ? "YES" : "NO").append("\n\n");
            }
            
            result.append("───────────────────────────────────────────────────────────\n");
            result.append("REAL-WORLD APPLICATION:\n");
            
            if (scenario.contains("Student")) {
                result.append("→ Use union to find all students involved in operations\n");
                result.append("→ Use intersection to find students meeting multiple criteria\n");
                result.append("→ Use difference to find students with specific properties\n");
            }
            else if (scenario.contains("Course")) {
                result.append("→ Use operations to manage course catalogs\n");
                result.append("→ Find overlapping prerequisites or requirements\n");
            }
            
            resultArea.setText(result.toString());
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    private Set<String> parseSet(String text) {
        Set<String> set = new HashSet<>();
        text = text.replace("[", "").replace("]", "").replace("{", "").replace("}", "");
        String[] elements = text.split(",");
        for (String elem : elements) {
            String trimmed = elem.trim();
            if (!trimmed.isEmpty()) {
                set.add(trimmed);
            }
        }
        return set;
    }
    
    private Set<String> computeIntersection(Set<String> a, Set<String> b) {
        Set<String> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        return intersection;
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
