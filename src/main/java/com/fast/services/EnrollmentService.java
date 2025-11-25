package com.fast.services;

import com.fast.models.Enrollment;
import com.fast.repositories.EnrollmentRepository;

public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment enrollStudent(int studentId, int courseId) {
        Enrollment enrollment = new Enrollment(studentId, courseId);
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment getEnrollment(int enrollmentId) {
        return enrollmentRepository.findById(enrollmentId);
    }

    public void dropEnrollment(int enrollmentId) {
        enrollmentRepository.delete(enrollmentId);
    }
}