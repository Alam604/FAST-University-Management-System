package com.fast.ui.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import com.fast.data.DataManager;

public class FunctionsAnalysisRealWorldPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color WARNING_COLOR = new Color(255, 152, 0);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    
    private JComboBox<String> scenarioCombo;
    private JTextArea domainDisplay, codomainDisplay, mappingDisplay;
    private JTextArea resultArea;
    private JPanel chartPanel;
    private JLabel statsLabel;
    private Map<String, Set<String>> currentResults;
    private Set<String> currentDomain, currentCodomain;
    
    public FunctionsAnalysisRealWorldPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Functions & Mapping - University System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(PRIMARY_COLOR);
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Left panel - Input
        contentPanel.add(createInputPanel(), BorderLayout.WEST);
        
        // Center panel - Analysis & Charts
        contentPanel.add(createAnalysisPanel(), BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(350, 0));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            "Real-World Scenarios", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR));
        
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.setBackground(Color.WHITE);
        
        // Scenario selection
        JPanel scenarioPanel = new JPanel(new BorderLayout(5, 5));
        scenarioPanel.setBackground(Color.WHITE);
        JLabel scenarioLabel = new JLabel("Select Function:");
        scenarioLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        scenarioCombo = new JComboBox<>(new String[]{
            "Student → Grade (Injective?)",
            "Course → Department (Function?)",
            "StudentID → Email (Bijection?)",
            "Enrollment Count → Course",
            "Attendance % → Pass/Fail",
            "Student ID → Advisory"
        });
        scenarioCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        scenarioCombo.addActionListener(e -> loadScenario());
        scenarioPanel.add(scenarioLabel, BorderLayout.NORTH);
        scenarioPanel.add(scenarioCombo, BorderLayout.CENTER);
        
        // Domain display
        JPanel domainPanel = new JPanel(new BorderLayout(5, 5));
        domainPanel.setBackground(Color.WHITE);
        JLabel domainLabel = new JLabel("Domain (Input Set):");
        domainLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        domainDisplay = new JTextArea(3, 30);
        domainDisplay.setFont(new Font("Consolas", Font.PLAIN, 9));
        domainDisplay.setEditable(false);
        domainDisplay.setLineWrap(true);
        domainPanel.add(domainLabel, BorderLayout.NORTH);
        domainPanel.add(new JScrollPane(domainDisplay), BorderLayout.CENTER);
        
        // Codomain display
        JPanel codomainPanel = new JPanel(new BorderLayout(5, 5));
        codomainPanel.setBackground(Color.WHITE);
        JLabel codomainLabel = new JLabel("Codomain (Output Set):");
        codomainLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        codomainDisplay = new JTextArea(3, 30);
        codomainDisplay.setFont(new Font("Consolas", Font.PLAIN, 9));
        codomainDisplay.setEditable(false);
        codomainDisplay.setLineWrap(true);
        codomainPanel.add(codomainLabel, BorderLayout.NORTH);
        codomainPanel.add(new JScrollPane(codomainDisplay), BorderLayout.CENTER);
        
        // Mapping display
        JPanel mappingPanel = new JPanel(new BorderLayout(5, 5));
        mappingPanel.setBackground(Color.WHITE);
        JLabel mappingLabel = new JLabel("Mappings:");
        mappingLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        mappingDisplay = new JTextArea(3, 30);
        mappingDisplay.setFont(new Font("Consolas", Font.PLAIN, 9));
        mappingDisplay.setEditable(false);
        mappingDisplay.setLineWrap(true);
        mappingPanel.add(mappingLabel, BorderLayout.NORTH);
        mappingPanel.add(new JScrollPane(mappingDisplay), BorderLayout.CENTER);
        
        // Quick analysis buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Quick Analysis"));
        
        JButton injectiveBtn = createQuickButton("✓ Injective?", new Color(76, 175, 80), e -> analyzeInjective());
        JButton surjectiveBtn = createQuickButton("✓ Surjective?", new Color(33, 150, 243), e -> analyzeSurjective());
        JButton bijectiveBtn = createQuickButton("✓ Bijective?", new Color(156, 39, 176), e -> analyzeBijective());
        JButton validityBtn = createQuickButton("Valid Function?", new Color(255, 87, 34), e -> checkValidity());
        JButton graphBtn = createQuickButton("📊 Show Graph", PRIMARY_COLOR, e -> showGraph());
        JButton countBtn = createQuickButton("📈 Count Stats", WARNING_COLOR, e -> countStats());
        
        buttonPanel.add(injectiveBtn);
        buttonPanel.add(surjectiveBtn);
        buttonPanel.add(bijectiveBtn);
        buttonPanel.add(validityBtn);
        buttonPanel.add(graphBtn);
        buttonPanel.add(countBtn);
        
        statsLabel = new JLabel("Status: Ready");
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        statsLabel.setForeground(new Color(100, 100, 100));
        
        inputsPanel.add(scenarioPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(domainPanel);
        inputsPanel.add(Box.createVerticalStrut(8));
        inputsPanel.add(codomainPanel);
        inputsPanel.add(Box.createVerticalStrut(8));
        inputsPanel.add(mappingPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(buttonPanel);
        inputsPanel.add(Box.createVerticalStrut(5));
        inputsPanel.add(statsLabel);
        inputsPanel.add(Box.createVerticalGlue());
        
        panel.add(inputsPanel, BorderLayout.NORTH);
        return panel;
    }
    
    private JPanel createAnalysisPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Results text area
        JPanel textPanel = new JPanel(new BorderLayout(5, 5));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
            "Analysis Results", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), SECONDARY_COLOR));
        
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 10));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(250, 250, 250));
        resultArea.setText("Select a scenario and click 'Load Scenario' or use Quick Analysis buttons...");
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        textPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Chart panel
        JPanel chartContainer = new JPanel(new BorderLayout(5, 5));
        chartContainer.setBackground(Color.WHITE);
        chartContainer.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
            "Visualization", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(76, 175, 80)));
        
        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawDefaultChart((Graphics2D) g);
            }
        };
        chartPanel.setBackground(new Color(250, 250, 250));
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        
        panel.add(textPanel);
        panel.add(chartContainer);
        
        return panel;
    }
    
    private void loadScenario() {
        try {
            String scenario = (String) scenarioCombo.getSelectedItem();
            currentResults = new HashMap<>();
            currentDomain = new HashSet<>();
            currentCodomain = new HashSet<>();
            
            java.util.List<String[]> allStudents = DataManager.getAllStudents();
            java.util.List<String[]> allCourses = DataManager.getAllCourses();
            
            if (scenario.contains("Grade")) {
                // Student → Grade mapping
                for (String[] student : allStudents) {
                    if (student.length > 1) {
                        currentDomain.add(student[1]); // Student name
                        currentCodomain.add("A");
                        currentCodomain.add("B");
                        currentCodomain.add("C");
                    }
                }
                // Simulate mapping
                int gradeIndex = 0;
                String[] grades = {"A", "B", "C", "A", "B"};
                for (String student : currentDomain) {
                    currentResults.put(student, new HashSet<>(java.util.Arrays.asList(grades[gradeIndex % grades.length])));
                    gradeIndex++;
                }
            }
            else if (scenario.contains("Department")) {
                // Course → Department (Should be function: each course one dept)
                for (String[] course : allCourses) {
                    if (course.length > 0) {
                        currentDomain.add(course[0]); // Course code
                    }
                    if (course.length > 3) {
                        currentCodomain.add(course[3]); // Department
                    }
                }
                // Each course maps to its department
                for (String[] course : allCourses) {
                    if (course.length > 3) {
                        currentResults.put(course[0], new HashSet<>(java.util.Arrays.asList(course[3])));
                    }
                }
            }
            else if (scenario.contains("Email")) {
                // Student ID → Email (bijective: each student has unique email)
                for (String[] student : allStudents) {
                    if (student.length > 0) {
                        currentDomain.add(student[0]); // Student ID
                        currentCodomain.add(student[0] + "@university.edu"); // Email
                        currentResults.put(student[0], new HashSet<>(
                            java.util.Arrays.asList(student[0] + "@university.edu")));
                    }
                }
            }
            else if (scenario.contains("Enrollment")) {
                // Enrollment count → Course (many-to-one)
                currentDomain.add("5+ students");
                currentDomain.add("10+ students");
                currentDomain.add("15+ students");
                for (String[] course : allCourses) {
                    if (course.length > 0) {
                        currentCodomain.add(course[0]);
                    }
                }
                // Simulate enrollment mapping
                currentResults.put("5+ students", new HashSet<>(java.util.Arrays.asList("CS101", "MATH101")));
                currentResults.put("10+ students", new HashSet<>(java.util.Arrays.asList("CS201")));
                currentResults.put("15+ students", new HashSet<>(java.util.Arrays.asList("CS301")));
            }
            else if (scenario.contains("Attendance")) {
                // Attendance % → Pass/Fail
                currentDomain.add("0-50%");
                currentDomain.add("50-75%");
                currentDomain.add("75-100%");
                currentCodomain.add("FAIL");
                currentCodomain.add("PASS");
                
                currentResults.put("0-50%", new HashSet<>(java.util.Arrays.asList("FAIL")));
                currentResults.put("50-75%", new HashSet<>(java.util.Arrays.asList("PASS")));
                currentResults.put("75-100%", new HashSet<>(java.util.Arrays.asList("PASS")));
            }
            else if (scenario.contains("Advisory")) {
                // Student ID → Advisor (many-to-one)
                for (String[] student : allStudents) {
                    if (student.length > 0) {
                        currentDomain.add(student[0]); // Student ID
                    }
                }
                // Simulate advisors
                currentCodomain.add("Dr. Smith");
                currentCodomain.add("Dr. Jones");
                currentCodomain.add("Dr. Brown");
                
                int advisorIdx = 0;
                String[] advisors = {"Dr. Smith", "Dr. Jones", "Dr. Brown"};
                for (String student : currentDomain) {
                    currentResults.put(student, new HashSet<>(
                        java.util.Arrays.asList(advisors[advisorIdx % advisors.length])));
                    advisorIdx++;
                }
            }
            
            updateDisplay();
            resultArea.setText("✓ Scenario loaded. Use Quick Analysis buttons to analyze...\n\n" +
                "Domain size: " + currentDomain.size() + "\nCodomain size: " + currentCodomain.size() + 
                "\nTotal mappings: " + currentResults.size());
            chartPanel.repaint();
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    private void updateDisplay() {
        domainDisplay.setText(currentDomain.toString());
        codomainDisplay.setText(currentCodomain.toString());
        
        StringBuilder mappings = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, Set<String>> entry : currentResults.entrySet()) {
            if (count > 0) mappings.append("\n");
            mappings.append(entry.getKey()).append(" → ").append(entry.getValue());
            count++;
        }
        mappingDisplay.setText(mappings.toString());
    }
    
    private void analyzeInjective() {
        if (currentResults == null || currentResults.isEmpty()) {
            resultArea.setText("Please load a scenario first.");
            return;
        }
        
        // Check if injective (no two inputs map to same output)
        Set<String> outputs = new HashSet<>();
        boolean isInjective = true;
        
        for (Set<String> outputSet : currentResults.values()) {
            for (String output : outputSet) {
                if (outputs.contains(output)) {
                    isInjective = false;
                    break;
                }
                outputs.add(output);
            }
        }
        
        StringBuilder result = new StringBuilder();
        result.append("════════════════════════════════════════════\n");
        result.append("INJECTIVE (ONE-TO-ONE) ANALYSIS\n");
        result.append("════════════════════════════════════════════\n\n");
        result.append("Definition: Each input maps to UNIQUE output\n");
        result.append("No two different inputs map to same output\n\n");
        result.append("Is this function injective? ").append(isInjective ? "✓ YES" : "✗ NO").append("\n\n");
        
        if (isInjective) {
            result.append("✓ Every element in domain maps to different element in codomain\n");
            result.append("✓ Mathematical notation: f(a) = f(b) ⟹ a = b\n");
        } else {
            result.append("✗ Some elements in domain map to same element\n");
            result.append("✗ At least two different inputs produce same output\n");
        }
        
        result.append("\nREAL-WORLD EXAMPLE:\n");
        String scenario = (String) scenarioCombo.getSelectedItem();
        if (scenario.contains("Grade")) {
            result.append("- Multiple students can get same grade (NOT injective)\n");
        } else if (scenario.contains("Email")) {
            result.append("- Each student has unique email (INJECTIVE)\n");
        } else if (scenario.contains("Advisory")) {
            result.append("- Multiple students share same advisor (NOT injective)\n");
        }
        
        resultArea.setText(result.toString());
        statsLabel.setText("Status: Injective analysis complete");
        chartPanel.repaint();
    }
    
    private void analyzeSurjective() {
        if (currentResults == null || currentResults.isEmpty()) {
            resultArea.setText("Please load a scenario first.");
            return;
        }
        
        // Check if surjective (every output is mapped from some input)
        Set<String> mappedOutputs = new HashSet<>();
        for (Set<String> outputSet : currentResults.values()) {
            mappedOutputs.addAll(outputSet);
        }
        
        boolean isSurjective = mappedOutputs.equals(currentCodomain);
        
        StringBuilder result = new StringBuilder();
        result.append("════════════════════════════════════════════\n");
        result.append("SURJECTIVE (ONTO) ANALYSIS\n");
        result.append("════════════════════════════════════════════\n\n");
        result.append("Definition: Every codomain element is OUTPUT\n");
        result.append("No element in codomain is left unmapped\n\n");
        result.append("Is this function surjective? ").append(isSurjective ? "✓ YES" : "✗ NO").append("\n\n");
        
        if (isSurjective) {
            result.append("✓ Every element in codomain has pre-image in domain\n");
            result.append("✓ Range = Codomain\n");
        } else {
            result.append("✗ Some elements in codomain are never reached\n");
            result.append("✗ Range ≠ Codomain\n");
            
            Set<String> unmapped = new HashSet<>(currentCodomain);
            unmapped.removeAll(mappedOutputs);
            result.append("✗ Unmapped outputs: ").append(unmapped).append("\n");
        }
        
        result.append("\nREAL-WORLD EXAMPLE:\n");
        String scenario = (String) scenarioCombo.getSelectedItem();
        if (scenario.contains("Grade")) {
            result.append("- Possible not all grades are given (NOT surjective)\n");
        } else if (scenario.contains("Advisory")) {
            result.append("- All advisors have at least one student (SURJECTIVE)\n");
        }
        
        resultArea.setText(result.toString());
        statsLabel.setText("Status: Surjective analysis complete");
        chartPanel.repaint();
    }
    
    private void analyzeBijective() {
        if (currentResults == null || currentResults.isEmpty()) {
            resultArea.setText("Please load a scenario first.");
            return;
        }
        
        // Check both injective and surjective
        Set<String> outputs = new HashSet<>();
        boolean isInjective = true;
        for (Set<String> outputSet : currentResults.values()) {
            for (String output : outputSet) {
                if (outputs.contains(output)) {
                    isInjective = false;
                    break;
                }
                outputs.add(output);
            }
        }
        
        Set<String> mappedOutputs = new HashSet<>();
        for (Set<String> outputSet : currentResults.values()) {
            mappedOutputs.addAll(outputSet);
        }
        boolean isSurjective = mappedOutputs.equals(currentCodomain);
        boolean isBijective = isInjective && isSurjective;
        
        StringBuilder result = new StringBuilder();
        result.append("════════════════════════════════════════════\n");
        result.append("BIJECTIVE (ONE-TO-ONE & ONTO) ANALYSIS\n");
        result.append("════════════════════════════════════════════\n\n");
        result.append("Definition: Both Injective AND Surjective\n");
        result.append("Perfect one-to-one correspondence\n\n");
        result.append("Is this function bijective? ").append(isBijective ? "✓ YES" : "✗ NO").append("\n");
        result.append("  - Injective: ").append(isInjective ? "✓ YES" : "✗ NO").append("\n");
        result.append("  - Surjective: ").append(isSurjective ? "✓ YES" : "✗ NO").append("\n\n");
        
        if (isBijective) {
            result.append("✓ Perfect one-to-one correspondence exists\n");
            result.append("✓ |Domain| = |Codomain|\n");
            result.append("✓ Inverse function exists\n");
        } else {
            result.append("✗ Not a perfect one-to-one correspondence\n");
        }
        
        result.append("\nREAL-WORLD EXAMPLE:\n");
        String scenario = (String) scenarioCombo.getSelectedItem();
        if (scenario.contains("Email")) {
            result.append("- Each student has exactly one unique email (BIJECTIVE)\n");
        } else {
            result.append("- Most real functions are NOT bijective\n");
        }
        
        resultArea.setText(result.toString());
        statsLabel.setText("Status: Bijective analysis complete");
        chartPanel.repaint();
    }
    
    private void checkValidity() {
        if (currentResults == null || currentResults.isEmpty()) {
            resultArea.setText("Please load a scenario first.");
            return;
        }
        
        StringBuilder result = new StringBuilder();
        result.append("════════════════════════════════════════════\n");
        result.append("FUNCTION VALIDITY CHECK\n");
        result.append("════════════════════════════════════════════\n\n");
        
        boolean isValid = true;
        String reason = "";
        
        // Check 1: Each domain element maps to exactly one element
        boolean oneToOne = true;
        for (Set<String> outputs : currentResults.values()) {
            if (outputs.size() > 1) {
                oneToOne = false;
                break;
            }
        }
        
        if (oneToOne) {
            result.append("✓ Each input maps to EXACTLY ONE output\n");
        } else {
            result.append("✗ Some inputs map to multiple outputs\n");
            isValid = false;
        }
        
        // Check 2: All domain elements are covered
        boolean allDomainCovered = currentResults.keySet().equals(currentDomain);
        if (allDomainCovered) {
            result.append("✓ All domain elements are mapped\n");
        } else {
            result.append("✗ Some domain elements are not mapped\n");
            isValid = false;
        }
        
        result.append("\n");
        result.append("Is this a VALID FUNCTION? ").append(isValid ? "✓ YES" : "✗ NO").append("\n\n");
        
        result.append("Function Requirements:\n");
        result.append("1. Each input must have exactly one output ").append(oneToOne ? "✓" : "✗").append("\n");
        result.append("2. All domain elements must map ").append(allDomainCovered ? "✓" : "✗").append("\n");
        
        resultArea.setText(result.toString());
        statsLabel.setText("Status: Validity check complete");
        chartPanel.repaint();
    }
    
    private void showGraph() {
        chartPanel.repaint();
        resultArea.setText("Graph visualization generated in the right panel.\n\n" +
            "Domain Elements: " + currentDomain.size() + "\n" +
            "Codomain Elements: " + currentCodomain.size() + "\n" +
            "Total Mappings: " + currentResults.size());
        statsLabel.setText("Status: Graph displayed");
    }
    
    private void countStats() {
        if (currentResults == null || currentResults.isEmpty()) {
            resultArea.setText("Please load a scenario first.");
            return;
        }
        
        StringBuilder result = new StringBuilder();
        result.append("════════════════════════════════════════════\n");
        result.append("FUNCTION STATISTICS & COUNTING\n");
        result.append("════════════════════════════════════════════\n\n");
        
        int domainSize = currentDomain.size();
        int codomainSize = currentCodomain.size();
        
        result.append("CARDINALITY:\n");
        result.append("  |Domain| = ").append(domainSize).append("\n");
        result.append("  |Codomain| = ").append(codomainSize).append("\n\n");
        
        result.append("COUNTING FUNCTIONS:\n");
        long totalFunctions = factorial(codomainSize);
        for (int i = 0; i < domainSize - 1; i++) {
            totalFunctions *= codomainSize;
        }
        result.append("  Total possible functions: ").append(codomainSize).append("^").append(domainSize).append(" = ")
            .append(totalFunctions).append("\n\n");
        
        // Calculate injective functions: P(codomain, domain)
        long injectiveFunctions = 1;
        if (domainSize <= codomainSize) {
            for (int i = 0; i < domainSize; i++) {
                injectiveFunctions *= (codomainSize - i);
            }
            result.append("  Injective functions: P(").append(codomainSize).append(",").append(domainSize)
                .append(") = ").append(injectiveFunctions).append("\n");
        } else {
            result.append("  Injective functions: 0 (domain > codomain)\n");
        }
        
        result.append("\nMAPPING ANALYSIS:\n");
        Set<String> allMapped = new HashSet<>();
        for (Set<String> outputs : currentResults.values()) {
            allMapped.addAll(outputs);
        }
        result.append("  Elements actually mapped: ").append(allMapped.size()).append("\n");
        result.append("  Elements in codomain: ").append(codomainSize).append("\n");
        result.append("  Coverage ratio: ").append(String.format("%.1f%%", 
            (allMapped.size() * 100.0) / codomainSize)).append("\n");
        
        resultArea.setText(result.toString());
        statsLabel.setText("Status: Statistics calculated");
    }
    
    private void drawDefaultChart(Graphics2D g) {
        int width = chartPanel.getWidth();
        int height = chartPanel.getHeight();
        
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, width, height);
        
        if (currentDomain == null || currentDomain.isEmpty()) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            g.drawString("Load a scenario to view mapping diagram", width / 2 - 150, height / 2);
            return;
        }
        
        g.setColor(PRIMARY_COLOR);
        g.setStroke(new BasicStroke(2));
        
        // Draw domain on left
        int domainY = 50;
        int domainSpacing = (height - 100) / Math.max(currentDomain.size(), 1);
        int idx = 0;
        Map<String, Integer> domainPositions = new HashMap<>();
        
        g.setColor(PRIMARY_COLOR);
        g.setFont(new Font("Segoe UI", Font.BOLD, 11));
        g.drawString("Domain", 20, 30);
        
        for (String domain : currentDomain) {
            int y = 50 + idx * domainSpacing;
            g.fillOval(20, y, 15, 15);
            domainPositions.put(domain, y + 7);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g.drawString(domain.length() > 10 ? domain.substring(0, 10) + "..." : domain, 45, y + 12);
            g.setColor(PRIMARY_COLOR);
            idx++;
        }
        
        // Draw codomain on right
        int codomainY = 50;
        int codomainSpacing = (height - 100) / Math.max(currentCodomain.size(), 1);
        idx = 0;
        Map<String, Integer> codomainPositions = new HashMap<>();
        
        g.setColor(SECONDARY_COLOR);
        g.setFont(new Font("Segoe UI", Font.BOLD, 11));
        g.drawString("Codomain", width - 120, 30);
        
        for (String codomain : currentCodomain) {
            int y = 50 + idx * codomainSpacing;
            g.fillOval(width - 35, y, 15, 15);
            codomainPositions.put(codomain, y + 7);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g.drawString(codomain.length() > 10 ? codomain.substring(0, 10) + "..." : codomain, 
                width - 150, y + 12);
            g.setColor(SECONDARY_COLOR);
            idx++;
        }
        
        // Draw arrows for mappings
        g.setColor(new Color(100, 100, 100, 100));
        g.setStroke(new BasicStroke(1.5f));
        for (Map.Entry<String, Set<String>> entry : currentResults.entrySet()) {
            Integer fromY = domainPositions.get(entry.getKey());
            if (fromY != null) {
                for (String to : entry.getValue()) {
                    Integer toY = codomainPositions.get(to);
                    if (toY != null) {
                        g.drawLine(35, fromY, width - 35, toY);
                    }
                }
            }
        }
    }
    
    private long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    private JButton createQuickButton(String text, Color color, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.addActionListener(listener);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(
                    Math.min(255, color.getRed() + 20),
                    Math.min(255, color.getGreen() + 20),
                    Math.min(255, color.getBlue() + 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(color);
            }
        });
        return btn;
    }
}
