package com.fast.services;

import com.fast.models.Course;
import com.fast.repositories.CourseRepository;

import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public Course findCourse(int courseId) {
        return courseRepository.findById(courseId);
    }

    public void removeCourse(int courseId) {
        courseRepository.delete(courseId);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}