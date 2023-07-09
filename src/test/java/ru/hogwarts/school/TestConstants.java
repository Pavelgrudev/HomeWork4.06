package ru.hogwarts.school;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collections;
import java.util.List;

public class TestConstants {
    static final Long MOCK_FACULTY_ID = 1L;
    static final String MOCK_FACULTY_NAME = "Faculty name";
    static final String MOCK_FACULTY_NEW_NAME = "Faculty new name";
    static final String MOCK_FACULTY_COLOR = "Black";
    public static final Faculty MOCK_FACULTY = new Faculty(
            MOCK_FACULTY_ID,
            MOCK_FACULTY_NAME,
            MOCK_FACULTY_COLOR
    );
    static final Long MOCK_STUDENT_ID = 1L;
    static final String MOCK_STUDENT_NAME = "Student name";
    static final String MOCK_STUDENT_NEW_NAME = "Student new name";
    static final Integer MOCK_STUDENT_AGE = 20;
    public static final Student MOCK_STUDENT = new Student(
            MOCK_STUDENT_ID,
            MOCK_STUDENT_NAME,
            MOCK_STUDENT_AGE,
            MOCK_FACULTY);


    public static final List<Student> MOCK_STUDENTS = Collections.singletonList(MOCK_STUDENT);

    public static final List<Faculty> MOCK_FACULTIES = Collections.singletonList(MOCK_FACULTY);

}
