package com.example.bootcamp.Repository;

import com.example.bootcamp.model.EmployeeSkill;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeSkillRepository extends CassandraRepository<EmployeeSkill, Double> {
    @Query("select * from employee_skill where java_exp >= :java_exp ALLOW FILTERING;")
    List<EmployeeSkill> findAllWithGreaterJavaExpr(
            @Param("java_exp") Double java_exp);
}
