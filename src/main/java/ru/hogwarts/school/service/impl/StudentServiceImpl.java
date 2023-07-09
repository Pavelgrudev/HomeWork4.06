package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student add(Student student) {
        logger.info("Was invoked method for add student");
        return repository.save(student);
    }

    @Override
    public Student remove(Long id) {
        logger.info("Was invoked method for remove student");
        Student student = get(id);
        repository.deleteById(id);
        return student;
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method for update student");
        Student existedStudent = get(student.getId());
        return repository.save(student);
    }

    @Override
    public Collection<Student> getAll() {
        logger.info("Was invoked method for getAll students");
        return repository.findAll();
    }

    @Override
    public Student get(Long id) {
        logger.info("Was invoked method for get student");
        Optional<Student> student = repository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            logger.error("student not found id=" + id);
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Student> getByAgeBetween(Integer startAge, Integer endAge) {
        logger.info("Was invoked method for get by age student");
        logger.debug("getByAgeBetween,startAge= " + startAge + " endAge " + endAge);
        checkAge(startAge);
        checkAge(endAge);
        return repository.findStudentByAgeBetween(startAge, endAge);
    }

    @Override
    public Integer getStudentsCount() {
        logger.info("Was invoked method for get students count");
        return repository.getCount();
    }

    @Override
    public Float getStudentsAverageAge() {
        logger.info("Was invoked method for get student average age");
        return repository.getAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return repository.getLastFive();
    }

    @Override
    public List<String> getNameByA() {
        logger.info("Was invoked method for get students name by A ");
        return repository.findAll().stream()
                .filter(s -> s.getName().startsWith("A"))
                .map(s -> s.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getStudentsAverageAgeByStream() {
        logger.info("Was invoked method for get student average age with stream");
        return repository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    @Override
    public void printStudents() {
        List<Student> students = repository.findAll();

        if (students.size() >= 6) {
            students.subList(0, 2).forEach(this::printStudentsInfo);
            printStudentsInNewThread(students.subList(2, 4));
            printStudentsInNewThread(students.subList(4, 6));
        }

    }

    @Override
    public void printStudentsSync() {
        List<Student> students = repository.findAll();

        if (students.size() >= 6) {
            students.subList(4, 6).forEach(this::printStudentsInfo);
            printStudentsInNewThreadSync(students.subList(2, 4));
            printStudentsInNewThreadSync(students.subList(0, 2));
        }


    }


    public void printStudentsInfo(Student student) {
        logger.info("student id = " + student.getId() + "name= " + student.getName());
    }

    private void printStudentsInNewThread(List<Student> students) {
        new Thread(() -> {
            students.forEach(this::printStudentsInfo);
        }).start();

    }

    public synchronized void printStudentsInfoSync(Student student) {
        logger.info("student id = " + student.getId() + "name= " + student.getName());
    }

    private void printStudentsInNewThreadSync(List<Student> students) {
        new Thread(() -> {
            students.forEach(this::printStudentsInfoSync);
        }).start();

    }

    private void checkAge(Integer age) {
        if (age <= 10 || age >= 90) {
            logger.error("Incorrect params to find students");
            throw new IncorrectArgumentException("некоректный возраст студента");
        }
    }


}
