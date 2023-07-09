package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "API для работы со студентами")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student addedStudent = service.add(student);
        return ResponseEntity.ok(addedStudent);
    }

    @PutMapping
    @Operation(summary = "Обновление студента")
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student apdatedStudent = service.update(student);
        return ResponseEntity.ok(apdatedStudent);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Student> remove(@PathVariable Long id) {
        Student deletedStudent = service.remove(id);
        return ResponseEntity.ok(deletedStudent);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение студента по id")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        Student student = service.get(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<Collection<Student>> getAll() {
        Collection<Student> students = service.getAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/age")
    @Operation(summary = "получение студентов по возрасту")
    public ResponseEntity<Collection<Student>> getByAgeBetween(@RequestParam Integer startAge, @RequestParam Integer endAge) {
        Collection<Student> students = service.getByAgeBetween(startAge, endAge);
        return ResponseEntity.ok(students);
    }

    @GetMapping("faculty/{studentId}")
    @Operation(summary = "Получение факультета студента")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long studentId) {
        Faculty faculty = service.get(studentId).getFaculty();
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("count")
    @Operation(summary = "Получение количества студентов")
    public ResponseEntity<Integer> getStudentsCount() {
        Integer count = service.getStudentsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("age/average")
    @Operation(summary = "Получение среднего возраста студентов")
    public ResponseEntity<Float> getStudentsAverageAge() {
        Float averageAge = service.getStudentsAverageAge();
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/last")
    @Operation(summary = "получение 5-ти последних студентов")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        Collection<Student> students = service.getLastFiveStudents();
        return ResponseEntity.ok(students);
    }
    @GetMapping("/name-by-a")
    @Operation(summary = "получить имена студентов на букву А")
    public ResponseEntity<Collection<String>> getNameByA() {
        List<String> students = service.getNameByA();
        return ResponseEntity.ok(students);
    }
    @GetMapping("age/average-stream")
    @Operation(summary = "Получение среднего возраста студентов(стрим)")
    public ResponseEntity<Double> getStudentsAverageAgeByStream() {
        Double averageAge = service.getStudentsAverageAgeByStream();
        return ResponseEntity.ok(averageAge);
    }
    @GetMapping("print-students-name")
    @Operation(summary = "Вывод имён студентов")
    public ResponseEntity<Void> printStudents() {
        service.printStudents();
        return ResponseEntity.ok().build();
    }
    @GetMapping("print-students-name-sync")
    @Operation(summary = "Синхронизированный Вывод имён студентов")
    public ResponseEntity<Void> printStudentsSync() {
        service.printStudentsSync();
        return ResponseEntity.ok().build();
    }

}


