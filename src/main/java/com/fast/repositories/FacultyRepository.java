package com.fast.repositories;

import com.fast.models.Faculty;
import java.util.HashMap;
import java.util.Map;

public class FacultyRepository {
    private Map<Integer, Faculty> facultyDatabase = new HashMap<>();

    public void save(Faculty faculty) {
        facultyDatabase.put(faculty.getId(), faculty);
    }

    public Faculty findById(int id) {
        return facultyDatabase.get(id);
    }

    public void delete(int id) {
        facultyDatabase.remove(id);
    }
}