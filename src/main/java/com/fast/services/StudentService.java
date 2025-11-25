package com.fast.services;

import com.fast.models.Student;
import com.fast.repositories.StudentRepository;

import java.util.Optional;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public Optional<Student> findStudent(int id) {
        return studentRepository.findById(id);
    }

    public void removeStudent(int id) {
        studentRepository.delete(id);
    }
}