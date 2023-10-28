package com.ttknpdev.springbootonetomanyh2.entities.many;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttknpdev.springbootonetomanyh2.entities.one.Employee;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
// address can't have many employees
// once address can have an employee
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    private String aid;
    /*
    -- the @JoinColumn annotation to specify the foreign key column (eid).
    -- If you don’t provide the JoinColumn name, the name will be set automatically.
    -- @JsonIgnore is used to ignore the logical property used in serialization and deserialization.
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eid")
    @JsonIgnore
    private Employee employee; // for create
    private String city;
    private String country;
    private String details;

    public Address() {
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    // for create
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Address{" +
                "aid='" + aid + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
