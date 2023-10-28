package com.ttknpdev.springbootonetomanyh2.repositories.one;

import com.ttknpdev.springbootonetomanyh2.entities.one.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepo extends JpaRepository<Employee,String> { }
