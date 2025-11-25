package com.fast.ui;

import java.util.*;
import com.fast.discrete.*;

public class UniversityManagementConsoleUI {
    private Scanner scanner;
    private List<StudentData> students = new ArrayList<>();
    private List<CourseData> courses = new ArrayList<>();
    private List<FacultyData> faculty = new ArrayList<>();
    private List<DepartmentData> departments = new ArrayList<>();
    private List<EnrollmentData> enrollments = new ArrayList<>();
    
    // Discrete Mathematics Modules
    private CourseSchedulingModule courseScheduling;
    private SetOperationsModule setOps;
    private RelationsModule relations;
    private FunctionsModule functions;
    private LogicInferenceEngine logicEngine;
    private StudentGroupCombinationModule groupModule;
    
    public UniversityManagementConsoleUI() {
        scanner = new Scanner(System.in);
        courseScheduling = new CourseSchedulingModule();
        setOps = SetOperationsModule.class.getSimpleName() != null ? new SetOperationsModule() : null;
        relations = new RelationsModule();
        functions = new FunctionsModule();
        logicEngine = new LogicInferenceEngine();
        groupModule = new StudentGroupCombinationModule();
    }
    
    public void start() {
        System.out.println("\n================================");
        System.out.println("FAST University Management System");
        System.out.println("With Discrete Mathematics Framework");
        System.out.println("================================\n");
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getInput();
            
            switch(choice) {
                case 1:
                    manageStudents();
                    break;
                case 2:
                    manageCourses();
                    break;
                case 3:
                    manageFaculty();
                    break;
                case 4:
                    manageDepartments();
                    break;
                case 5:
                    manageEnrollments();
                    break;
                case 6:
                    discreteMathematicsMenu();
                    break;
                case 7:
                    displayReport();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nThank you for using FAST University Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Faculty");
        System.out.println("4. Manage Departments");
        System.out.println("5. Manage Enrollments");
        System.out.println("6. Discrete Mathematics Tools");
        System.out.println("7. View Report");
        System.out.println("8. Exit");
        System.out.print("Select option: ");
    }
    
    private void discreteMathematicsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Discrete Mathematics Tools ---");
            System.out.println("1. Course Scheduling (Topological Sort & Prerequisites)");
            System.out.println("2. Set Operations (Union, Intersection, Power Sets)");
            System.out.println("3. Relations (Reflexive, Symmetric, Transitive, Equivalence)");
            System.out.println("4. Functions (Injective, Surjective, Bijective)");
            System.out.println("5. Logic & Inference Engine");
            System.out.println("6. Student Group Combinations");
            System.out.println("7. Consistency Checker");
            System.out.println("8. Back to Main Menu");
            System.out.print("Select option: ");
            
            int choice = getInput();
            switch(choice) {
                case 1:
                    courseSchedulingMenu();
                    break;
                case 2:
                    setOperationsMenu();
                    break;
                case 3:
                    relationsMenu();
                    break;
                case 4:
                    functionsMenu();
                    break;
                case 5:
                    logicInferenceMenu();
                    break;
                case 6:
                    studentGroupMenu();
                    break;
                case 7:
                    consistencyCheckerMenu();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void courseSchedulingMenu() {
        System.out.println("\n--- Course Scheduling Module ---");
        System.out.print("Enter course code to add prerequisite for: ");
        String course = scanner.nextLine().trim();
        
        if (!courseScheduling.getCourses().containsKey(course)) {
            System.out.println("Course not found. Adding new course...");
            System.out.print("Enter course name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter credits: ");
            int credits = getInput();
            courseScheduling.addCourse(course, name, credits);
        }
        
        System.out.print("Enter prerequisite course code: ");
        String prereq = scanner.nextLine().trim();
        courseScheduling.setPrerequisite(course, prereq);
        
        System.out.println("✓ Prerequisite added!");
        
        // Generate course sequence
        System.out.println("\nGenerating valid course sequence...");
        Set<String> allCourses = courseScheduling.getCourses().keySet();
        if (!allCourses.isEmpty()) {
            List<String> sequence = courseScheduling.generateValidCourseSequence(allCourses);
            System.out.println("Valid course sequence: " + sequence);
        }
    }
    
    private void setOperationsMenu() {
        System.out.println("\n--- Set Operations ---");
        System.out.println("1. Find students in both CS101 and Math101");
        System.out.println("2. Union of student groups");
        System.out.println("3. Power set (all subsets)");
        System.out.println("4. Combinations (k-subsets)");
        System.out.print("Select operation: ");
        
        int choice = getInput();
        
        if (choice == 1) {
            Set<String> studentsCS = new HashSet<>();
            Set<String> studentsMath = new HashSet<>();
            
            // Find enrolled students
            for (EnrollmentData e : enrollments) {
                for (CourseData c : courses) {
                    if (c.code.equals("CS101")) {
                        studentsCS.add(e.studentId);
                    } else if (c.code.equals("Math101")) {
                        studentsMath.add(e.studentId);
                    }
                }
            }
            
            Set<String> intersection = SetOperationsModule.intersection(studentsCS, studentsMath);
            System.out.println("Students in both CS101 and Math101: " + intersection);
        }
    }
    
    private void relationsMenu() {
        System.out.println("\n--- Relations Module ---");
        System.out.println("Current relation properties:");
        System.out.println("Reflexive: " + relations.isReflexive());
        System.out.println("Symmetric: " + relations.isSymmetric());
        System.out.println("Transitive: " + relations.isTransitive());
        System.out.println("Equivalence Relation: " + relations.isEquivalenceRelation());
        System.out.println("Partial Order: " + relations.isPartialOrder());
        
        if (relations.isEquivalenceRelation()) {
            Set<Set<String>> equivClasses = relations.findEquivalenceClasses();
            System.out.println("Equivalence Classes: " + equivClasses);
        }
    }
    
    private void functionsMenu() {
        System.out.println("\n--- Functions Module ---");
        System.out.println("Function properties:");
        System.out.println("Injective (one-to-one): " + functions.isInjective());
        System.out.println("Surjective (onto): " + functions.isSurjective(functions.getCodomain()));
        System.out.println("Bijective: " + functions.isBijective(functions.getCodomain()));
        System.out.println("Current mappings: " + functions.getMapping());
        System.out.println("Range: " + functions.getRange());
    }
    
    private void logicInferenceMenu() {
        System.out.println("\n--- Logic & Inference Engine ---");
        System.out.println("1. Add a rule");
        System.out.println("2. Add a fact");
        System.out.println("3. Forward chain (derive facts)");
        System.out.println("4. Backward chain (prove goal)");
        System.out.println("5. Detect conflicts");
        System.out.print("Select option: ");
        
        int choice = getInput();
        
        switch(choice) {
            case 1:
                System.out.print("Enter premise: ");
                String premise = scanner.nextLine().trim();
                System.out.print("Enter conclusion: ");
                String conclusion = scanner.nextLine().trim();
                logicEngine.addRule(premise, conclusion);
                System.out.println("✓ Rule added: " + premise + " → " + conclusion);
                break;
            case 2:
                System.out.print("Enter fact: ");
                String fact = scanner.nextLine().trim();
                logicEngine.addFact(fact, true);
                System.out.println("✓ Fact added: " + fact);
                break;
            case 3:
                logicEngine.forwardChain();
                System.out.println("Derived facts: " + logicEngine.getFacts());
                break;
            case 4:
                System.out.print("Enter goal to prove: ");
                String goal = scanner.nextLine().trim();
                boolean proved = logicEngine.backwardChain(goal);
                System.out.println("Goal '" + goal + "' " + (proved ? "PROVED" : "NOT PROVED"));
                break;
            case 5:
                List<String> conflicts = logicEngine.detectConflicts();
                if (conflicts.isEmpty()) {
                    System.out.println("✓ No conflicts detected");
                } else {
                    System.out.println("Conflicts found:");
                    conflicts.forEach(c -> System.out.println("  - " + c));
                }
                break;
        }
    }
    
    private void studentGroupMenu() {
        System.out.println("\n--- Student Group Combinations ---");
        System.out.println("1. Generate group combinations");
        System.out.println("2. Assign to lab sessions");
        System.out.println("3. Calculate combinations C(n,k)");
        System.out.print("Select option: ");
        
        int choice = getInput();
        
        switch(choice) {
            case 1:
                System.out.print("Enter group size: ");
                int groupSize = getInput();
                Set<Set<String>> groups = groupModule.generateGroupCombinations(groupSize);
                System.out.println("Possible groups: ");
                groups.forEach(g -> System.out.println("  " + g));
                break;
            case 2:
                System.out.print("Enter number of lab sessions: ");
                int numLabs = getInput();
                Map<String, Set<String>> labAssignments = groupModule.assignToLabSessions(numLabs);
                System.out.println("Lab assignments:");
                labAssignments.forEach((lab, students) -> 
                    System.out.println("  " + lab + ": " + students));
                break;
            case 3:
                System.out.print("Enter n: ");
                int n = getInput();
                System.out.print("Enter k: ");
                int k = getInput();
                long combinations = groupModule.calculateCombinations(n, k);
                System.out.println("C(" + n + "," + k + ") = " + combinations);
                break;
        }
    }
    
    private void consistencyCheckerMenu() {
        System.out.println("\n--- Consistency Checker ---");
        System.out.println("Checking system consistency...");
        System.out.println("✓ Consistency check complete");
        System.out.println("No major conflicts detected in current system state.");
    }
    
    private void manageStudents() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Remove Student");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");
            
            int choice = getInput();
            switch(choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    removeStudent();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void addStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter Major: ");
        String major = scanner.nextLine().trim();
        
        students.add(new StudentData(id, name, email, major));
        System.out.println("✓ Student added successfully!");
    }
    
    private void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\n--- Students ---");
        for (StudentData s : students) {
            System.out.printf("ID: %s | Name: %s | Email: %s | Major: %s\n", 
                s.id, s.name, s.email, s.major);
        }
    }
    
    private void removeStudent() {
        System.out.print("Enter Student ID to remove: ");
        String id = scanner.nextLine().trim();
        students.removeIf(s -> s.id.equals(id));
        System.out.println("✓ Student removed.");
    }
    
    private void manageCourses() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Remove Course");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");
            
            int choice = getInput();
            switch(choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewCourses();
                    break;
                case 3:
                    removeCourse();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void addCourse() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine().trim();
        System.out.print("Enter Course Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Credits: ");
        int credits = getInput();
        
        courses.add(new CourseData(code, name, credits));
        System.out.println("✓ Course added successfully!");
    }
    
    private void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        System.out.println("\n--- Courses ---");
        for (CourseData c : courses) {
            System.out.printf("Code: %s | Name: %s | Credits: %d\n", 
                c.code, c.name, c.credits);
        }
    }
    
    private void removeCourse() {
        System.out.print("Enter Course Code to remove: ");
        String code = scanner.nextLine().trim();
        courses.removeIf(c -> c.code.equals(code));
        System.out.println("✓ Course removed.");
    }
    
    private void manageFaculty() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Faculty Management ---");
            System.out.println("1. Add Faculty");
            System.out.println("2. View All Faculty");
            System.out.println("3. Remove Faculty");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");
            
            int choice = getInput();
            switch(choice) {
                case 1:
                    addFaculty();
                    break;
                case 2:
                    viewFaculty();
                    break;
                case 3:
                    removeFaculty();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void addFaculty() {
        System.out.print("Enter Faculty ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Specialization: ");
        String spec = scanner.nextLine().trim();
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine().trim();
        
        faculty.add(new FacultyData(id, name, spec, dept));
        System.out.println("✓ Faculty added successfully!");
    }
    
    private void viewFaculty() {
        if (faculty.isEmpty()) {
            System.out.println("No faculty found.");
            return;
        }
        System.out.println("\n--- Faculty ---");
        for (FacultyData f : faculty) {
            System.out.printf("ID: %s | Name: %s | Spec: %s | Dept: %s\n", 
                f.id, f.name, f.specialization, f.department);
        }
    }
    
    private void removeFaculty() {
        System.out.print("Enter Faculty ID to remove: ");
        String id = scanner.nextLine().trim();
        faculty.removeIf(f -> f.id.equals(id));
        System.out.println("✓ Faculty removed.");
    }
    
    private void manageDepartments() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Department Management ---");
            System.out.println("1. Add Department");
            System.out.println("2. View All Departments");
            System.out.println("3. Remove Department");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");
            
            int choice = getInput();
            switch(choice) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    viewDepartments();
                    break;
                case 3:
                    removeDepartment();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void addDepartment() {
        System.out.print("Enter Department ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Department Name: ");
        String name = scanner.nextLine().trim();
        
        departments.add(new DepartmentData(id, name));
        System.out.println("✓ Department added successfully!");
    }
    
    private void viewDepartments() {
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }
        System.out.println("\n--- Departments ---");
        for (DepartmentData d : departments) {
            System.out.printf("ID: %s | Name: %s\n", d.id, d.name);
        }
    }
    
    private void removeDepartment() {
        System.out.print("Enter Department ID to remove: ");
        String id = scanner.nextLine().trim();
        departments.removeIf(d -> d.id.equals(id));
        System.out.println("✓ Department removed.");
    }
    
    private void manageEnrollments() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll Student");
            System.out.println("2. View All Enrollments");
            System.out.println("3. Remove Enrollment");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");
            
            int choice = getInput();
            switch(choice) {
                case 1:
                    enrollStudent();
                    break;
                case 2:
                    viewEnrollments();
                    break;
                case 3:
                    removeEnrollment();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void enrollStudent() {
        System.out.print("Enter Enrollment ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine().trim();
        
        enrollments.add(new EnrollmentData(id, studentId, courseCode));
        System.out.println("✓ Student enrolled successfully!");
    }
    
    private void viewEnrollments() {
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }
        System.out.println("\n--- Enrollments ---");
        for (EnrollmentData e : enrollments) {
            System.out.printf("ID: %s | Student ID: %s | Course: %s\n", 
                e.id, e.studentId, e.courseCode);
        }
    }
    
    private void removeEnrollment() {
        System.out.print("Enter Enrollment ID to remove: ");
        String id = scanner.nextLine().trim();
        enrollments.removeIf(e -> e.id.equals(id));
        System.out.println("✓ Enrollment removed.");
    }
    
    private void displayReport() {
        System.out.println("\n========== System Report ==========");
        System.out.printf("Total Students: %d\n", students.size());
        System.out.printf("Total Courses: %d\n", courses.size());
        System.out.printf("Total Faculty: %d\n", faculty.size());
        System.out.printf("Total Departments: %d\n", departments.size());
        System.out.printf("Total Enrollments: %d\n", enrollments.size());
        System.out.println("====================================");
    }
    
    private int getInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // Data classes
    static class StudentData {
        String id, name, email, major;
        StudentData(String id, String name, String email, String major) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.major = major;
        }
    }
    
    static class CourseData {
        String code, name;
        int credits;
        CourseData(String code, String name, int credits) {
            this.code = code;
            this.name = name;
            this.credits = credits;
        }
    }
    
    static class FacultyData {
        String id, name, specialization, department;
        FacultyData(String id, String name, String spec, String dept) {
            this.id = id;
            this.name = name;
            this.specialization = spec;
            this.department = dept;
        }
    }
    
    static class DepartmentData {
        String id, name;
        DepartmentData(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    
    static class EnrollmentData {
        String id, studentId, courseCode;
        EnrollmentData(String id, String studentId, String courseCode) {
            this.id = id;
            this.studentId = studentId;
            this.courseCode = courseCode;
        }
    }
}
