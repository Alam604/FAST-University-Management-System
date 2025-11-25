package com.fast.repositories;

import com.fast.models.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {
    private List<Student> students = new ArrayList<>();

    public void save(Student student) {
        students.add(student);
    }

    public Optional<Student> findById(int id) {
        return students.stream().filter(student -> student.getId() == id).findFirst();
    }

    public void delete(int id) {
        students.removeIf(student -> student.getId() == id);
    }
}