package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeesController.class)
@Import(SecurityConfig.class)
public class EmployeesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void testWithUser() throws Exception {
        RequestBuilder request = get("/employees").with(user("user").roles("EMPLOYEE"));
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testGetAdminEmployees_withUserRole() throws Exception {
        RequestBuilder request = get("/admin/employees").with(user("user").roles("EMPLOYEE"));
        mockMvc.perform(request).andExpect(status().isForbidden());
    }
    @Test
    public void testGetAdminEmployees_withNoUserRole() throws Exception {
        RequestBuilder request = get("/admin/employees");
        mockMvc.perform(request).andExpect(status().isUnauthorized());
    }
    @Test
    public void testGetAdminEmployees_withAdminRole() throws Exception {
        RequestBuilder request = get("/admin/employees").with(user("user").roles("ADMIN"));
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testGetAdminEmployees_withManagerRole() throws Exception {
        RequestBuilder request = get("/admin/employees").with(user("user").roles("MANAGER"));
        mockMvc.perform(request).andExpect(status().isOk());
    }

}