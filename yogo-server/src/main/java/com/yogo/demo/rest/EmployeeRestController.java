package com.yogo.demo.rest;

import com.yogo.demo.entity.Employee;
import com.yogo.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    // 1. Service injection (Constructor injection)
    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService){
        employeeService = theEmployeeService;
    }

    // 2. 메소드 작성
    @GetMapping("/test")
    public String getTest() {
        return "Hello from server";
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    // Add mapping for GET /employee/{employeeId}
    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);

        if(theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        return theEmployee;
    }

    // Add mapping for POST /employees - add new employees
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee) {

        // also just in case they pass an id in JSON .. . set id to 0
        // this is to force a wave of new item ... instead of update

        theEmployee.setId(0);

        employeeService.save(theEmployee);

        return theEmployee;
    }

    // Add mapping for PUT /employees - update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee) {

        employeeService.save(theEmployee);

        return theEmployee;
    }

    // Add mapping for DELETE /employees/{emplyeeId} - delete employee
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee tempEmployee = employeeService.findById(employeeId);

        // throw exception if null
        if (tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        employeeService.deleteById(employeeId);

        return "Deleted employee id - " + employeeId;
    }

}
