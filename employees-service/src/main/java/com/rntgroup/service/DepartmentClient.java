package com.rntgroup.service;

import com.rntgroup.api.dto.DepartmentDTO;
import com.rntgroup.api.dto.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient("DEPARTMENTS-SERVICE")
public interface DepartmentClient {

    @GetMapping("/api/departments/id/{id}")
    DepartmentDTO findById(@PathVariable Long id);

    @GetMapping("/api/departments/name/{name}")
    ResponseEntity<DepartmentDTO> getDepartmentByName(@PathVariable String name);

    @GetMapping("/api/departments/{id}/employees")
    List<EmployeeDTO> getAllEmployeesByDepartmentId(@PathVariable Long id);

    @PutMapping("/api/departments/{id}")
    DepartmentDTO updateDepartmentById(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO);

    @DeleteMapping("/api/departments/{id}")
    void deleteDepartment(@PathVariable Long id);

    @GetMapping("/api/departments")
    List<DepartmentDTO> getAllDepartments();

    @GetMapping("/api/departments/{id}/summary")
    Double getAllDepartmentEmployeesSalary(@PathVariable Long id);

    @GetMapping("/api/departments/{id}/details")
    DepartmentDTO getInformationAboutDepartment(@PathVariable Long id);

    @PostMapping("/api/departments/add")
    DepartmentDTO createDepartment(@RequestBody DepartmentDTO newDepartment);

}
