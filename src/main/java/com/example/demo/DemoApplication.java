package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Bean
//    @Profile("default")
//    public CommandLineRunner seedData(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
//        return (args) -> {
//            employeeRepository.deleteAll();
//            Employee employee = new Employee();
//            employee.setName("Employee");
//            employee.setSalary(24);
//            employee.setUsername("employee");
//            employee.setPassword(passwordEncoder.encode("my-employee-password"));
//            employee.setRole("EMPLOYEE");
//            employeeRepository.save(employee);
//
//            Employee boss = new Employee();
//            boss.setName("Bossy Boss");
//            boss.setSalary(24);
//            boss.setUsername("boss");
//            boss.setPassword(passwordEncoder.encode("my-boss-password"));
//            boss.setRole("MANAGER");
//            employeeRepository.save(boss);
//        };
//    }

}
