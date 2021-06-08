package com.example.bootcamp.Repository;

import com.example.bootcamp.model.Employee;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends CassandraRepository<Employee, Integer> {
    @Query( "select * from employee where emp_id in :ids" )
    List<Employee> findByEmployeeIds(@Param("ids") List<Integer> emp);
}
