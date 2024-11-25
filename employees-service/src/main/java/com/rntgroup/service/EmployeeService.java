package com.rntgroup.service;

import com.rntgroup.db.entity.Department;
import com.rntgroup.api.dto.EmployeeDTO;
import com.rntgroup.db.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> findAll();

    void deleteEmployeeById(Long id);

    EmployeeDTO hireEmployee(Long id, LocalDate hireDate);

    List<EmployeeDTO> getAllEmployeesFromDepartmentByDepartment(Long department);

    EmployeeDTO findById(Long id);

    EmployeeDTO createEmployee(Employee employee);

    Double getAllEmployeesSalaryAtDepartment(Long id);

    EmployeeDTO changeEmployeeDepartment(Long id, Department department);

    EmployeeDTO changeEmployeeInfo(Long id, Employee employee);

    Integer getCountAllEmployeesFromDepartmentByDepartment(Long id);

    Boolean CheckExistsEmployeesFromDepartmentByDepartment(Long id);

}
