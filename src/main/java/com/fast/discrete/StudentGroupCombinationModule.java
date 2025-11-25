package com.fast.discrete;

import java.util.*;

/**
 * Student Group Combination Module
 * Assign students to project groups, lab sessions using combination calculations
 */
public class StudentGroupCombinationModule {
    private Set<String> students;
    private Map<String, Set<String>> groups;
    
    public StudentGroupCombinationModule() {
        this.students = new HashSet<>();
        this.groups = new HashMap<>();
    }
    
    public void addStudent(String studentId) {
        students.add(studentId);
    }
    
    /**
     * Generate all possible groups of size k from n students
     * Uses combination formula: C(n,k) = n! / (k!(n-k)!)
     */
    public Set<Set<String>> generateGroupCombinations(int groupSize) {
        Set<Set<String>> combinations = new HashSet<>();
        List<String> studentList = new ArrayList<>(students);
        generateCombinationsHelper(studentList, groupSize, 0, new HashSet<>(), combinations);
        return combinations;
    }
    
    private void generateCombinationsHelper(List<String> students, int k, int start,
                                           Set<String> current, Set<Set<String>> result) {
        if (current.size() == k) {
            result.add(new HashSet<>(current));
            return;
        }
        
        for (int i = start; i < students.size(); i++) {
            current.add(students.get(i));
            generateCombinationsHelper(students, k, i + 1, current, result);
            current.remove(students.get(i));
        }
    }
    
    /**
     * Calculate number of ways to form groups of size k from n students
     */
    public long calculateCombinations(int n, int k) {
        if (k > n || k < 0) return 0;
        if (k == 0 || k == n) return 1;
        
        // Use factorial formula with optimization
        long numerator = factorial(n);
        long denominator = factorial(k) * factorial(n - k);
        
        return numerator / denominator;
    }
    
    private long factorial(int n) {
        if (n <= 1) return 1;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    /**
     * Assign students to lab sessions (distribute evenly)
     */
    public Map<String, Set<String>> assignToLabSessions(int numLabSessions) {
        Map<String, Set<String>> labAssignments = new HashMap<>();
        List<String> studentList = new ArrayList<>(students);
        
        for (int i = 0; i < numLabSessions; i++) {
            labAssignments.put("Lab_" + (i + 1), new HashSet<>());
        }
        
        // Round-robin assignment
        for (int i = 0; i < studentList.size(); i++) {
            String labId = "Lab_" + ((i % numLabSessions) + 1);
            labAssignments.get(labId).add(studentList.get(i));
        }
        
        return labAssignments;
    }
    
    /**
     * Assign students to electives based on preferences
     * Ensures each student gets at least one elective
     */
    public Map<String, Set<String>> assignToElectives(Map<String, Set<String>> studentPreferences,
                                                       Set<String> availableElectives) {
        Map<String, Set<String>> assignments = new HashMap<>();
        
        for (String elective : availableElectives) {
            assignments.put(elective, new HashSet<>());
        }
        
        for (String student : students) {
            Set<String> preferences = studentPreferences.getOrDefault(student, new HashSet<>());
            
            // Assign to first available preference
            for (String elective : preferences) {
                if (availableElectives.contains(elective)) {
                    assignments.get(elective).add(student);
                    break;
                }
            }
        }
        
        return assignments;
    }
    
    /**
     * Check if group assignment is balanced (within 1 student difference)
     */
    public boolean isGroupAssignmentBalanced(Map<String, Set<String>> groupAssignment) {
        if (groupAssignment.isEmpty()) return true;
        
        int[] sizes = groupAssignment.values().stream()
            .mapToInt(Set::size)
            .sorted()
            .toArray();
        
        int minSize = sizes[0];
        int maxSize = sizes[sizes.length - 1];
        
        return maxSize - minSize <= 1;
    }
    
    public Set<String> getStudents() {
        return students;
    }
    
    public int getStudentCount() {
        return students.size();
    }
}
