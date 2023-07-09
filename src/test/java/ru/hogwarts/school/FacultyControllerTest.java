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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.TestConstants.*;


@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyServiceImpl facultyService;
    @InjectMocks
    private FacultyController facultyController;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createFaculty() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY);
        JSONObject createFacultyRq = new JSONObject();
        createFacultyRq.put("name", MOCK_FACULTY_NAME);
        createFacultyRq.put("color", MOCK_FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(createFacultyRq.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));

    }

    @Test
    public void getFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test
    public void getUnknownFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        MOCK_FACULTY.setName(MOCK_FACULTY_NEW_NAME);
        JSONObject updateFacultyRq = new JSONObject();
        updateFacultyRq.put("id", MOCK_FACULTY.getId());
        updateFacultyRq.put("name", MOCK_FACULTY.getName());
        updateFacultyRq.put("color", MOCK_FACULTY.getColor());
        when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(updateFacultyRq.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY.getName()))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY.getColor()));
    }

    @Test
    public void getAllFaculties() throws Exception {
        when(facultyRepository.findAll()).thenReturn(MOCK_FACULTIES);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_FACULTIES)));

    }

    @Test
    public void getFacultyByNameOrColor() throws Exception {
        when(facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(anyString(), anyString())).thenReturn(MOCK_FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter?name=" + MOCK_FACULTY_NAME + "&color=" + MOCK_FACULTY_COLOR)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_FACULTIES)));
    }

    @Test
    public void getStudentsByFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}