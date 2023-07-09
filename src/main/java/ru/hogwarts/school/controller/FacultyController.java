package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
@Tag(name = " API для работы с факультетами")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создание факультета")
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        Faculty addedFaculty = service.add(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @PutMapping
    @Operation(summary = "Обновление факультета")
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty apdatedFaculty = service.update(faculty);
        return ResponseEntity.ok(apdatedFaculty);
    }

    @DeleteMapping("/delete{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> remove(@PathVariable Long id) {
        Faculty deletedFaculty = service.remove(id);
        return ResponseEntity.ok(deletedFaculty);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение факультета по id")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        Faculty faculty = service.get(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех факультетов")
    public ResponseEntity<Collection<Faculty>> getAll() {
        Collection<Faculty> faculties = service.getAll();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("filter")
    @Operation(summary = "получение факультета по наименованию или цвету")
    public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        Collection<Faculty> faculties = service.getByNameOrColor(name, color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("students/{facultyId}")
    @Operation(summary = "Получение студентов факультета")
    public ResponseEntity<List<Student>> getStudents(@PathVariable Long facultyId) {
        List<Student> students = service.get(facultyId).getStudents();
        return ResponseEntity.ok(students);
    }
    @GetMapping("/longest-name")
    @Operation(summary = "Получение самого длинного наименования факультета")
    public ResponseEntity<String> getLongestFacultyName() {
        String LongestFacultyName = service.getLongestFacultyName();
        return ResponseEntity.ok(LongestFacultyName);
    }

}

