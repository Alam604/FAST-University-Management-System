package com.fast.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UniversityManagementUI extends JFrame {
    private JTabbedPane tabbedPane;

    public UniversityManagementUI() {
        setTitle("FAST University Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        // Add tabs for different functionalities
        tabbedPane.addTab("Students", new StudentPanel());
        tabbedPane.addTab("Courses", new CoursePanel());
        tabbedPane.addTab("Faculty", new FacultyPanel());
        tabbedPane.addTab("Departments", new DepartmentPanel());
        tabbedPane.addTab("Enrollments", new EnrollmentPanel());
        
        add(tabbedPane);
    }
}

class StudentPanel extends JPanel {
    public StudentPanel() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));
        
        formPanel.add(new JLabel("Student ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Major:"));
        JTextField majorField = new JTextField();
        formPanel.add(majorField);
        
        JButton addButton = new JButton("Add Student");
        JButton resetButton = new JButton("Reset");
        formPanel.add(addButton);
        formPanel.add(resetButton);
        
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        addButton.addActionListener(e -> {
            String output = String.format("Student Added:\nID: %s\nName: %s\nEmail: %s\nMajor: %s\n",
                    idField.getText(), nameField.getText(), emailField.getText(), majorField.getText());
            outputArea.append(output);
        });
        
        resetButton.addActionListener(e -> {
            idField.setText("");
            nameField.setText("");
            emailField.setText("");
            majorField.setText("");
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}

class CoursePanel extends JPanel {
    public CoursePanel() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Course"));
        
        formPanel.add(new JLabel("Course Code:"));
        JTextField codeField = new JTextField();
        formPanel.add(codeField);
        
        formPanel.add(new JLabel("Course Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Credits:"));
        JTextField creditsField = new JTextField();
        formPanel.add(creditsField);
        
        JButton addButton = new JButton("Add Course");
        JButton resetButton = new JButton("Reset");
        formPanel.add(addButton);
        formPanel.add(resetButton);
        
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        addButton.addActionListener(e -> {
            String output = String.format("Course Added:\nCode: %s\nName: %s\nCredits: %s\n",
                    codeField.getText(), nameField.getText(), creditsField.getText());
            outputArea.append(output);
        });
        
        resetButton.addActionListener(e -> {
            codeField.setText("");
            nameField.setText("");
            creditsField.setText("");
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}

class FacultyPanel extends JPanel {
    public FacultyPanel() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Faculty"));
        
        formPanel.add(new JLabel("Faculty ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Specialization:"));
        JTextField specField = new JTextField();
        formPanel.add(specField);
        
        formPanel.add(new JLabel("Department:"));
        JTextField deptField = new JTextField();
        formPanel.add(deptField);
        
        JButton addButton = new JButton("Add Faculty");
        JButton resetButton = new JButton("Reset");
        formPanel.add(addButton);
        formPanel.add(resetButton);
        
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        addButton.addActionListener(e -> {
            String output = String.format("Faculty Added:\nID: %s\nName: %s\nSpecialization: %s\nDepartment: %s\n",
                    idField.getText(), nameField.getText(), specField.getText(), deptField.getText());
            outputArea.append(output);
        });
        
        resetButton.addActionListener(e -> {
            idField.setText("");
            nameField.setText("");
            specField.setText("");
            deptField.setText("");
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}

class DepartmentPanel extends JPanel {
    public DepartmentPanel() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Department"));
        
        formPanel.add(new JLabel("Department ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Department Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        
        JButton addButton = new JButton("Add Department");
        JButton resetButton = new JButton("Reset");
        formPanel.add(addButton);
        formPanel.add(resetButton);
        
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        addButton.addActionListener(e -> {
            String output = String.format("Department Added:\nID: %s\nName: %s\n",
                    idField.getText(), nameField.getText());
            outputArea.append(output);
        });
        
        resetButton.addActionListener(e -> {
            idField.setText("");
            nameField.setText("");
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}

class EnrollmentPanel extends JPanel {
    public EnrollmentPanel() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Enrollment"));
        
        formPanel.add(new JLabel("Enrollment ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Student ID:"));
        JTextField studentIdField = new JTextField();
        formPanel.add(studentIdField);
        
        formPanel.add(new JLabel("Course ID:"));
        JTextField courseIdField = new JTextField();
        formPanel.add(courseIdField);
        
        JButton addButton = new JButton("Enroll Student");
        JButton resetButton = new JButton("Reset");
        formPanel.add(addButton);
        formPanel.add(resetButton);
        
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        addButton.addActionListener(e -> {
            String output = String.format("Enrollment Added:\nID: %s\nStudent ID: %s\nCourse ID: %s\n",
                    idField.getText(), studentIdField.getText(), courseIdField.getText());
            outputArea.append(output);
        });
        
        resetButton.addActionListener(e -> {
            idField.setText("");
            studentIdField.setText("");
            courseIdField.setText("");
        });
        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
