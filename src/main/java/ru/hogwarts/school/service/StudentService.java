package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student add(Student student);

    Student remove(Long id);

    Student update(Student student);

    Collection<Student> getAll();

    Student get(Long id);

    Collection<Student> getByAgeBetween(Integer startAge, Integer endAge);


    Integer getStudentsCount();

    Float getStudentsAverageAge();

    List<Student> getLastFiveStudents();

    List<String> getNameByA();

    Double getStudentsAverageAgeByStream();

    void printStudents();

    void printStudentsSync();

}









