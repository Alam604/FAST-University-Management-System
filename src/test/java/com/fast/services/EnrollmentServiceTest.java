import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fast.models.Enrollment;
import com.fast.services.EnrollmentService;

class EnrollmentServiceTest {
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setUp() {
        enrollmentService = new EnrollmentService();
    }

    @Test
    void testEnrollStudent() {
        Enrollment enrollment = new Enrollment(1, 101, 202);
        assertTrue(enrollmentService.enrollStudent(enrollment));
    }

    @Test
    void testGetEnrollment() {
        Enrollment enrollment = new Enrollment(1, 101, 202);
        enrollmentService.enrollStudent(enrollment);
        Enrollment retrievedEnrollment = enrollmentService.getEnrollment(1);
        assertEquals(enrollment.getEnrollmentId(), retrievedEnrollment.getEnrollmentId());
    }

    @Test
    void testDropEnrollment() {
        Enrollment enrollment = new Enrollment(1, 101, 202);
        enrollmentService.enrollStudent(enrollment);
        assertTrue(enrollmentService.dropEnrollment(1));
        assertNull(enrollmentService.getEnrollment(1));
    }
}