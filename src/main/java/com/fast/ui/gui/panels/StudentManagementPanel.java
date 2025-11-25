package com.fast.ui.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.fast.data.DataManager;

public class StudentManagementPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private JTable studentTable;
    private JTextField idField, nameField, emailField, majorField;
    
    public StudentManagementPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(createTopPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.WEST);
        add(createTablePanel(), BorderLayout.CENTER);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(BACKGROUND_COLOR);
        
        JLabel title = new JLabel("Student Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY_COLOR);
        
        panel.add(title);
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(250, 300));
        panel.setPreferredSize(new Dimension(250, 300));
        
        panel.add(createLabel("Add New Student"));
        panel.add(Box.createVerticalStrut(10));
        
        panel.add(createLabel("ID:"));
        idField = new JTextField();
        idField.setMaximumSize(new Dimension(220, 30));
        panel.add(idField);
        panel.add(Box.createVerticalStrut(8));
        
        panel.add(createLabel("Name:"));
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(220, 30));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(8));
        
        panel.add(createLabel("Email:"));
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(220, 30));
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(8));
        
        panel.add(createLabel("Major:"));
        majorField = new JTextField();
        majorField.setMaximumSize(new Dimension(220, 30));
        panel.add(majorField);
        panel.add(Box.createVerticalStrut(15));
        
        JButton addBtn = new JButton("Add Student");
        addBtn.setBackground(PRIMARY_COLOR);
        addBtn.setForeground(Color.WHITE);
        addBtn.setMaximumSize(new Dimension(220, 40));
        addBtn.addActionListener(e -> addStudent());
        panel.add(addBtn);
        
        panel.add(Box.createVerticalStrut(10));
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBackground(SECONDARY_COLOR);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setMaximumSize(new Dimension(220, 40));
        refreshBtn.addActionListener(e -> refreshTable());
        panel.add(refreshBtn);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        return label;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder("All Students"));
        
        String[] columns = {"ID", "Name", "Email", "Major", "Courses"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        studentTable = new JTable(model);
        studentTable.setBackground(Color.WHITE);
        studentTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        refreshTable();
        
        return panel;
    }
    
    private void addStudent() {
        if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Name are required!", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DataManager.addStudent(
            idField.getText().trim(),
            nameField.getText().trim(),
            emailField.getText().trim(),
            majorField.getText().trim()
        );
        
        JOptionPane.showMessageDialog(this, "Student added successfully!", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        majorField.setText("");
        
        refreshTable();
    }
    
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        model.setRowCount(0);
        
        for (String[] student : DataManager.getAllStudents()) {
            int courseCount = DataManager.getStudentCourses(student[0]).size();
            model.addRow(new Object[]{student[0], student[1], student[2], student[3], courseCount});
        }
    }
}
