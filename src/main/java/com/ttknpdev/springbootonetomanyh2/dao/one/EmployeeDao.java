package com.ttknpdev.springbootonetomanyh2.dao.one;

import com.ttknpdev.springbootonetomanyh2.entities.one.Employee;
import com.ttknpdev.springbootonetomanyh2.repositories.one.EmployeeRepo;
import com.ttknpdev.springbootonetomanyh2.services.one.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeDao implements EmployeeService<Employee> {
    private EmployeeRepo employeeRepo;
    @Autowired
    public EmployeeDao(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    @Override
    public Iterable<Employee> reads() {
        return employeeRepo.findAll();
    }
    @Override
    public Employee read(String eid) {
        return employeeRepo.findById(eid).orElseThrow(()-> {
            System.out.println(eid+" didn't exist");
            // when throw the program didn't stop
            return new RuntimeException(eid+" didn't exist");
        });
    }
    @Override
    public Employee create(Employee obj) {
        // if it has validation in attribute List<Address> addresses
        // it will be better
        return employeeRepo.save(obj);
        /*
        when create have array

        // select if didn't exist it will insert not update
        Hibernate: select e.eid , e.age , e.fullname , e.salary ,
                   a.eid , a.aid , a.city , a.country , a.details
                   from employees e
                   left join addresses a (employee is main table)
                   on e.eid = a.eid
                   where e.eid = ?
        Hibernate: select a1_0.aid,a1_0.city,a1_0.country,a1_0.details,a1_0.eid from addresses a1_0 where a1_0.aid=?
        Hibernate: select a1_0.aid,a1_0.city,a1_0.country,a1_0.details,a1_0.eid from addresses a1_0 where a1_0.aid=?

        // it will insert ******
        Hibernate: insert into employees (age,fullname,salary,eid) values (?,?,?,?)
        Hibernate: insert into addresses (city,country,details,eid,aid) values (?,?,?,?,?)
        Hibernate: insert into addresses (city,country,details,eid,aid) values (?,?,?,?,?)

        // than update foreign key ******
        Hibernate: update addresses set eid=? where aid=?
        Hibernate: update addresses set eid=? where aid=?
        */
    }

    @Override
    public Employee update(String eid, Employee obj) {
        return employeeRepo.findById(eid).map(employee -> {
            // block updated
            employee.setAge(obj.getAge());
            employee.setFullname(obj.getFullname());
            employee.setSalary(obj.getSalary());
            return employeeRepo.save(employee);
        }).orElseThrow(() -> {
            return new RuntimeException(eid+" didn't exist");
        });
    }
    @Override
    public Map<String, Employee> delete(String eid) {
        Map<String,Employee> response = new HashMap<>();
        employeeRepo.findById(eid).ifPresent(employee -> {
            employeeRepo.delete(employee);
            response.put("deleted",employee);
        });
        return response;
    }



    @Override
    public Employee updateForTesting(String eid, Employee obj) {
        if (eid.equals("E001")) {
            return employeeRepo.save(obj);
        } else {
            throw new RuntimeException(eid+"didn't exist");
        }
    }
    @Override
    public Map<String, Employee> deleteForTesting(String eid) {
        Map<String,Employee> response = new HashMap<>();
        Employee e001 = new Employee();
        e001.setEid("E001");
        if (eid.equals("E001")) {
            employeeRepo.delete(e001);
            response.put("delete",e001);
        } else {
            throw new RuntimeException(eid+" didn't exist");
        }
        return response;
    }


}

