package com.rntgroup.service.impl;

import com.rntgroup.api.dto.DepartmentDTO;
import com.rntgroup.api.dto.EmployeeDTO;
import com.rntgroup.mappers.EmployeeMapper;
import com.rntgroup.service.DepartmentClient;
import com.rntgroup.service.DepartmentSnapshotService;
import com.rntgroup.service.EmployeeService;
import com.rntgroup.service.exception.ChiefDepartmentAlreadyExistsException;
import com.rntgroup.service.exception.EmployeeSalaryMoreThenChiefDepartmentSalaryException;
import com.rntgroup.service.exception.WrongDateOfEmployeeException;
import com.rntgroup.service.exception.IdNotFoundException;
import com.rntgroup.db.entity.Department;
import com.rntgroup.db.entity.Employee;

import com.rntgroup.db.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentClient departmentClient;
    private final EmployeeMapper employeeMapper;
    private final DepartmentSnapshotService departmentSnapshotService;

    @Override
    public List<EmployeeDTO> findAll() {
        List<Employee> allEmployeesEntities = employeeRepository.findAll();
        List<EmployeeDTO> allEmployeesDTO = new ArrayList<>(allEmployeesEntities.size());
        for (Employee allEmployeesEntity : allEmployeesEntities) {
            allEmployeesDTO.add(employeeMapper.employeeToDTO(allEmployeesEntity));
        }
        return allEmployeesDTO;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDTO findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Employee with id " + id + " doesn't exist"));
        return employeeMapper.employeeToDTO(employee);
    }

    @Override
    public EmployeeDTO createEmployee(Employee employee) {
        Employee newEmployee = new Employee();
        newEmployee.setName(employee.getName());
        newEmployee.setSurname(employee.getSurname());
        newEmployee.setPatronymic(employee.getPatronymic());
        newEmployee.setDateOfBirth(employee.getDateOfBirth());
        newEmployee.setSex(employee.getSex());
        newEmployee.setNumber(employee.getNumber());
        newEmployee.setChief(employee.isChief());
        if (employee.getDateOfHire().isAfter(employee.getDateOfBirth())) {
            newEmployee.setDateOfHire(employee.getDateOfHire());
        } else {
            throw new WrongDateOfEmployeeException("Date of Hire less then Date of birth");
        }
        if (employee.getDateOfFire().isAfter(employee.getDateOfHire())) {
            newEmployee.setDateOfFire(employee.getDateOfFire());
        } else {
            throw new WrongDateOfEmployeeException("Date of Fire less then Date of Hire");
        }

        if (employee.getDepartment() != null) {
            DepartmentDTO department = departmentSnapshotService.checkForDepartmentFromSnapshot(employee.getDepartment());
            long oldId = department.getId();
            if ((department.getChiefOfDepartment() == null) && (employee.isChief())) {
                department.setChiefOfDepartment(employee.getName());
            } else if ((department.getChiefOfDepartment() != null) && (employee.isChief())) {
                throw new ChiefDepartmentAlreadyExistsException("Department " + department.getName() + " already headed by " + department.getChiefOfDepartment() + " CEO");
            }
            newEmployee.setDepartment(department.getId());
            EmployeeDTO chief = employeeMapper.employeeToDTO(employeeRepository.findByName(department.getName()));

            if (chief != null && chief.getSalary() <= employee.getSalary()) {
                throw new EmployeeSalaryMoreThenChiefDepartmentSalaryException("Salary of new Employee more then Chief of " + department.getName() + " department");
            }
            if (chief == null || chief.getSalary() >= employee.getSalary())
                newEmployee.setSalary(employee.getSalary());
        }
        employeeRepository.save(newEmployee);
        return employeeMapper.employeeToDTO(newEmployee);

    }

    @Override
    public Double getAllEmployeesSalaryAtDepartment(Long id) {
        Double sumSalary = 0D;
        DepartmentDTO newDepartment = departmentClient.findById(id);
        if (newDepartment != null) {
            sumSalary = employeeRepository.getAllEmployeesSalaryAtDepartment(newDepartment.getId());
        }
        return sumSalary;
    }

    @Override
    public EmployeeDTO changeEmployeeDepartment(Long id, Department department) {
        Employee newEmployee = employeeRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Employee with id " + id + " doesn't exist"));
        DepartmentDTO newDepartment = departmentClient.findById(id);
        long oldId = newDepartment.getId();
        if (newEmployee != null) {
            department.setId(oldId);
            newEmployee.setDepartment(department.getId());
            employeeRepository.save(newEmployee);
        }
        return employeeMapper.employeeToDTO(newEmployee);
    }

    @Override
    public EmployeeDTO changeEmployeeInfo(Long id, Employee employee) {
        Employee oldEmployee = employeeRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Employee with id " + id + " doesn't exist"));
        Optional.ofNullable(employee.getName()).ifPresent(name -> oldEmployee.setName(employee.getName()));
        Optional.ofNullable(employee.getSurname()).ifPresent(surname -> oldEmployee.setSurname(employee.getSurname()));
        Optional.ofNullable(employee.getPatronymic()).ifPresent(patronymic -> oldEmployee.setPatronymic(employee.getPatronymic()));
        Optional.ofNullable(employee.getDateOfBirth()).ifPresent(dateofbirth -> oldEmployee.setDateOfBirth(employee.getDateOfBirth()));
        Optional.ofNullable(employee.getSex()).ifPresent(sex -> oldEmployee.setSex(employee.getSex()));
        Optional.ofNullable(employee.getNumber()).ifPresent(number -> oldEmployee.setNumber(employee.getNumber()));
        Optional.ofNullable(employee.getDateOfHire()).ifPresent(dateofhire ->
        {
            if (dateofhire.isAfter(employee.getDateOfBirth())) {
                employee.setDateOfHire(dateofhire);
            } else {
                throw new WrongDateOfEmployeeException("Date of Hire less then Date of birth");
            }
        });
        Optional.ofNullable(employee.getDateOfFire()).ifPresent(dateoffire ->
        {
            if (dateoffire.isAfter(employee.getDateOfBirth())) {
                employee.setDateOfFire(dateoffire);
            } else {
                throw new WrongDateOfEmployeeException("Date of Fire less then Date of Hire");
            }
        });
        Optional.ofNullable(employee.getDepartment()).ifPresent(department ->
        {
            DepartmentDTO newDepartment = departmentClient.findById(employee.getDepartment());
            long oldId = newDepartment.getId();
            if ((newDepartment.getChiefOfDepartment() == null) && (employee.isChief())) {
                newDepartment.setChiefOfDepartment(employee.getName());
            } else if ((newDepartment.getChiefOfDepartment() != null) && (employee.isChief())) {
                throw new ChiefDepartmentAlreadyExistsException("Department " + newDepartment.getName() + " already headed by " + newDepartment.getChiefOfDepartment() + " CEO");
            }
            oldEmployee.setDepartment(newDepartment.getId());
            EmployeeDTO chief = employeeMapper.employeeToDTO(employeeRepository.findByName(newDepartment.getName()));
            if (chief != null && chief.getSalary() <= employee.getSalary())
                throw new EmployeeSalaryMoreThenChiefDepartmentSalaryException("Salary of new Employee more then Chief of " + newDepartment.getName() + " department");
            else {
                oldEmployee.setSalary(employee.getSalary());
            }
            departmentClient.updateDepartmentById(oldId, newDepartment);
        });
        employeeRepository.save(oldEmployee);
        return employeeMapper.employeeToDTO(oldEmployee);
    }

    @Override
    public EmployeeDTO hireEmployee(Long id, LocalDate hireDate) {
        Employee newEmployee = employeeRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Employee with id " + id + " doesn't exist"));
        Optional.ofNullable(newEmployee.getDateOfHire()).ifPresent(dateofhire ->
        {
            if (dateofhire.isAfter(newEmployee.getDateOfBirth())) {
                newEmployee.setDateOfHire(dateofhire);
            } else {
                throw new WrongDateOfEmployeeException("Date of Hire less then Date of birth");
            }
            employeeRepository.save(newEmployee);
        });
        return employeeMapper.employeeToDTO(newEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployeesFromDepartmentByDepartment(Long id) {
        List<Employee> allEmployeesEntities = employeeRepository.getAllEmployeesFromDepartmentByDepartment(id);
        List<EmployeeDTO> allEmployeesDTO = new ArrayList<>(allEmployeesEntities.size());
        for (Employee allEmployeesEntity : allEmployeesEntities) {
            allEmployeesDTO.add(employeeMapper.employeeToDTO(allEmployeesEntity));
        }
        return allEmployeesDTO;
    }

    @Override
    public Integer getCountAllEmployeesFromDepartmentByDepartment(Long id) {
        return employeeRepository.getCountAllEmployeesFromDepartmentByDepartment(id);
    }

    @Override
    public Boolean CheckExistsEmployeesFromDepartmentByDepartment(Long id) {
        return employeeRepository.CheckExistsEmployeesFromDepartmentByDepartment(id);
    }

}
