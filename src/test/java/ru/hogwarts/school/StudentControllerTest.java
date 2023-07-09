package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.TestConstants.*;


@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentServiceImpl studentService;
    @InjectMocks
    private StudentController studentController;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createStudent() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT);
        JSONObject createStudentRq = new JSONObject();
        createStudentRq.put("name", MOCK_STUDENT_NAME);
        createStudentRq.put("age", MOCK_STUDENT_AGE);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(createStudentRq.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));

    }

    @Test
    public void getStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test
    public void getUnknownStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));
        MOCK_STUDENT.setName(MOCK_STUDENT_NEW_NAME);
        JSONObject updateStudentRq = new JSONObject();
        updateStudentRq.put("id", MOCK_STUDENT.getId());
        updateStudentRq.put("name", MOCK_STUDENT.getName());
        updateStudentRq.put("age", MOCK_STUDENT.getAge());
        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(updateStudentRq.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT.getName()))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT.getAge()));
    }

    @Test
    public void getAllStudents() throws Exception {
        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS)));

    }

    @Test
    public void getStudentsBetweenAge() throws Exception {
        when(studentRepository.findStudentByAgeBetween(anyInt(), anyInt())).thenReturn(MOCK_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?startAge=20&endAge=30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
    }

    @Test
    public void getFacultyByStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student//faculty/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

