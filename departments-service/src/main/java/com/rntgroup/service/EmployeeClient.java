package com.rntgroup.service;

import com.rntgroup.api.dto.EmployeeDTO;
import com.rntgroup.db.entity.Department;

import com.rntgroup.db.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@FeignClient("EMPLOYEES-SERVICE")
public interface EmployeeClient {

    @GetMapping("/api/employees")
    EmployeeDTO getAllEmployees();

    @GetMapping("/api/departments/{id}/employees")
    EmployeeDTO getAllEmployeesByDepartmentById(Long id);

    @GetMapping("/api/employees/{id}")
    EmployeeDTO getEmployee(@PathVariable Long id);

    @PutMapping("/api/employees/{id}")
    Employee hireEmployee(@PathVariable Long id, LocalDate hireDate);

    @PutMapping("/api/employees/change-department/{id}")
    Employee changeEmployeeDepartment(@PathVariable Long id, @RequestBody Department department);

    @PutMapping("/api/employees/change-info/{id}")
    EmployeeDTO changeEmployeeInfo(@PathVariable Long id, @RequestBody Employee employee);

    @DeleteMapping("/api/employees/{id}")
    void deleteEmployee(@PathVariable Long id);

    @PostMapping("/api/employees/add")
    EmployeeDTO createEmployee(@RequestBody Employee employee);

    @GetMapping("/api/employees/get-all-employees-salary-by-department-id/{id}")
    Double getAllEmployeesSalaryAtDepartment(@PathVariable Long id);

    @GetMapping("/api/employees/get-count-all-employees-from-department-by-department/{id}")
    Integer getCountAllEmployeesFromDepartmentByDepartment(@PathVariable Long id);

    @GetMapping("/api/employees/check-exists-employees-from-department-by-department/{id}")
    Boolean checkExistsEmployeesFromDepartmentByDepartment(@PathVariable Long id);

    @GetMapping("/api/employees/get-all-employees-from-department-by-department-id/{id}")
    List<EmployeeDTO> getAllEmployeesFromDepartmentByDepartment(@PathVariable Long id);

}
