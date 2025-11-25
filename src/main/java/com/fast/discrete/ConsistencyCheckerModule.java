package com.fast.discrete;

import java.util.*;

/**
 * Consistency Checker Module
 * Detects conflicts, violations across all modules
 * - Course overlaps, missing prerequisites, student overload
 */
public class ConsistencyCheckerModule {
    
    public static class ConflictReport {
        public List<String> courseConflicts = new ArrayList<>();
        public List<String> prerequisiteViolations = new ArrayList<>();
        public List<String> schedulingConflicts = new ArrayList<>();
        public List<String> capacityViolations = new ArrayList<>();
        public List<String> logicConflicts = new ArrayList<>();
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("===== CONSISTENCY CHECK REPORT =====\n");
            
            if (courseConflicts.isEmpty()) {
                sb.append("✓ No course conflicts\n");
            } else {
                sb.append("✗ Course Conflicts:\n");
                courseConflicts.forEach(c -> sb.append("  - ").append(c).append("\n"));
            }
            
            if (prerequisiteViolations.isEmpty()) {
                sb.append("✓ No prerequisite violations\n");
            } else {
                sb.append("✗ Prerequisite Violations:\n");
                prerequisiteViolations.forEach(p -> sb.append("  - ").append(p).append("\n"));
            }
            
            if (schedulingConflicts.isEmpty()) {
                sb.append("✓ No scheduling conflicts\n");
            } else {
                sb.append("✗ Scheduling Conflicts:\n");
                schedulingConflicts.forEach(s -> sb.append("  - ").append(s).append("\n"));
            }
            
            if (capacityViolations.isEmpty()) {
                sb.append("✓ No capacity violations\n");
            } else {
                sb.append("✗ Capacity Violations:\n");
                capacityViolations.forEach(cap -> sb.append("  - ").append(cap).append("\n"));
            }
            
            if (logicConflicts.isEmpty()) {
                sb.append("✓ No logic conflicts\n");
            } else {
                sb.append("✗ Logic Conflicts:\n");
                logicConflicts.forEach(l -> sb.append("  - ").append(l).append("\n"));
            }
            
            return sb.toString();
        }
    }
    
    /**
     * Check for course overlaps in student schedule
     */
    public static List<String> checkCourseOverlaps(Map<String, Set<String>> studentCourses,
                                                    Map<String, String> courseTime) {
        List<String> conflicts = new ArrayList<>();
        
        for (String student : studentCourses.keySet()) {
            Set<String> courses = studentCourses.get(student);
            List<String> courseList = new ArrayList<>(courses);
            
            for (int i = 0; i < courseList.size(); i++) {
                for (int j = i + 1; j < courseList.size(); j++) {
                    String time1 = courseTime.getOrDefault(courseList.get(i), "");
                    String time2 = courseTime.getOrDefault(courseList.get(j), "");
                    
                    if (!time1.isEmpty() && time1.equals(time2)) {
                        conflicts.add("Student " + student + ": " + courseList.get(i) + 
                                    " and " + courseList.get(j) + " have same time");
                    }
                }
            }
        }
        
        return conflicts;
    }
    
    /**
     * Check for missing prerequisites
     */
    public static List<String> checkPrerequisiteViolations(
            Map<String, Set<String>> studentCourses,
            Map<String, Set<String>> prerequisites,
            Map<String, Set<String>> completedCourses) {
        
        List<String> violations = new ArrayList<>();
        
        for (String student : studentCourses.keySet()) {
            Set<String> enrolledCourses = studentCourses.get(student);
            Set<String> completed = completedCourses.getOrDefault(student, new HashSet<>());
            
            for (String course : enrolledCourses) {
                Set<String> requiredPrereqs = prerequisites.getOrDefault(course, new HashSet<>());
                
                for (String prereq : requiredPrereqs) {
                    if (!completed.contains(prereq)) {
                        violations.add("Student " + student + ": Missing prerequisite " + 
                                     prereq + " for " + course);
                    }
                }
            }
        }
        
        return violations;
    }
    
    /**
     * Check for student overload (too many courses)
     */
    public static List<String> checkStudentOverload(Map<String, Set<String>> studentCourses,
                                                      int maxCoursesPerTerm) {
        List<String> violations = new ArrayList<>();
        
        for (String student : studentCourses.keySet()) {
            int courseCount = studentCourses.get(student).size();
            if (courseCount > maxCoursesPerTerm) {
                violations.add("Student " + student + ": Enrolled in " + courseCount + 
                             " courses (max allowed: " + maxCoursesPerTerm + ")");
            }
        }
        
        return violations;
    }
    
    /**
     * Check capacity violations (too many students in course)
     */
    public static List<String> checkCapacityViolations(Map<String, Set<String>> studentCourses,
                                                        Map<String, Integer> courseCapacity) {
        List<String> violations = new ArrayList<>();
        Map<String, Integer> courseEnrollment = new HashMap<>();
        
        for (String student : studentCourses.keySet()) {
            for (String course : studentCourses.get(student)) {
                courseEnrollment.put(course, courseEnrollment.getOrDefault(course, 0) + 1);
            }
        }
        
        for (String course : courseEnrollment.keySet()) {
            int enrolled = courseEnrollment.get(course);
            int capacity = courseCapacity.getOrDefault(course, Integer.MAX_VALUE);
            
            if (enrolled > capacity) {
                violations.add("Course " + course + ": " + enrolled + " students enrolled " +
                             "(capacity: " + capacity + ")");
            }
        }
        
        return violations;
    }
    
    /**
     * Generate comprehensive consistency report
     */
    public static ConflictReport generateFullReport(
            Map<String, Set<String>> studentCourses,
            Map<String, Set<String>> prerequisites,
            Map<String, Set<String>> completedCourses,
            Map<String, String> courseTime,
            Map<String, Integer> courseCapacity,
            List<String> logicConflicts,
            int maxCoursesPerTerm) {
        
        ConflictReport report = new ConflictReport();
        
        report.courseConflicts.addAll(checkCourseOverlaps(studentCourses, courseTime));
        report.prerequisiteViolations.addAll(checkPrerequisiteViolations(
            studentCourses, prerequisites, completedCourses));
        report.schedulingConflicts.addAll(checkStudentOverload(studentCourses, maxCoursesPerTerm));
        report.capacityViolations.addAll(checkCapacityViolations(studentCourses, courseCapacity));
        report.logicConflicts.addAll(logicConflicts);
        
        return report;
    }
}
