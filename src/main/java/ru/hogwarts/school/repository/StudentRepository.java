package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentByAgeBetween(Integer startAge, Integer endAge);

    @Query(value = " select count (*) from students ", nativeQuery = true)
    Integer getCount();

    @Query(value = " select avg(age) from students", nativeQuery = true)
    Float getAverageAge();

    @Query(value = " select * from students order by id desc limit 5 ", nativeQuery = true)
    List<Student> getLastFive();

    List<Student>getStudentByName(String name);


}
