package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeesController {

    private EmployeeRepository employeeRepository;

    public EmployeesController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public String getEmployees() {
        return "Super secret list of employees";
    }

    @GetMapping("/admin/employees")
    public Iterable<Employee> getAdminEmployees() {
        return employeeRepository.findAll();
    }

}