package com.fast.repositories;

import com.fast.models.Enrollment;

import java.util.HashMap;
import java.util.Map;

public class EnrollmentRepository {
    private Map<Integer, Enrollment> enrollmentDatabase = new HashMap<>();

    public void save(Enrollment enrollment) {
        enrollmentDatabase.put(enrollment.getEnrollmentId(), enrollment);
    }

    public Enrollment findById(int enrollmentId) {
        return enrollmentDatabase.get(enrollmentId);
    }

    public void delete(int enrollmentId) {
        enrollmentDatabase.remove(enrollmentId);
    }
}