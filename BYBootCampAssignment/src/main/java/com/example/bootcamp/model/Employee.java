package com.example.bootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
public class Employee {
    @PrimaryKey
    private int emp_id;
    private String emp_name;
    private String emp_city;
    private String emp_phone;

    public Employee(){}

    public Employee(int emp_id, String emp_name, String emp_city, String emp_phone) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_city = emp_city;
        this.emp_phone = emp_phone;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_city() {
        return emp_city;
    }

    public void setEmp_city(String emp_city) {
        this.emp_city = emp_city;
    }

    public String getEmp_phone() {
        return emp_phone;
    }

    public void setEmp_phone(String emp_phone) {
        this.emp_phone = emp_phone;
    }
}
