package de.majudegi.network9;

import de.majudegi.db.DatabaseHandler;
import de.majudegi.models.Department;
import de.majudegi.network9.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ControllerTest {
    private MockMvc mockMvc;

    @Mock
    private DatabaseHandler mockDbHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Controller controller = new Controller();
        controller.setDbHandler(mockDbHandler);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testShowHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to our network management API! Feel free to try: <br><br>"
                        + "/department/{name} - To get information about a department and it's devices, <br>"
                        + "/departments - To get list of all departments <br>"
                        + "/city/{name} - To get department list based on city, <br>"
                        + "/cities  - To get list of all cities, where department exist, <br>"
                        + "/department/{} - To get information about a department and it's devices, <br>"
                        + "/create/department - To create a new apartment by sending a POST request, <br>"
                        + "/create/device - To create a new device by sending a POST request, <br>"
                        + "/delete/department/{name} - To delete a department via DELETE"));
    }

    @Test
    public void testShowDepartmentData() throws Exception {
        Department department = new Department(1, "IT Department", "Location");
        when(mockDbHandler.getDepartmentData("IT Department")).thenReturn(department);

        mockMvc.perform(get("/department/{name}", "IT Department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("IT Department"))
                .andExpect(jsonPath("$.location").value("Location"));
    }

    @Test
    public void testShowCities() throws Exception {
        List<String> cities = new ArrayList<>();
        cities.add("City 1");
        cities.add("City 2");
        when(mockDbHandler.getCities()).thenReturn(cities);

        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("City 1"))
                .andExpect(jsonPath("$[1]").value("City 2"));
    }

    @Test
    public void testShowDepartments() throws Exception {
        List<String> departments = new ArrayList<>();
        departments.add("Department 1");
        departments.add("Department 2");
        when(mockDbHandler.getDepartments()).thenReturn(departments);

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Department 1"))
                .andExpect(jsonPath("$[1]").value("Department 2"));
    }

    @Test
    public void testShowDepartmentsByCity() throws Exception {
        List<String> departments = new ArrayList<>();
        departments.add("Department 1");
        departments.add("Department 2");
        when(mockDbHandler.getDepartmentsByCity("City")).thenReturn(departments);

        mockMvc.perform(get("/city/{city}", "City"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Department 1"))
                .andExpect(jsonPath("$[1]").value("Department 2"));
    }

    @Test
    public void testCreateDepartment() throws Exception {
        Department department = new Department(1, "IT Department", "Location");

        mockMvc.perform(post("/create/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"IT Department\",\"location\":\"Location\"}"))
                .andExpect(status().isOk());

        verify(mockDbHandler, times(1)).createDepartment("IT Department", "Location");
    }

    @Test
    public void testCreateDevice() throws Exception {
        mockMvc.perform(post("/create/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Device\",\"department_id\":1,\"ip\":\"192.168.0.1\"}"))
                .andExpect(status().isOk());
        verify(mockDbHandler, times(1)).createDevice("Device", 1, "192.168.0.1");
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        when(mockDbHandler.deleteDepartment("IT Department")).thenReturn(true);

        mockMvc.perform(delete("/delete/department/{name}", "IT Department"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully deleted the IT Department department"));

        verify(mockDbHandler, times(1)).deleteDepartment("IT Department");
    }

    @Test
    public void testDeleteDevice() throws Exception {
        when(mockDbHandler.deleteDevice("192.168.0.1")).thenReturn(true);

        mockMvc.perform(delete("/delete/device/{ip}", "192.168.0.1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully deleted device with the ip '192.168.0.1'"));

        verify(mockDbHandler, times(1)).deleteDevice("192.168.0.1");
    }
}
