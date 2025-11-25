package com.fast.services;

import com.fast.models.Faculty;
import com.fast.repositories.FacultyRepository;

import java.util.Optional;

public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public void addFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    public Optional<Faculty> findFaculty(int id) {
        return facultyRepository.findById(id);
    }

    public void removeFaculty(int id) {
        facultyRepository.delete(id);
    }
}