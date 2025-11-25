import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fast.models.Course;
import com.fast.services.CourseService;

class CourseServiceTest {

    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseService = new CourseService();
    }

    @Test
    void testAddCourse() {
        Course course = new Course(1, "Mathematics", 3);
        courseService.addCourse(course);
        assertEquals(course, courseService.findCourse(1));
    }

    @Test
    void testFindCourse() {
        Course course = new Course(2, "Physics", 4);
        courseService.addCourse(course);
        assertNotNull(courseService.findCourse(2));
    }

    @Test
    void testRemoveCourse() {
        Course course = new Course(3, "Chemistry", 3);
        courseService.addCourse(course);
        courseService.removeCourse(3);
        assertNull(courseService.findCourse(3));
    }
}