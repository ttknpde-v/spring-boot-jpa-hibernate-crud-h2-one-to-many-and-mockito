package com.ttknpdev.springbootonetomanyh2.command;

import com.ttknpdev.springbootonetomanyh2.entities.many.Address;
import com.ttknpdev.springbootonetomanyh2.entities.one.Employee;
import com.ttknpdev.springbootonetomanyh2.log.Logging;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class CommandClient {
    /*
      RestTemplate is a class within the Spring framework we will understand how to use RestTemplate for invoking REST APIs of different shapes.
      Once the above spring application is up and running,
      test all Spring rest APIs and logging results with the below Spring rest client
      (Making an HTTP GET Request to Obtain the JSON Response)
      to honestly we can use them(this class) instead Postman! ???
    * */
    private RestTemplate restTemplate = new RestTemplate();
    private static final String[] GET_READS = {
            "http://localhost:8080/api/employee/reads" ,
            "http://localhost:8080/api/address/reads",
            "http://localhost:8080/api/address/reads/{eid}"
    };
    private static final String[] POST_CREATE = {
            "http://localhost:8080/api/employee/create" ,
            "http://localhost:8080/api/address/create-second/{eid}"
    };
    private static final String[] DELETE = {
            "http://localhost:8080/api/address/delete/{eid}/employee"
    };
    public static void main(String[] args) {
        new CommandClient().deleteAddressByEmployeeId();
    }
    private void readsEmployee() {
        // Use HttpHeaders to set the Request Headers.
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // Use HttpEntity to wrap the request object.
        HttpEntity< String > entity = new HttpEntity<>("parameters", headers);
        // exchange(): executes a specified HTTP method, such as GET, POST, PUT, etc, and returns a ResponseEntity containing both the HTTP status code and the resource as an object.
        ResponseEntity< String > employees = restTemplate.exchange(GET_READS[0] , HttpMethod.GET , entity , String.class);
        // System.out.println(employees);
        Logging.commandClient.info(String.valueOf(employees));
    }

    private void readsAddress() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity< String > entity = new HttpEntity<>("parameters", headers);
        ResponseEntity< String > address = restTemplate.exchange(GET_READS[1] , HttpMethod.GET , entity , String.class);
        // System.out.println(address);
        Logging.commandClient.info(String.valueOf(address));
    }
    private void readsAddressByEmployeeId() {
        Map<String,String> params = new HashMap<>();
        params.put("eid" , "E003");// key id value 1 for taking on path read/{id}
        // Use HttpHeaders to set the Request Headers.
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // Use HttpEntity to wrap the request object.
        HttpEntity< String > entity = new HttpEntity<>("parameters", headers);
        // exchange(): executes a specified HTTP method, such as GET, POST, PUT, etc, and returns a ResponseEntity containing both the HTTP status code and the resource as an object.
        ResponseEntity< String > addresses = restTemplate.exchange(GET_READS[2] , HttpMethod.GET , entity ,String.class, params); // can add some parameter
        Logging.commandClient.info(String.valueOf(addresses));
        // student store <200 OK OK,{"status":202,"code":"accepted","info":{"code":10000,"fullname":"peter parker","weight":66.6,"height":169.9,"age":23,"status":true,"subjects":[{"fullname":"calculus 1","score":65,"grade":"C"},{"fullname":"english 1 B","score":82,"grade":"A+"}]}},[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Sat, 22 Jul 2023 17:05:40 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]>
    }
    private void deleteAddressByEmployeeId() {
        Map<String,String> params = new HashMap<>();
        params.put("eid" , "E006");// key id value 1 for taking on path read/{id}
        // Use HttpHeaders to set the Request Headers.
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // Use HttpEntity to wrap the request object.
        HttpEntity< String > entity = new HttpEntity<>("parameters", headers);
        // exchange(): executes a specified HTTP method, such as GET, POST, PUT, etc, and returns a ResponseEntity containing both the HTTP status code and the resource as an object.
        ResponseEntity< String > addresses = restTemplate.exchange(DELETE[0] , HttpMethod.DELETE , entity ,String.class, params); // can add some parameter
        Logging.commandClient.info(String.valueOf(addresses)); // result is like log4j
        // 2023-10-28T08:53:14.741+07:00  INFO 17424 --- [nio-8080-exec-2] c.t.s.dao.many.AddressDao                : E005 did exists
        // 2023-10-28T08:55:01.306+07:00  WARN 17424 --- [nio-8080-exec-4] c.t.s.dao.many.AddressDao                : E005 didn't exist
    }
    private void createEmployee() {
        // Use HttpHeaders to set the Request Headers.
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); // importance for returning like json object

        Address address1 = new Address();
        Employee employee = new Employee();

        address1.setAid("A015");
        address1.setCity("Bangkok");
        address1.setCountry("Thailand");

        List<Address> addresses = new ArrayList<>();

        employee.setFullname("Peter Parker");
        employee.setAge((short)23);
        employee.setEid("E015");
        employee.setSalary(70000.0);

        address1.setEmployee(employee);
        addresses.add(address1);

        employee.setAddresses(addresses);

        // Create the request body by wrapping
        // the object in HttpEntity
        HttpEntity< Employee > request = new HttpEntity<>(employee);
        // exchange(): executes a specified HTTP method, such as GET, POST, PUT, etc, and returns a ResponseEntity containing both the HTTP status code and the resource as an object.
        ResponseEntity< String > response = restTemplate.exchange(POST_CREATE[0] , HttpMethod.POST , request , String.class); // can add some entity
    }
}
