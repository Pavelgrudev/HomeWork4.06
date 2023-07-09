package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository repository;
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        return repository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        logger.info("Was invoked method for remove faculty");
        Faculty Faculty = get(id);
        repository.deleteById(id);
        return Faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        Faculty existedFaculty = get(faculty.getId());
        return repository.save(faculty);
    }

    @Override
    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for getAll faculties");
        return repository.findAll();
    }

    @Override
    public Faculty get(Long id) {
        logger.info("Was invoked method for get faculty");
        Optional<Faculty> faculty = repository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            logger.error("Faculty not found, id =" + id);
            throw new EntityNotFoundException();
        }
    }


    @Override
    public Collection<Faculty> getByNameOrColor(String name, String color) {
        logger.info("Was invoked method for get faculty by name or color");
        if (!StringUtils.hasText(name) && !StringUtils.hasText(color)) {
            logger.error("Incorrect params to find faculties");
            throw new IncorrectArgumentException("Неккоректное наименование или цвет факультета");
        }
        return repository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public String getLongestFacultyName() {
        logger.info("Was invoked method for get faculty longest  name");
        return repository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }
}
