package com.fast.discrete;

import java.util.*;

/**
 * Course and Scheduling Module
 * Handles course sequences, prerequisites, and scheduling using recursive algorithms
 */
public class CourseSchedulingModule {
    private Map<String, Course> courses;
    private Map<String, Set<String>> prerequisites; // course -> list of prerequisite courses
    
    public CourseSchedulingModule() {
        this.courses = new HashMap<>();
        this.prerequisites = new HashMap<>();
    }
    
    public void addCourse(String courseCode, String courseName, int credits) {
        courses.put(courseCode, new Course(courseCode, courseName, credits));
        prerequisites.putIfAbsent(courseCode, new HashSet<>());
    }
    
    public void setPrerequisite(String course, String prerequisite) {
        prerequisites.computeIfAbsent(course, k -> new HashSet<>()).add(prerequisite);
    }
    
    /**
     * Verify multi-term prerequisite chains using strong induction
     * Returns true if all prerequisites are satisfied for a course
     */
    public boolean verifyPrerequisiteChain(String courseCode, Set<String> completedCourses) {
        if (!prerequisites.containsKey(courseCode)) {
            return true;
        }
        
        Set<String> directPrereqs = prerequisites.get(courseCode);
        
        // Base case: no prerequisites
        if (directPrereqs.isEmpty()) {
            return true;
        }
        
        // Recursive case: check all prerequisites and their prerequisites
        for (String prereq : directPrereqs) {
            if (!completedCourses.contains(prereq)) {
                return false;
            }
            // Strong induction: recursively verify prerequisites of prerequisites
            if (!verifyPrerequisiteChain(prereq, completedCourses)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Generate all valid course sequences (topological sort)
     * Uses dynamic programming and memoization
     */
    public List<String> generateValidCourseSequence(Set<String> targetCourses) {
        List<String> sequence = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Map<String, Integer> inDegree = new HashMap<>();
        
        // Calculate in-degrees
        for (String course : targetCourses) {
            inDegree.put(course, prerequisites.getOrDefault(course, new HashSet<>()).size());
        }
        
        // Queue of courses with no prerequisites
        Queue<String> queue = new LinkedList<>();
        for (String course : targetCourses) {
            if (inDegree.getOrDefault(course, 0) == 0) {
                queue.add(course);
            }
        }
        
        // Topological sort
        while (!queue.isEmpty()) {
            String current = queue.poll();
            sequence.add(current);
            visited.add(current);
            
            // Find dependent courses
            for (String course : targetCourses) {
                if (!visited.contains(course)) {
                    Set<String> prereqs = prerequisites.getOrDefault(course, new HashSet<>());
                    if (prereqs.contains(current)) {
                        inDegree.put(course, inDegree.get(course) - 1);
                        if (inDegree.get(course) == 0) {
                            queue.add(course);
                        }
                    }
                }
            }
        }
        
        return sequence;
    }
    
    public Map<String, Course> getCourses() {
        return courses;
    }
    
    public Map<String, Set<String>> getPrerequisites() {
        return prerequisites;
    }
    
    public static class Course {
        public String code;
        public String name;
        public int credits;
        
        public Course(String code, String name, int credits) {
            this.code = code;
            this.name = name;
            this.credits = credits;
        }
        
        @Override
        public String toString() {
            return code + " - " + name + " (" + credits + " credits)";
        }
    }
}
