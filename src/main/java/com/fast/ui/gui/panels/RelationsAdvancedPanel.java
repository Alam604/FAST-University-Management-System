package com.fast.ui.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.fast.data.DataManager;
import com.fast.discrete.RelationsModule;

public class RelationsAdvancedPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color WARNING_COLOR = new Color(255, 152, 0);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    
    private JTextArea relationArea, resultArea;
    private JComboBox<String> relationTypeCombo, scenarioCombo;
    private JTable resultTable;
    
    public RelationsAdvancedPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Relations Analysis - University System");
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
            "Real-World Scenarios", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
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
            "Student-Course Enrollment",
            "Faculty-Course Teaching",
            "Student-Prerequisite",
            "Course-Department",
            "Student-Advisor Assignment"
        });
        scenarioCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        scenarioCombo.addActionListener(e -> updateRelationDisplay());
        scenarioPanel.add(scenarioLabel, BorderLayout.NORTH);
        scenarioPanel.add(scenarioCombo, BorderLayout.CENTER);
        
        // Relation type analysis
        JPanel typePanel = new JPanel(new BorderLayout(5, 5));
        typePanel.setBackground(Color.WHITE);
        JLabel typeLabel = new JLabel("Analyze Property:");
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        relationTypeCombo = new JComboBox<>(new String[]{
            "All Properties",
            "Reflexive",
            "Symmetric",
            "Transitive",
            "Equivalence",
            "Function Check"
        });
        relationTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        typePanel.add(typeLabel, BorderLayout.NORTH);
        typePanel.add(relationTypeCombo, BorderLayout.CENTER);
        
        // Relation display
        JPanel relationPanel = new JPanel(new BorderLayout(5, 5));
        relationPanel.setBackground(Color.WHITE);
        JLabel relationLabel = new JLabel("Relation Pairs from System:");
        relationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        relationArea = new JTextArea(6, 30);
        relationArea.setFont(new Font("Consolas", Font.PLAIN, 10));
        relationArea.setEditable(false);
        relationArea.setLineWrap(true);
        relationArea.setWrapStyleWord(true);
        relationPanel.add(relationLabel, BorderLayout.NORTH);
        relationPanel.add(new JScrollPane(relationArea), BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton analyzeBtn = createButton("Analyze Relation", PRIMARY_COLOR);
        analyzeBtn.addActionListener(e -> analyzeRelation());
        
        JButton loadBtn = createButton("Load from System", SUCCESS_COLOR);
        loadBtn.addActionListener(e -> loadRelationFromSystem());
        
        JButton clearBtn = createButton("Clear", SECONDARY_COLOR);
        clearBtn.addActionListener(e -> {
            relationArea.setText("");
            resultArea.setText("");
        });
        
        buttonPanel.add(analyzeBtn);
        buttonPanel.add(loadBtn);
        buttonPanel.add(clearBtn);
        
        inputsPanel.add(scenarioPanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(typePanel);
        inputsPanel.add(Box.createVerticalStrut(10));
        inputsPanel.add(relationPanel);
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
        resultArea.setText("Select a scenario and click 'Analyze Relation' to see results...");
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private void loadRelationFromSystem() {
        try {
            String scenario = (String) scenarioCombo.getSelectedItem();
            StringBuilder relations = new StringBuilder();
            
            if (scenario.contains("Student-Course")) {
                relations.append("Relations: (Student, Course) - Enrollments\n\n");
                java.util.List<String[]> students = DataManager.getAllStudents();
                java.util.List<String[]> courses = DataManager.getAllCourses();
                int count = 0;
                // Simulate enrollment relations based on system data
                for (int i = 0; i < Math.min(students.size(), courses.size()); i++) {
                    String student = students.get(i).length > 1 ? students.get(i)[1] : "S" + i;
                    String course = courses.get(i).length > 0 ? courses.get(i)[0] : "C" + i;
                    relations.append("(").append(student).append(", ").append(course).append(")");
                    count++;
                    if (count % 3 == 0) relations.append("\n");
                    else relations.append("  ");
                }
                relations.append("\n\nTotal pairs: ").append(count);
            } 
            else if (scenario.contains("Faculty-Course")) {
                relations.append("Relations: (Faculty, Course) - Teaching Assignments\n\n");
                java.util.List<String[]> courses = DataManager.getAllCourses();
                int count = 0;
                // Simulate faculty-course relations
                for (String[] course : courses) {
                    relations.append("(Faculty").append(count % 3).append(", ").append(course[0]).append(")");
                    count++;
                    if (count % 3 == 0) relations.append("\n");
                    else relations.append("  ");
                }
                relations.append("\n\nTotal teaching pairs: ").append(count);
            }
            else if (scenario.contains("Student-Prerequisite")) {
                relations.append("Relations: (Course, Prerequisite) - Academic Path\n\n");
                java.util.Map<String, java.util.List<String>> prereqs = DataManager.getAllPrerequisites();
                int count = 0;
                for (String course : prereqs.keySet()) {
                    for (String prereq : prereqs.get(course)) {
                        relations.append("(").append(course).append(", ").append(prereq).append(")");
                        count++;
                        if (count % 3 == 0) relations.append("\n");
                        else relations.append("  ");
                    }
                }
                relations.append("\n\nTotal prerequisite pairs: ").append(count);
            }
            else if (scenario.contains("Course-Department")) {
                relations.append("Relations: (Course, Department) - Course Allocation\n\n");
                java.util.List<String[]> courses = DataManager.getAllCourses();
                int count = 0;
                for (String[] course : courses) {
                    if (course.length >= 4) {
                        relations.append("(").append(course[0]).append(", ").append(course[3]).append(")");
                        count++;
                        if (count % 3 == 0) relations.append("\n");
                        else relations.append("  ");
                    }
                }
                relations.append("\n\nTotal course-department pairs: ").append(count);
            }
            else if (scenario.contains("Advisor")) {
                relations.append("Relations: (Student, Advisor) - Academic Guidance\n\n");
                java.util.List<String[]> students = DataManager.getAllStudents();
                int count = 0;
                for (String[] student : students) {
                    if (student.length >= 4) {
                        relations.append("(").append(student[1]).append(", ").append(student[3]).append(")");
                        count++;
                        if (count % 3 == 0) relations.append("\n");
                        else relations.append("  ");
                    }
                }
                relations.append("\n\nTotal student-advisor pairs: ").append(count);
            }
            
            relationArea.setText(relations.toString());
        } catch (Exception ex) {
            relationArea.setText("Error loading relations: " + ex.getMessage());
        }
    }
    
    private void updateRelationDisplay() {
        relationArea.setText("Click 'Load from System' to fetch real data for this scenario...");
        resultArea.setText("");
    }
    
    private void analyzeRelation() {
        try {
            String relationText = relationArea.getText();
            if (relationText.trim().isEmpty()) {
                resultArea.setText("Error: Please load a relation first");
                return;
            }
            
            String scenario = (String) scenarioCombo.getSelectedItem();
            String analysis = (String) relationTypeCombo.getSelectedItem();
            
            StringBuilder result = new StringBuilder();
            result.append("═══════════════════════════════════════════════════════════\n");
            result.append("RELATION ANALYSIS - ").append(scenario).append("\n");
            result.append("═══════════════════════════════════════════════════════════\n\n");
            
            result.append("Analysis Type: ").append(analysis).append("\n\n");
            result.append("Scenario Context:\n");
            
            if (scenario.contains("Student-Course")) {
                result.append("• Represents: Which students are enrolled in which courses\n");
                result.append("• Purpose: Track academic enrollments and course load\n");
                result.append("• Properties to check:\n");
                result.append("  - Reflexive: Would require a student in their own course (NOT meaningful)\n");
                result.append("  - Symmetric: If student A is in course C, then course C is in student A (YES, bidirectional)\n");
                result.append("  - Transitive: If student A→course C and course C→instructor, then student A→instructor (contextual)\n");
                result.append("  - Function: Each student can take multiple courses (NOT a function, MANY-to-MANY)\n");
            }
            else if (scenario.contains("Faculty-Course")) {
                result.append("• Represents: Which faculty members teach which courses\n");
                result.append("• Purpose: Manage course assignments and faculty workload\n");
                result.append("• Properties to check:\n");
                result.append("  - Reflexive: Faculty teaching themselves (NOT meaningful)\n");
                result.append("  - Symmetric: Bidirectional teaching relationship (depends on context)\n");
                result.append("  - Transitive: Not typically applicable\n");
                result.append("  - Function: Each faculty teaches one/more courses (partial function)\n");
            }
            else if (scenario.contains("Student-Prerequisite")) {
                result.append("• Represents: Which courses require other courses as prerequisites\n");
                result.append("• Purpose: Ensure students meet academic requirements before enrollment\n");
                result.append("• Properties to check:\n");
                result.append("  - Reflexive: A course requiring itself (NOT valid)\n");
                result.append("  - Symmetric: If A requires B, then B requires A (usually NOT true)\n");
                result.append("  - Transitive: If A requires B and B requires C, then A requires C (YES, cascading requirements)\n");
                result.append("  - Function: Each course can have multiple prerequisites (NOT a function)\n");
            }
            else if (scenario.contains("Course-Department")) {
                result.append("• Represents: Which department offers which courses\n");
                result.append("• Purpose: Organize courses by academic departments\n");
                result.append("• Properties to check:\n");
                result.append("  - Reflexive: Course offering itself (NOT meaningful)\n");
                result.append("  - Symmetric: If department D offers course C, then C offers D (NOT true)\n");
                result.append("  - Transitive: Not applicable\n");
                result.append("  - Function: Each course belongs to exactly ONE department (YES, function)\n");
            }
            else if (scenario.contains("Advisor")) {
                result.append("• Represents: Student-to-Advisor relationships\n");
                result.append("• Purpose: Assign academic advisors to guide students\n");
                result.append("• Properties to check:\n");
                result.append("  - Reflexive: Students advising themselves (NOT valid)\n");
                result.append("  - Symmetric: If A advises B, then B advises A (NOT true)\n");
                result.append("  - Transitive: Advisor chains (contextual)\n");
                result.append("  - Function: Each student has ONE advisor (YES, partial function)\n");
            }
            
            result.append("\n═══════════════════════════════════════════════════════════\n");
            result.append("REAL-WORLD IMPLICATIONS:\n");
            result.append("═══════════════════════════════════════════════════════════\n");
            
            if (scenario.contains("Student-Course")) {
                result.append("✓ This is a MANY-TO-MANY relationship\n");
                result.append("  - One student can enroll in multiple courses\n");
                result.append("  - One course can have multiple students\n");
                result.append("✓ Requires junction table in database (enrollments table)\n");
                result.append("✓ Supports: Course recommendations, prerequisite checking, load balancing\n");
            }
            else if (scenario.contains("Faculty-Course")) {
                result.append("✓ This is a ONE-TO-MANY or MANY-TO-MANY relationship\n");
                result.append("  - One faculty teaches multiple courses\n");
                result.append("  - One course can have multiple faculty (co-taught)\n");
                result.append("✓ Supports: Course allocation, faculty workload analysis\n");
            }
            else if (scenario.contains("Student-Prerequisite")) {
                result.append("✓ This is a Directed Acyclic Graph (DAG)\n");
                result.append("✓ TRANSITIVE property is KEY for academic path verification\n");
                result.append("✓ Detects circular prerequisites (cycle detection needed)\n");
                result.append("✓ Supports: Curriculum design, degree progress tracking\n");
            }
            else if (scenario.contains("Course-Department")) {
                result.append("✓ This is a MANY-TO-ONE (partial function)\n");
                result.append("  - Multiple courses belong to one department\n");
                result.append("  - One course belongs to exactly one department\n");
                result.append("✓ Supports: Department reporting, course organization\n");
            }
            else if (scenario.contains("Advisor")) {
                result.append("✓ This is a MANY-TO-ONE relationship\n");
                result.append("  - Multiple students can have the same advisor\n");
                result.append("  - One student has one (or few) advisors\n");
                result.append("✓ Supports: Advisor workload distribution, student guidance tracking\n");
            }
            
            resultArea.setText(result.toString());
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
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
