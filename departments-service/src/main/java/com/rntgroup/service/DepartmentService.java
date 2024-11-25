package com.rntgroup.service;

import com.rntgroup.api.dto.DepartmentDTO;
import com.rntgroup.api.dto.EmployeeDTO;

import java.util.List;


public interface DepartmentService {

    DepartmentDTO findByName(String name);

    DepartmentDTO findById(Long id);

    List<DepartmentDTO> findAll();

    DepartmentDTO updateDepartmentById(Long id, DepartmentDTO departmentDTO);

    DepartmentDTO getInformationAboutDepartment(Long id);

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    void deleteDepartmentById(Long id);

    List<EmployeeDTO> getAllEmployeesByDepartmentById(Long id);

    Double getAllEmployeesSalaryAtDepartmentById(Long id);

    Double getAllEmployeesSalaryAtDepartmentByName(String name);

    List<String> getAllDepartmentsNames();
}
