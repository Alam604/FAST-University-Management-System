package com.fast.data;

import java.io.*;
import java.util.*;

/**
 * File-based Data Management System for FAST University
 */
public class DataManager {
    private static final String DATA_DIR = "data";
    private static final String STUDENTS_FILE = "data/students.txt";
    private static final String COURSES_FILE = "data/courses.txt";
    private static final String ENROLLMENTS_FILE = "data/enrollments.txt";
    private static final String PREREQUISITES_FILE = "data/prerequisites.txt";
    
    static {
        createDataDirectory();
    }
    
    private static void createDataDirectory() {
        new File(DATA_DIR).mkdirs();
    }
    
    // ========== STUDENTS MANAGEMENT ==========
    public static void addStudent(String id, String name, String email, String major) {
        try (FileWriter fw = new FileWriter(STUDENTS_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(id + "|" + name + "|" + email + "|" + major);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }
    
    public static List<String[]> getAllStudents() {
        List<String[]> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                students.add(line.split("\\|"));
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, return empty list
        } catch (IOException e) {
            System.err.println("Error reading students: " + e.getMessage());
        }
        return students;
    }
    
    // ========== COURSES MANAGEMENT ==========
    public static void addCourse(String courseCode, String courseName, int credits) {
        try (FileWriter fw = new FileWriter(COURSES_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(courseCode + "|" + courseName + "|" + credits);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error adding course: " + e.getMessage());
        }
    }
    
    public static List<String[]> getAllCourses() {
        List<String[]> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                courses.add(line.split("\\|"));
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            System.err.println("Error reading courses: " + e.getMessage());
        }
        return courses;
    }
    
    // ========== PREREQUISITES MANAGEMENT ==========
    public static void addPrerequisite(String course, String prerequisite) {
        try (FileWriter fw = new FileWriter(PREREQUISITES_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(course + "|" + prerequisite);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error adding prerequisite: " + e.getMessage());
        }
    }
    
    public static Map<String, List<String>> getAllPrerequisites() {
        Map<String, List<String>> prereqs = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PREREQUISITES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    prereqs.computeIfAbsent(parts[0], k -> new ArrayList<>()).add(parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            System.err.println("Error reading prerequisites: " + e.getMessage());
        }
        return prereqs;
    }
    
    // ========== ENROLLMENTS MANAGEMENT ==========
    public static void addEnrollment(String studentId, String courseCode) {
        try (FileWriter fw = new FileWriter(ENROLLMENTS_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(studentId + "|" + courseCode);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error adding enrollment: " + e.getMessage());
        }
    }
    
    public static List<String> getStudentCourses(String studentId) {
        List<String> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ENROLLMENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2 && parts[0].equals(studentId)) {
                    courses.add(parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (IOException e) {
            System.err.println("Error reading enrollments: " + e.getMessage());
        }
        return courses;
    }
    
    public static void initializeSampleData() {
        // Clear existing data
        clearAllData();
        
        // Add sample students
        addStudent("S001", "Ali Ahmed", "ali@fast.edu.pk", "Computer Science");
        addStudent("S002", "Fatima Khan", "fatima@fast.edu.pk", "Software Engineering");
        addStudent("S003", "Hassan Ali", "hassan@fast.edu.pk", "Computer Science");
        
        // Add sample courses
        addCourse("CS101", "Programming Fundamentals", 3);
        addCourse("CS102", "Data Structures", 3);
        addCourse("CS201", "Algorithms", 3);
        addCourse("CS301", "Database Systems", 3);
        addCourse("MATH101", "Discrete Mathematics", 3);
        
        // Add prerequisites
        addPrerequisite("CS102", "CS101");
        addPrerequisite("CS201", "CS102");
        addPrerequisite("CS301", "CS201");
        
        // Add sample enrollments
        addEnrollment("S001", "CS101");
        addEnrollment("S001", "MATH101");
        addEnrollment("S002", "CS101");
        addEnrollment("S002", "CS102");
        addEnrollment("S003", "CS201");
    }
    
    public static void clearAllData() {
        new File(STUDENTS_FILE).delete();
        new File(COURSES_FILE).delete();
        new File(ENROLLMENTS_FILE).delete();
        new File(PREREQUISITES_FILE).delete();
    }
}
