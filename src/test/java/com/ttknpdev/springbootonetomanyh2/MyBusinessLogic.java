package com.ttknpdev.springbootonetomanyh2;
import com.ttknpdev.springbootonetomanyh2.dao.many.AddressDao;
import com.ttknpdev.springbootonetomanyh2.dao.one.EmployeeDao;
import com.ttknpdev.springbootonetomanyh2.entities.many.Address;
import com.ttknpdev.springbootonetomanyh2.entities.one.Employee;
import com.ttknpdev.springbootonetomanyh2.repositories.many.AddressRepo;
import com.ttknpdev.springbootonetomanyh2.repositories.one.EmployeeRepo;
import com.ttknpdev.springbootonetomanyh2.services.many.AddressService;
import com.ttknpdev.springbootonetomanyh2.services.one.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;


// import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// concept is mocking
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MyBusinessLogic {
    @Mock
    private EmployeeRepo employeeRepo;
    @Mock
    private AddressRepo addressRepo;

    private EmployeeService<Employee> employeeService;
    private AddressService<Address> addressService;

    @BeforeEach
    public void onInitial() {
        employeeService = new EmployeeDao(employeeRepo);
        addressService = new AddressDao(addressRepo,employeeRepo);
    }

    // employee testing
    @Test
    public void createEmployee() {
        Employee employee = new Employee();
        employee.setFullname("Peter Parker");
        employee.setAge((short)23);
        employee.setEid("E001");
        employee.setSalary(30000.0);

        when(employeeRepo.save( any(Employee.class)) ).then(returnsFirstArg());
        Employee newEmployee = employeeService.create(employee);
        assertEquals("Peter Parker",newEmployee.getFullname());
        verify(employeeRepo,times(1)).save(employee); // verify (v. ตรวจสอบ) , invocations (n. การร้องขอ)
    }

    @Test
    public void readsEmployee() {
        when(employeeRepo.findAll()).thenReturn(getEmployees());
        List<Employee> employees = (List<Employee>) employeeService.reads();
        assertEquals("E001",employees.get(0).getEid());
        verify(employeeRepo,times(1)).findAll(); // verify (v. ตรวจสอบ) , invocations (n. การร้องขอ)
    }

    @Test
    public void readEmployee() {
        String eid = "E001";
        when(employeeRepo.findById(eid)).thenReturn(Optional.of(getEmployee()));
        Employee employee = employeeService.read(eid);
        assertEquals("Peter Parker",employee.getFullname());
        verify(employeeRepo,times(1)).findById(eid); // verify (v. ตรวจสอบ) , invocations (n. การร้องขอ)
    }

    @Test
    public void updateEmployee() {
        String eid = "E001";
        Employee newEmployee = new Employee();
        newEmployee.setFullname("Alex Parker");
        newEmployee.setAge((short)23);
        newEmployee.setSalary(30000.0);
        when(employeeRepo.save(newEmployee)).thenReturn(newEmployee);
        Employee employee = employeeService.updateForTesting(eid,newEmployee); // for testing
        assertEquals("Alex Parker",employee.getFullname());
        verify(employeeRepo,times(1)).save(newEmployee); // verify (v. ตรวจสอบ) , invocations (n. การร้องขอ)
    }
    // employee testing

    // address testing
    @Test
    public void createAddress() {
        String eid = "E001";
        Address newAddress = new Address();
        newAddress.setAid("A001");
        newAddress.setCity("Bangkok");
        newAddress.setCountry("Thailand");
        when(addressRepo.save( any(Address.class)) ).then(returnsFirstArg());
        Address address = addressService.createForTesting(newAddress,eid);
        assertEquals("A001",address.getAid());
        verify(addressRepo,times(1)).save(newAddress); // verify (v. ตรวจสอบ) , invocations (n. การร้องขอ)
    }
    @Test //  for testing native query
    public void readsAddressesByEmployeeId() {
        String eid = "E001";
        when(addressRepo.readsByEmployeeId(eid)).thenReturn(getAddresses());
        List<Address> addresses = (List<Address>) addressService.readsByEmployeeId(eid);
        assertEquals("E001",addresses.get(0).getEmployee().getEid());
        verify(addressRepo,times(1)).readsByEmployeeId(eid); // verify (v. ตรวจสอบ) , invocations (n. การร้องขอ)
    }
    // address testing

    private List<Employee> getEmployees() {
        Employee employee1 = new Employee();
        employee1.setFullname("Peter Parker");
        employee1.setAge((short)23);
        employee1.setEid("E001");
        employee1.setSalary(30000.0);
        return List.of(employee1,employee1);
    }

    private List<Address> getAddresses() {
        Address address1 = new Address();
        address1.setCountry("Thailand");
        address1.setCity("Chon Buri");
        address1.setAid("A001");
        address1.setDetails("123/121 ABC village");
        address1.setEmployee(getEmployee());
        return List.of(address1,address1);
    }
    private Employee getEmployee() {
        Employee employee = new Employee();
        employee.setFullname("Peter Parker");
        employee.setAge((short)23);
        employee.setEid("E001");
        employee.setSalary(30000.0);
        return employee;
    }


}
