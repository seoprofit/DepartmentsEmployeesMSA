package com.rntgroup.api;

import com.rntgroup.api.dto.EmployeeDTO;
import com.rntgroup.db.entity.Department;
import com.rntgroup.db.entity.Employee;
import com.rntgroup.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAll();
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/employees/{id}")
    EmployeeDTO getEmployee(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PutMapping("/employees/{id}")
    EmployeeDTO hireEmployee(@PathVariable Long id, LocalDate hireDate) {
        return employeeService.hireEmployee(id, hireDate);
    }

    @GetMapping("/employees/get-all-employees-from-department-by-department-id/{id}")
    List<EmployeeDTO> getAllEmployeesFromDepartmentByDepartment(@PathVariable Long id) {
        return employeeService.getAllEmployeesFromDepartmentByDepartment(id);
    }

    @GetMapping("/employees/get-all-employees-salary-by-department-id/{id}")
    Double getAllEmployeesSalaryAtDepartment(@PathVariable Long id) {
        return employeeService.getAllEmployeesSalaryAtDepartment(id);
    }

    @GetMapping("/employees/get-count-all-employees-from-department-by-department/{id}")
    Integer getCountAllEmployeesFromDepartmentByDepartment(@PathVariable Long id) {
        return employeeService.getCountAllEmployeesFromDepartmentByDepartment(id);
    }
    @GetMapping("/api/employees/check-exists-employees-from-department-by-department/{id}")
    Integer checkExistsEmployeesFromDepartmentByDepartment(@PathVariable Long id) {
        return employeeService.getCountAllEmployeesFromDepartmentByDepartment(id);
    }

    @PutMapping("/employees/change-department/{id}")
    EmployeeDTO changeEmployeeDepartment(@PathVariable Long id, @RequestBody Department department) {
        return employeeService.changeEmployeeDepartment(id, department);
    }

    @PutMapping("/employees/change-info/{id}")
    EmployeeDTO changeEmployeeInfo(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.changeEmployeeInfo(id, employee);
    }

    @PostMapping("/employees/add")
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeDTO createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }
}
