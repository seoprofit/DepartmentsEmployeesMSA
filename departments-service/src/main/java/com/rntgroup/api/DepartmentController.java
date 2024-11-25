package com.rntgroup.api;

import com.rntgroup.api.dto.DepartmentDTO;
import com.rntgroup.api.dto.EmployeeDTO;
import com.rntgroup.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/departments/name/{name}")
    DepartmentDTO getDepartmentByName(@PathVariable String name) {
        return departmentService.findByName(name);
    }

    @GetMapping("/departments/id/{id}")
    DepartmentDTO getDepartmentById(@PathVariable Long id) {
        return departmentService.findById(id);
    }

    @PutMapping("/departments/{id}")
    DepartmentDTO updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO newDepartmentDTO) {
        return departmentService.updateDepartmentById(id, newDepartmentDTO);
    }

    @GetMapping("/departments/{id}/employees")
    List<EmployeeDTO> getAllEmployeesByDepartment(@PathVariable Long id) {
        return departmentService.getAllEmployeesByDepartmentById(id);
    }

    @DeleteMapping("/departments/{id}")
    void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
    }

    @GetMapping("/departments")
    List<DepartmentDTO> getAllDepartments() {
        return departmentService.findAll();
    }

    @GetMapping("/departments/{id}/summary")
    Double getAllDepartmentEmployeesSalary(@PathVariable Long id) {
        return departmentService.getAllEmployeesSalaryAtDepartmentById(id);
    }

    @GetMapping("/departments/{id}/details")
    DepartmentDTO getInformationAboutDepartment(@PathVariable Long id) {
        return departmentService.getInformationAboutDepartment(id);
    }

    @PostMapping("/departments/add")
    @ResponseStatus(HttpStatus.CREATED)
    DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

}
