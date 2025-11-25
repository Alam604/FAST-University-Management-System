import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fast.models.Student;
import com.fast.services.StudentService;

class StudentServiceTest {
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
    }

    @Test
    void testAddStudent() {
        Student student = new Student(1, "John Doe", "john.doe@example.com");
        studentService.addStudent(student);
        assertEquals(student, studentService.findStudent(1));
    }

    @Test
    void testFindStudent() {
        Student student = new Student(2, "Jane Doe", "jane.doe@example.com");
        studentService.addStudent(student);
        assertNotNull(studentService.findStudent(2));
    }

    @Test
    void testRemoveStudent() {
        Student student = new Student(3, "Mark Smith", "mark.smith@example.com");
        studentService.addStudent(student);
        studentService.removeStudent(3);
        assertNull(studentService.findStudent(3));
    }
}