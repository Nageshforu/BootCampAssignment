package com.example.bootcamp.Controller;


import com.example.bootcamp.Configuration.KafkaConfig;
import com.example.bootcamp.Repository.EmployeeRepository;
import com.example.bootcamp.Repository.EmployeeSkillRepository;
import com.example.bootcamp.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    public EmployeeController(KafkaConfig kafkaConfig) {

        this.kafkaConfig = kafkaConfig;
    }
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeSkillRepository employeeSkillRepository;
    private final KafkaConfig kafkaConfig;

    @PostMapping("/createEmployee")
    public EmployeeInformation createEmployee(@RequestBody EmployeeInformation employeeInformation) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Employee empployee = new Employee(employeeInformation.getEmp_id(),
                employeeInformation.getEmp_name(), employeeInformation.getEmp_city(),
                employeeInformation.getEmp_phone());
        EmployeeSkill emp_skill = new EmployeeSkill(employeeInformation.getEmp_id(),
                employeeInformation.getJava_exp(), employeeInformation.getSpring_exp());
        boolean empIsPresent =  employeeRepository.findById(employeeInformation.getEmp_id()).isPresent();
        if(empIsPresent){
            employeeInformation.setStatus("Already Exists");
        } else {
            employeeInformation.setStatus("Created");
        }
        employeeRepository.save(empployee);
        employeeSkillRepository.save(emp_skill);
        String kafkaMessage = mapper.writeValueAsString(employeeInformation);
        this.kafkaConfig.writeMessage(kafkaMessage);

        return employeeInformation;
    }

    @GetMapping("/findEmpSkillset")
    public ResponseEntity<List<EmployeeInfo>> findEmployeeSkillset(@RequestBody FindEmpSkillsetInput findEmpSkillsetInput){
        List<EmployeeSkill> employee_skill =  employeeSkillRepository.findAllWithGreaterJavaExpr(findEmpSkillsetInput.getJava_exp());
        if(employee_skill.isEmpty()){
            return ResponseEntity.ok().body(new ArrayList<EmployeeInfo>());
        }
        List<EmployeeInfo> employeeInfos = new ArrayList<EmployeeInfo>();
        List<Integer> employeeIds = employee_skill.stream().map(EmployeeSkill::getEmp_id).collect(Collectors.toList());
        List<Employee> employeeList = employeeRepository.findByEmployeeIds(employeeIds);
        List<EmployeeInfo> employeeInfoList = new ArrayList<>();
        for (EmployeeSkill employeeSkill: employee_skill) {
            Employee employee = employeeRepository.findById(employeeSkill.getEmp_id()).orElse(
                    null
            );
            EmployeeInfo employeeInfo = null;
            if(null != employee) {
                employeeInfo =  new EmployeeInfo(employee.getEmp_id(), employee.getEmp_name(), employee.getEmp_city(),employee.getEmp_phone(),
                        employeeSkill.getJava_exp(), employeeSkill.getSpring_exp());
                employeeInfoList.add(employeeInfo);
            }
        }

        return ResponseEntity.ok().body(employeeInfoList);
    }

}


