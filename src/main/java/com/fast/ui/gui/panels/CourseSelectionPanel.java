package com.fast.ui.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import com.fast.data.DataManager;
import com.fast.discrete.CourseSchedulingModule;

public class CourseSelectionPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private JTable courseTable;
    private JTextArea prerequisitesArea;
    private JComboBox<String> studentCombo;
    private JComboBox<String> courseCombo;
    
    public CourseSelectionPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JLabel title = new JLabel("Course Selection with Prerequisites");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY_COLOR);
        
        panel.add(title);
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Left: Courses table
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.setBorder(BorderFactory.createTitledBorder("Available Courses"));
        
        String[] columns = {"Course Code", "Name", "Credits"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        courseTable = new JTable(model);
        courseTable.setBackground(Color.WHITE);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(courseTable);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshBtn = new JButton("Refresh Courses");
        refreshBtn.setBackground(PRIMARY_COLOR);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> refreshCourses());
        leftPanel.add(refreshBtn, BorderLayout.SOUTH);
        
        // Right: Prerequisites
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightPanel.setBorder(BorderFactory.createTitledBorder("Prerequisites & Sequence"));
        
        prerequisitesArea = new JTextArea();
        prerequisitesArea.setEditable(false);
        prerequisitesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        prerequisitesArea.setBackground(new Color(240, 240, 240));
        
        JScrollPane scrollPane2 = new JScrollPane(prerequisitesArea);
        rightPanel.add(scrollPane2, BorderLayout.CENTER);
        
        courseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = courseTable.getSelectedRow();
                if (row >= 0) {
                    String courseCode = (String) courseTable.getValueAt(row, 0);
                    displayPrerequisites(courseCode);
                }
            }
        });
        
        panel.add(leftPanel);
        panel.add(rightPanel);
        
        refreshCourses();
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        JLabel studentLabel = new JLabel("Student:");
        studentCombo = new JComboBox<>();
        studentCombo.addItem("-- Select Student --");
        
        for (String[] student : DataManager.getAllStudents()) {
            studentCombo.addItem(student[0] + " - " + student[1]);
        }
        
        JLabel courseLabel = new JLabel("Course:");
        courseCombo = new JComboBox<>();
        courseCombo.addItem("-- Select Course --");
        
        for (String[] course : DataManager.getAllCourses()) {
            courseCombo.addItem(course[0] + " - " + course[1]);
        }
        
        JButton enrollBtn = new JButton("Enroll Student");
        enrollBtn.setBackground(PRIMARY_COLOR);
        enrollBtn.setForeground(Color.WHITE);
        enrollBtn.addActionListener(e -> enrollStudent());
        
        panel.add(studentLabel);
        panel.add(studentCombo);
        panel.add(courseLabel);
        panel.add(courseCombo);
        panel.add(enrollBtn);
        
        return panel;
    }
    
    private void refreshCourses() {
        DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
        model.setRowCount(0);
        
        for (String[] course : DataManager.getAllCourses()) {
            model.addRow(new Object[]{course[0], course[1], course[2]});
        }
    }
    
    private void displayPrerequisites(String courseCode) {
        java.util.Map<String, java.util.List<String>> allPrereqs = DataManager.getAllPrerequisites();
        java.util.List<String> coursePrereqs = allPrereqs.getOrDefault(courseCode, new java.util.ArrayList<>());
        
        StringBuilder sb = new StringBuilder();
        sb.append("Course: ").append(courseCode).append("\n\n");
        
        if (coursePrereqs.isEmpty()) {
            sb.append("✓ No prerequisites required\n");
        } else {
            sb.append("Prerequisites:\n");
            for (String prereq : coursePrereqs) {
                sb.append("  • ").append(prereq).append("\n");
            }
        }
        
        sb.append("\n--- Course Sequence ---\n");
        CourseSchedulingModule scheduler = new CourseSchedulingModule();
        java.util.Set<String> allCoursesSet = new java.util.HashSet<>();
        
        // Add all courses first
        for (String[] course : DataManager.getAllCourses()) {
            allCoursesSet.add(course[0]);
        }
        
        // Then add prerequisites to scheduler
        for (java.util.Map.Entry<String, java.util.List<String>> entry : allPrereqs.entrySet()) {
            String course = entry.getKey();
            for (String prereq : entry.getValue()) {
                scheduler.setPrerequisite(course, prereq);
            }
        }
        
        try {
            java.util.List<String> sequence = scheduler.generateValidCourseSequence(allCoursesSet);
            sb.append("Valid sequence:\n");
            for (int i = 0; i < sequence.size(); i++) {
                sb.append((i + 1)).append(". ").append(sequence.get(i)).append("\n");
            }
        } catch (Exception e) {
            sb.append("Error generating sequence: ").append(e.getMessage());
        }
        
        prerequisitesArea.setText(sb.toString());
    }
    
    private void enrollStudent() {
        int studentIdx = studentCombo.getSelectedIndex();
        int courseIdx = courseCombo.getSelectedIndex();
        
        if (studentIdx <= 0 || courseIdx <= 0) {
            JOptionPane.showMessageDialog(this, "Please select both student and course!", 
                "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String studentData = (String) studentCombo.getSelectedItem();
        String courseData = (String) courseCombo.getSelectedItem();
        
        String studentId = studentData.split(" - ")[0];
        String courseCode = courseData.split(" - ")[0];
        
        DataManager.addEnrollment(studentId, courseCode);
        JOptionPane.showMessageDialog(this, "Student enrolled successfully!", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
