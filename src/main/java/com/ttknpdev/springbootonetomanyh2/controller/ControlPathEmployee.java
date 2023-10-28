package com.ttknpdev.springbootonetomanyh2.controller;

import com.ttknpdev.springbootonetomanyh2.entities.one.Employee;
import com.ttknpdev.springbootonetomanyh2.services.one.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/employee")
public class ControlPathEmployee {
    private EmployeeService<Employee> employeeService;
    @Autowired
    public ControlPathEmployee(EmployeeService<Employee> employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping(value = "/reads")
    private ResponseEntity<?> reads() {
        return ResponseEntity
                .accepted()
                .body(employeeService.reads());
    }

    @GetMapping(value = "/read/{eid}")
    private ResponseEntity<?> read(@PathVariable String eid) {
        return ResponseEntity
                .accepted()
                .body(employeeService.read(eid));
    }
    @PostMapping(value = "/create")
    private ResponseEntity<?> create (@RequestBody Employee employee) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.create(employee));
    }
}
