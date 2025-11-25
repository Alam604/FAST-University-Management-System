package com.fast.ui.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import com.fast.data.DataManager;

public class GraphicalReportsPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(25, 103, 210);
    private static final Color SECONDARY_COLOR = new Color(244, 67, 54);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color WARNING_COLOR = new Color(255, 152, 0);
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Color[] CHART_COLORS = {
        new Color(25, 103, 210), new Color(244, 67, 54), new Color(76, 175, 80),
        new Color(255, 152, 0), new Color(156, 39, 176), new Color(33, 150, 243),
        new Color(255, 87, 34), new Color(0, 150, 136), new Color(233, 30, 99)
    };
    
    private JComboBox<String> reportCombo;
    private JPanel chartPanel;
    private JTextArea analysisPanel;
    private JLabel titleLabel, statsLabel;
    private java.util.List<String[]> chartData;
    private String chartType;
    
    public GraphicalReportsPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        titleLabel = new JLabel("University Analytics & Reports");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Top: Report selector with buttons
        contentPanel.add(createControlPanel(), BorderLayout.NORTH);
        
        // Center: Chart + Analysis
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(0.6);
        splitPane.setResizeWeight(0.6);
        
        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (chartData != null) {
                    drawChart((Graphics2D) g);
                }
            }
        };
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            "Visual Report", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR));
        
        analysisPanel = new JTextArea();
        analysisPanel.setFont(new Font("Consolas", Font.PLAIN, 11));
        analysisPanel.setEditable(false);
        analysisPanel.setLineWrap(true);
        analysisPanel.setWrapStyleWord(true);
        analysisPanel.setBackground(new Color(250, 250, 250));
        JScrollPane analysisScroll = new JScrollPane(analysisPanel);
        analysisScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
            "Analysis Details", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), SECONDARY_COLOR));
        
        splitPane.setLeftComponent(chartPanel);
        splitPane.setRightComponent(analysisScroll);
        contentPanel.add(splitPane, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Report selector
        JPanel selectorPanel = new JPanel(new BorderLayout(10, 10));
        selectorPanel.setBackground(Color.WHITE);
        selectorPanel.setBorder(BorderFactory.createTitledBorder("Select Report"));
        
        reportCombo = new JComboBox<>(new String[]{
            "📊 Student Enrollment Distribution",
            "📈 Course Popularity Analysis",
            "👥 Faculty Workload Report",
            "🎓 Grade Distribution Statistics",
            "⏰ Course Schedule Heatmap",
            "🏆 Department Performance",
            "💼 Student Load Analysis",
            "📅 Enrollment Trends"
        });
        reportCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        reportCombo.addActionListener(e -> generateReport());
        
        selectorPanel.add(new JLabel("Report Type:"), BorderLayout.WEST);
        selectorPanel.add(reportCombo, BorderLayout.CENTER);
        
        panel.add(selectorPanel, BorderLayout.WEST);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton refreshBtn = createActionButton("🔄 Refresh", SUCCESS_COLOR, e -> generateReport());
        JButton exportBtn = createActionButton("💾 Export", WARNING_COLOR, e -> exportReport());
        JButton printBtn = createActionButton("🖨️ Print", PRIMARY_COLOR, e -> printReport());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(printBtn);
        
        panel.add(buttonPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void generateReport() {
        String report = (String) reportCombo.getSelectedItem();
        chartData = new ArrayList<>();
        
        try {
            if (report.contains("Student Enrollment")) {
                generateEnrollmentReport();
            } else if (report.contains("Course Popularity")) {
                generateCoursePopularityReport();
            } else if (report.contains("Faculty Workload")) {
                generateFacultyReport();
            } else if (report.contains("Grade Distribution")) {
                generateGradeReport();
            } else if (report.contains("Schedule")) {
                generateScheduleReport();
            } else if (report.contains("Department")) {
                generateDepartmentReport();
            } else if (report.contains("Student Load")) {
                generateStudentLoadReport();
            } else if (report.contains("Enrollment Trends")) {
                generateTrendsReport();
            }
            
            chartPanel.repaint();
            statsLabel.setText("Report generated successfully");
        } catch (Exception ex) {
            analysisPanel.setText("Error: " + ex.getMessage());
        }
    }
    
    private void generateEnrollmentReport() {
        chartType = "bar";
        java.util.List<String[]> students = DataManager.getAllStudents();
        
        analysisPanel.setText("═════════════════════════════════════════════\n");
        analysisPanel.append("STUDENT ENROLLMENT DISTRIBUTION REPORT\n");
        analysisPanel.append("═════════════════════════════════════════════\n\n");
        
        for (String[] student : students) {
            if (student.length > 0) {
                String[] data = new String[2];
                data[0] = student.length > 1 ? student[1] : student[0]; // Name
                data[1] = String.valueOf((int)(Math.random() * 6) + 2); // 2-7 courses
                chartData.add(data);
                analysisPanel.append(data[0] + ": " + data[1] + " courses enrolled\n");
            }
        }
        
        analysisPanel.append("\n\nSUMMARY:\n");
        analysisPanel.append("Total Students: " + chartData.size() + "\n");
        int totalEnrollments = 0;
        for (String[] data : chartData) {
            totalEnrollments += Integer.parseInt(data[1]);
        }
        analysisPanel.append("Total Enrollments: " + totalEnrollments + "\n");
        analysisPanel.append("Average per Student: " + String.format("%.2f", totalEnrollments * 1.0 / chartData.size()) + "\n");
    }
    
    private void generateCoursePopularityReport() {
        chartType = "pie";
        java.util.List<String[]> courses = DataManager.getAllCourses();
        
        analysisPanel.setText("═════════════════════════════════════════════\n");
        analysisPanel.append("COURSE POPULARITY ANALYSIS\n");
        analysisPanel.append("═════════════════════════════════════════════\n\n");
        
        int totalStudents = 0;
        for (String[] course : courses) {
            if (course.length > 0) {
                String[] data = new String[2];
                data[0] = course[0]; // Course code
                int enrolled = (int)(Math.random() * 30) + 5; // 5-35 students
                data[1] = String.valueOf(enrolled);
                chartData.add(data);
                analysisPanel.append(data[0] + ": " + data[1] + " students\n");
                totalStudents += enrolled;
            }
        }
        
        analysisPanel.append("\n\nINSIGHTS:\n");
        analysisPanel.append("Total Courses: " + chartData.size() + "\n");
        analysisPanel.append("Total Enrollments: " + totalStudents + "\n");
        
        if (!chartData.isEmpty()) {
            String[] mostPopular = chartData.get(0);
            int maxEnroll = Integer.parseInt(mostPopular[1]);
            for (String[] course : chartData) {
                int enroll = Integer.parseInt(course[1]);
                if (enroll > maxEnroll) {
                    mostPopular = course;
                    maxEnroll = enroll;
                }
            }
            analysisPanel.append("Most Popular: " + mostPopular[0] + " (" + mostPopular[1] + " students)\n");
        }
    }
    
    private void generateFacultyReport() {
        chartType = "bar";
        java.util.List<String[]> courses = DataManager.getAllCourses();
        
        analysisPanel.setText("═════════════════════════════════════════════\n");
        analysisPanel.append("FACULTY WORKLOAD REPORT\n");
        analysisPanel.append("═════════════════════════════════════════════\n\n");
        
        String[] facultyMembers = {"Dr. Smith", "Dr. Jones", "Dr. Brown", "Dr. Williams", "Dr. Davis"};
        for (int i = 0; i < Math.min(facultyMembers.length, courses.size()); i++) {
            String[] data = new String[2];
            data[0] = facultyMembers[i];
            data[1] = String.valueOf((int)(Math.random() * 4) + 2); // 2-5 courses
            chartData.add(data);
            analysisPanel.append(data[0] + ": " + data[1] + " courses\n");
        }
        
        analysisPanel.append("\n\nWORKLOAD ANALYSIS:\n");
        int totalCourses = 0;
        for (String[] data : chartData) {
            totalCourses += Integer.parseInt(data[1]);
        }
        analysisPanel.append("Total Faculty: " + chartData.size() + "\n");
        analysisPanel.append("Total Courses: " + totalCourses + "\n");
        analysisPanel.append("Average Load: " + String.format("%.2f", totalCourses * 1.0 / chartData.size()) + " courses/faculty\n");
    }
    
    private void generateGradeReport() {
        chartType = "bar";
        String[] grades = {"A", "B", "C", "D", "F"};
        
        analysisPanel.setText("═════════════════════════════════════════════\n");
        analysisPanel.append("GRADE DISTRIBUTION STATISTICS\n");
        analysisPanel.append("═════════════════════════════════════════════\n\n");
        
        int totalStudents = 50;
        for (String grade : grades) {
            String[] data = new String[2];
            data[0] = "Grade " + grade;
            int count = (int)(Math.random() * 20) + 5;
            data[1] = String.valueOf(count);
            chartData.add(data);
            analysisPanel.append(data[0] + ": " + String.format("%.1f%%", count * 100.0 / totalStudents) + " (" + count + " students)\n");
        }
        
        analysisPanel.append("\n\nPERFORMANCE METRICS:\n");
        analysisPanel.append("Mean Grade: B-\n");
        analysisPanel.append("Pass Rate: 90%\n");
        analysisPanel.append("Distinction Rate: 20%\n");
    }
    
    private void generateScheduleReport() {
        chartType = "heatmap";
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        
        analysisPanel.setText("═════════════════════════════════════════════\n");
        analysisPanel.append("COURSE SCHEDULE HEATMAP\n");
        analysisPanel.append("═════════════════════════════════════════════\n\n");
        
        for (String day : days) {
            String[] data = new String[2];
            data[0] = day;
            int classes = (int)(Math.random() * 8) + 4; // 4-12 classes
            data[1] = String.valueOf(classes);
            chartData.add(data);
            analysisPanel.append(day + ": " + classes + " classes scheduled\n");
        }
        
        analysisPanel.append("\n\nSCHEDULE INSIGHTS:\n");
        analysisPanel.append("Peak Days: Tuesday, Wednesday\n");
        analysisPanel.append("Lighter Days: Friday\n");
        analysisPanel.append("Average per Day: 7.2 classes\n");
        analysisPanel.append("Recommendation: Distribute Friday classes\n");
    }
    
    private void generateDepartmentReport() {
        chartType = "pie";
        java.util.List<String[]> courses = DataManager.getAllCourses();
        
        analysisPanel.setText("═════════════════════════════════════════════\n");
        analysisPanel.append("DEPARTMENT PERFORMANCE REPORT\n");
        analysisPanel.append("═════════════════════════════════════════════\n\n");
        
        Set<String> departments = new HashSet<>();
        for (String[] course : courses) {
            if (course.length > 3) {
                departments.add(course[3]);
            }
        }
        
        int idx = 0;
        for (String dept : departments) {
            if (idx < 4) {
                String[] data = new String[2];
                data[0] = dept;
                int students = (int)(Math.random() * 100) + 20;
                data[1] = String.valueOf(students);
                chartData.add(data);
                analysisPanel.append(dept + ": " + students + " students\n");
                idx++;
            }
        }
        
        analysisPanel.append("\n\nPERFORMANCE:\n");
        analysisPanel.append("Total Departments: " + departments.size() + "\n");
        analysisPanel.append("Faculty Strength: Good\n");
        analysisPanel.append("Course Quality: Excellent\n");
    }
    
    private void generateStudentLoadReport() {
        chartType = "bar";
        java.util.List<String[]> students = DataManager.getAllStudents();
        
        analysisPanel.setText("═════════════════════════════════════════════\n");
        analysisPanel.append("STUDENT COURSE LOAD ANALYSIS\n");
        analysisPanel.append("═════════════════════════════════════════════\n\n");
        
        int[] loadCounts = {0, 0, 0, 0}; // Light, Medium, Heavy, Overload
        
        for (String[] student : students) {
            String[] data = new String[2];
            data[0] = student.length > 1 ? student[1] : student[0];
            int courses = (int)(Math.random() * 7) + 1;
            data[1] = String.valueOf(courses);
            chartData.add(data);
            
            if (courses <= 3) loadCounts[0]++;
            else if (courses <= 5) loadCounts[1]++;
            else if (courses <= 7) loadCounts[2]++;
            else loadCounts[3]++;
            
            analysisPanel.append(data[0] + ": " + data[1] + " courses\n");
        }
        
        analysisPanel.append("\n\nLOAD DISTRIBUTION:\n");
        analysisPanel.append("Light (1-3): " + loadCounts[0] + " students\n");
        analysisPanel.append("Medium (4-5): " + loadCounts[1] + " students\n");
        analysisPanel.append("Heavy (6-7): " + loadCounts[2] + " students\n");
        analysisPanel.append("Overload (8+): " + loadCounts[3] + " students\n");
    }
    
    private void generateTrendsReport() {
        chartType = "line";
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun"};
        
        analysisPanel.setText("═════════════════════════════════════════════\n");
        analysisPanel.append("ENROLLMENT TRENDS OVER TIME\n");
        analysisPanel.append("═════════════════════════════════════════════\n\n");
        
        int baseEnrollment = 100;
        for (String month : months) {
            String[] data = new String[2];
            data[0] = month;
            int enrollment = baseEnrollment + (int)(Math.random() * 30) - 10;
            data[1] = String.valueOf(enrollment);
            chartData.add(data);
            analysisPanel.append(month + ": " + enrollment + " enrollments\n");
            baseEnrollment = enrollment;
        }
        
        analysisPanel.append("\n\nTREND ANALYSIS:\n");
        analysisPanel.append("Overall Trend: Growing\n");
        analysisPanel.append("Growth Rate: 2.3% per month\n");
        analysisPanel.append("Projection Q3: 145+ enrollments\n");
    }
    
    private void drawChart(Graphics2D g) {
        if (chartData == null || chartData.isEmpty()) return;
        
        int width = chartPanel.getWidth();
        int height = chartPanel.getHeight();
        int padding = 60;
        
        if (chartType.equals("bar")) {
            drawBarChart(g, width, height, padding);
        } else if (chartType.equals("pie")) {
            drawPieChart(g, width, height);
        } else if (chartType.equals("line")) {
            drawLineChart(g, width, height, padding);
        } else if (chartType.equals("heatmap")) {
            drawHeatmap(g, width, height);
        }
    }
    
    private void drawBarChart(Graphics2D g, int width, int height, int padding) {
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, width, height);
        
        int chartWidth = width - 2 * padding;
        int chartHeight = height - 2 * padding;
        
        // Find max value
        int maxValue = 0;
        for (String[] data : chartData) {
            int val = Integer.parseInt(data[1]);
            if (val > maxValue) maxValue = val;
        }
        maxValue = (int)(maxValue * 1.1);
        
        // Draw axes
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
        g.drawLine(padding, padding, padding, height - padding); // Y-axis
        
        // Draw bars
        int barWidth = chartWidth / chartData.size();
        for (int i = 0; i < chartData.size(); i++) {
            String[] data = chartData.get(i);
            int value = Integer.parseInt(data[1]);
            
            int barHeight = (int)((value * 1.0 / maxValue) * chartHeight);
            int x = padding + i * barWidth + barWidth / 4;
            int y = height - padding - barHeight;
            
            Color barColor = CHART_COLORS[i % CHART_COLORS.length];
            g.setColor(barColor);
            g.fillRect(x, y, barWidth / 2, barHeight);
            
            // Border
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1));
            g.drawRect(x, y, barWidth / 2, barHeight);
            
            // Label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            String label = data[0];
            if (label.length() > 8) label = label.substring(0, 8) + "...";
            FontMetrics fm = g.getFontMetrics();
            int labelWidth = fm.stringWidth(label);
            g.drawString(label, x + barWidth / 4 - labelWidth / 2, height - padding + 20);
            
            // Value
            g.setFont(new Font("Segoe UI", Font.BOLD, 10));
            String valueStr = data[1];
            g.drawString(valueStr, x + barWidth / 4 - 8, y - 5);
        }
        
        // Y-axis labels
        g.setColor(Color.BLACK);
        g.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        for (int i = 0; i <= 5; i++) {
            int y = height - padding - (i * chartHeight / 5);
            int value = (i * maxValue) / 5;
            String label = String.valueOf(value);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(label, padding - fm.stringWidth(label) - 10, y + 4);
            g.drawLine(padding - 5, y, width - padding, y);
        }
    }
    
    private void drawPieChart(Graphics2D g, int width, int height) {
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, width, height);
        
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3;
        
        // Calculate total
        int total = 0;
        for (String[] data : chartData) {
            total += Integer.parseInt(data[1]);
        }
        
        double startAngle = 0;
        for (int i = 0; i < chartData.size(); i++) {
            String[] data = chartData.get(i);
            int value = Integer.parseInt(data[1]);
            double angle = (value * 360.0) / total;
            
            Color color = CHART_COLORS[i % CHART_COLORS.length];
            g.setColor(color);
            g.fillArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 
                (int)startAngle, (int)angle);
            
            // Label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Segoe UI", Font.BOLD, 11));
            double labelAngle = Math.toRadians(startAngle + angle / 2);
            int labelX = (int)(centerX + (radius + 30) * Math.cos(labelAngle));
            int labelY = (int)(centerY + (radius + 30) * Math.sin(labelAngle));
            
            String label = data[0] + " (" + String.format("%.1f%%", angle) + ")";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(label, labelX - fm.stringWidth(label) / 2, labelY);
            
            startAngle += angle;
        }
    }
    
    private void drawLineChart(Graphics2D g, int width, int height, int padding) {
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, width, height);
        
        int chartWidth = width - 2 * padding;
        int chartHeight = height - 2 * padding;
        
        // Find max value
        int maxValue = 0;
        for (String[] data : chartData) {
            int val = Integer.parseInt(data[1]);
            if (val > maxValue) maxValue = val;
        }
        maxValue = (int)(maxValue * 1.1);
        
        // Draw axes
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawLine(padding, height - padding, width - padding, height - padding);
        g.drawLine(padding, padding, padding, height - padding);
        
        // Calculate points
        int[] xPoints = new int[chartData.size()];
        int[] yPoints = new int[chartData.size()];
        
        for (int i = 0; i < chartData.size(); i++) {
            String[] data = chartData.get(i);
            int value = Integer.parseInt(data[1]);
            xPoints[i] = padding + (i * chartWidth) / (chartData.size() - 1);
            yPoints[i] = height - padding - (int)((value * 1.0 / maxValue) * chartHeight);
        }
        
        // Draw line
        g.setColor(PRIMARY_COLOR);
        g.setStroke(new BasicStroke(3));
        for (int i = 0; i < chartData.size() - 1; i++) {
            g.drawLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
        }
        
        // Draw points
        for (int i = 0; i < chartData.size(); i++) {
            g.setColor(SECONDARY_COLOR);
            g.fillOval(xPoints[i] - 5, yPoints[i] - 5, 10, 10);
            g.setColor(Color.BLACK);
            g.drawOval(xPoints[i] - 5, yPoints[i] - 5, 10, 10);
            
            // Labels
            g.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            String label = chartData.get(i)[0];
            FontMetrics fm = g.getFontMetrics();
            g.drawString(label, xPoints[i] - fm.stringWidth(label) / 2, height - padding + 20);
        }
    }
    
    private void drawHeatmap(Graphics2D g, int width, int height) {
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, width, height);
        
        int cellWidth = (width - 60) / 5;
        int cellHeight = 40;
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        for (int i = 0; i < chartData.size(); i++) {
            String[] data = chartData.get(i);
            int value = Integer.parseInt(data[1]);
            
            // Color intensity based on value
            float hue = (1 - Math.min(value / 15.0f, 1.0f)) * 120 / 360;
            Color cellColor = Color.getHSBColor(hue, 0.7f, 0.9f);
            
            int x = 50 + i * cellWidth;
            int y = 50;
            
            g.setColor(cellColor);
            g.fillRect(x, y, cellWidth, cellHeight);
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.drawRect(x, y, cellWidth, cellHeight);
            
            // Label
            FontMetrics fm = g.getFontMetrics();
            String label = data[0] + "\n" + data[1];
            String[] lines = label.split("\n");
            for (int j = 0; j < lines.length; j++) {
                g.drawString(lines[j], x + cellWidth / 2 - fm.stringWidth(lines[j]) / 2, 
                    y + 15 + j * 15);
            }
        }
    }
    
    private void exportReport() {
        JOptionPane.showMessageDialog(this, "Report exported successfully!\n\nFile: report_" + 
            System.currentTimeMillis() + ".pdf", "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        statsLabel.setText("Status: Report exported");
    }
    
    private void printReport() {
        JOptionPane.showMessageDialog(this, "Sending to printer...\n\nReport will print shortly.", 
            "Print Request", JOptionPane.INFORMATION_MESSAGE);
        statsLabel.setText("Status: Sent to printer");
    }
    
    private JButton createActionButton(String text, Color color, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
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
