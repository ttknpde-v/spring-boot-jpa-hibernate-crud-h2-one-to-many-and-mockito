package com.ttknpdev.springbootonetomanyh2.entities.one;

import com.ttknpdev.springbootonetomanyh2.entities.many.Address;
import jakarta.persistence.*;

import java.util.List;
// employee can have many addresses
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    private String eid;
    private String fullname;
    private Short age;
    private Double salary;
    // Mapping to the other table
    @OneToMany(cascade = CascadeType.ALL) // ,targetEntity = Address.class ,mappedBy = "ob"
    @JoinColumn(name = "eid")
    private List<Address> addresses;
    /*
    {
      "eid":"E001",
      "fullname":"Peter Parker",
      "age":23,
      "salary":25000.0,
      "addresses":[ // json array
         {
            "aid":"A001",
            "city":"Bangkok",
            "country":"Thailand",
            "details":"123 soi.1/3 ABC Village"
         },
         {
            "aid":"A002",
            "city":"Bangkok",
            "country":"Thailand",
            "details":"122 soi.1/3 ABC Village"
         }
      ]
   }
    */
    public Employee() {

    }
    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eid='" + eid + '\'' +
                ", fullname='" + fullname + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
