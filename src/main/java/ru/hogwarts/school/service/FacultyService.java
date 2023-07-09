package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty add(Faculty student);

    Faculty remove(Long id);

    Faculty update(Faculty student);

    Collection<Faculty> getAll();

    Faculty get(Long id);

    Collection<Faculty> getByNameOrColor(String name, String color);
    String getLongestFacultyName();
}
