public class ValidationUtil {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    public static boolean isValidStudentId(String studentId) {
        return studentId != null && studentId.matches("\\d+");
    }

    public static boolean isValidCourseId(String courseId) {
        return courseId != null && courseId.matches("\\d+");
    }

    public static boolean isValidDepartmentId(String departmentId) {
        return departmentId != null && departmentId.matches("\\d+");
    }
}