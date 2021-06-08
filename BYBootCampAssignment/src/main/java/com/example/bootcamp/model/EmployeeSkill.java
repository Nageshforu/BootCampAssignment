package com.example.bootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
public class EmployeeSkill {
    private int emp_id;
    @PrimaryKey
    private Double java_exp;
    private double spring_exp;

    public EmployeeSkill(){}

    public EmployeeSkill(int emp_id, Double java_exp, double spring_exp) {
        this.emp_id = emp_id;
        this.java_exp = java_exp;
        this.spring_exp = spring_exp;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public Double getJava_exp() {
        return java_exp;
    }

    public void setJava_exp(Double java_exp) {
        this.java_exp = java_exp;
    }

    public double getSpring_exp() {
        return spring_exp;
    }

    public void setSpring_exp(double spring_exp) {
        this.spring_exp = spring_exp;
    }
}
