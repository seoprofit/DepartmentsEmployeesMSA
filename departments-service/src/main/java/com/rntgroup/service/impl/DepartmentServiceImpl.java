package com.rntgroup.service.impl;


import com.rntgroup.api.dto.DepartmentInfoDTO;
import com.rntgroup.config.DepartmentServicePropertySource;
import com.rntgroup.db.entity.DepartmentManipulation;
import com.rntgroup.api.dto.EmployeeDTO;
import com.rntgroup.db.entity.Department;
import com.rntgroup.api.dto.DepartmentDTO;
import com.rntgroup.db.entity.DepartmentSalary;
import com.rntgroup.mappers.DepartmentMapper;
import com.rntgroup.db.repository.DepartmentRepository;


import com.rntgroup.service.KafkaDepartmentProducerService;
import com.rntgroup.service.DepartmentInfoService;
import com.rntgroup.service.DepartmentFundService;
import com.rntgroup.service.DepartmentService;
import com.rntgroup.service.EmployeeClient;
import com.rntgroup.service.exception.DepartmentAlreadyExistsException;
import com.rntgroup.service.exception.DepartmentHasNotHireEmployees;
import com.rntgroup.service.exception.IdNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentServicePropertySource departmentServicePropertySource;
    private final DepartmentRepository departmentRepository;
    private final DepartmentFundService departmentFundService;
    private final DepartmentInfoService departmentInfoService;
    private final EmployeeClient employeeClient;
    private final DepartmentMapper departmentMapper;
    private final KafkaDepartmentProducerService kafkaDepartmentProducerService;
    private final String DEPARTMENT_MANIPULATION_OPERATION_READ = "READ";
    private final String DEPARTMENT_MANIPULATION_OPERATION_DELETE = "DELETE";
    private final String DEPARTMENT_MANIPULATION_OPERATION_UPDATE = "UPDATE";
    private final String DEPARTMENT_MANIPULATION_OPERATION_CREATE = "CREATE";

    @Override
    public DepartmentDTO findByName(String name) {
        Optional<Department> byName = departmentRepository.findByName(name);
        return byName.map(departmentMapper::departmentToDTO).orElse(null);
    }

    @Override
    public DepartmentDTO findById(Long id) {
        return departmentMapper.departmentToDTO(departmentRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Department with id " + id + " doesn't exist")));
    }

    @Override
    public List<DepartmentDTO> findAll() {
        List<Department> allDepartmentsEntities = departmentRepository.findAll();
        List<DepartmentDTO> allDepartmentsDTO = new ArrayList<>(allDepartmentsEntities.size());
        for (Department allDepartmentsEntity : allDepartmentsEntities) {
            allDepartmentsDTO.add(departmentMapper.departmentToDTO(allDepartmentsEntity));
        }
        return allDepartmentsDTO;
    }

    @Override
    public DepartmentDTO updateDepartmentById(Long id, DepartmentDTO departmentDTO) {
        Department departmentFromDB = departmentRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Department with id " + id + " doesn't exist"));
        DepartmentSalary departmentSalary = departmentFundService.findByName(departmentFromDB.getName());
        String parentDepartmentName = departmentDTO.getParentDepartment();
        if (departmentDTO.getParentDepartment() != null) {
            Department parentDepartmentFromDB = departmentRepository.findByName(parentDepartmentName).orElseThrow(() -> new IdNotFoundException("Parent Department " + parentDepartmentName + " doesn't exist"));
            departmentFromDB.setParentDepartment(parentDepartmentFromDB.getName());
        }
        if ((departmentFromDB.getName() != null) && (!departmentFromDB.getName().equals(departmentDTO.getName()))) {
            departmentFromDB.setName(departmentDTO.getName());
            departmentFromDB.setParentDepartment(departmentDTO.getParentDepartment());
            departmentDTO = departmentMapper.departmentToDTO(departmentFromDB);
            saveInfoAboutDepartmentManipulations(departmentFromDB, DEPARTMENT_MANIPULATION_OPERATION_UPDATE);
            departmentFundService.deleteDepartmentFundById(departmentSalary.getId());
            departmentRepository.save(departmentFromDB);
        }
        return departmentDTO;
    }

    @Override
    @CircuitBreaker(name = "EMPLOYEES-SERVICE", fallbackMethod = "getDefaultSalaryFund")
    public Double getAllEmployeesSalaryAtDepartmentById(Long id) {
        return employeeClient.getAllEmployeesSalaryAtDepartment(id);
    }

    @Override
    public Double getAllEmployeesSalaryAtDepartmentByName(String name) {
        Optional<Department> department = departmentRepository.findByName(name);
        if (department.isPresent())
            return employeeClient.getAllEmployeesSalaryAtDepartment(department.get().getId());
        return 0.0;
    }


    @Override
    public List<String> getAllDepartmentsNames() {
        return departmentRepository.getDepartmentsNames();
    }

    public Double getDefaultSalaryFund(Exception e) {
        return departmentServicePropertySource.getDefaultValueSalaryFund();
    }

    @Override
    public DepartmentDTO getInformationAboutDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Department with id " + id + " doesn't exist"));
        DepartmentDTO departmentDTO = departmentMapper.departmentToDTO(department);
        int allEmployeesCountFromDepartment = employeeClient.getCountAllEmployeesFromDepartmentByDepartment(id);
        departmentDTO.setCountOfEmployees(allEmployeesCountFromDepartment);
        saveInfoAboutDepartmentManipulations(department, DEPARTMENT_MANIPULATION_OPERATION_READ);
        return departmentDTO;
    }

    public void saveInfoAboutDepartmentManipulations(Department department, String databaseTableOperations) {
        DepartmentManipulation departmentManipulation = new DepartmentManipulation();
        departmentManipulation.setDepartmentsName(department.getName());
        departmentManipulation.setDate(new Timestamp(Instant.now().toEpochMilli()));
        departmentManipulation.setOperation(databaseTableOperations);
        departmentInfoService.saveDepartmentInfo(departmentManipulation);
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentEntity) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        Department parentDepartment = new Department();
        Optional<Department> department = departmentRepository.findByName(departmentEntity.getName());
        if (departmentEntity.getParentDepartment() != null) {
            parentDepartment = departmentRepository.findByName(departmentEntity.getParentDepartment()).orElseThrow(() -> new IdNotFoundException("Parent Department " + departmentEntity.getParentDepartment() + " doesn't exist"));
        }
        if (department.isPresent()) {
            throw new DepartmentAlreadyExistsException("Sorry, Department " + departmentEntity.getName() + " already exists");
        }
        if (parentDepartment.getName() == null) {
            throw new DepartmentAlreadyExistsException("Sorry, Parent department doesn't exists");
        } else {
            departmentDTO.setParentDepartment(departmentEntity.getParentDepartment());
            departmentDTO.setName(departmentEntity.getName());
            saveInfoAboutDepartmentManipulations(departmentMapper.DTOToEntity(departmentEntity), DEPARTMENT_MANIPULATION_OPERATION_CREATE);
            departmentRepository.save(departmentMapper.departmentToEntity(departmentEntity));
            Optional<Department> departmentDTOGetId = departmentRepository.findByName(departmentDTO.getName());
            if (departmentDTOGetId.isPresent()) {
                DepartmentInfoDTO departmentInfoDTO = new DepartmentInfoDTO(departmentDTOGetId.get().getId(), departmentDTO.getName(), DEPARTMENT_MANIPULATION_OPERATION_CREATE);
                kafkaDepartmentProducerService.sendMessage(departmentInfoDTO);
            }
        }
        return departmentDTO;
    }

    @Override
    public void deleteDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Department with id " + id + " doesn't exist"));
        Boolean checkEmployeesAtDepartment = employeeClient.checkExistsEmployeesFromDepartmentByDepartment(id);
        if (checkEmployeesAtDepartment) {
            if (departmentFundService.findByName(department.getName()) != null) {
                DepartmentSalary departmentSalary = departmentFundService.findByName(department.getName());
                departmentFundService.deleteDepartmentFundById(departmentSalary.getId());
            }
            saveInfoAboutDepartmentManipulations(department, DEPARTMENT_MANIPULATION_OPERATION_DELETE);
            departmentRepository.deleteById(id);
            DepartmentInfoDTO departmentInfoDTO = new DepartmentInfoDTO(id, department.getName(), DEPARTMENT_MANIPULATION_OPERATION_DELETE);
            kafkaDepartmentProducerService.sendMessage(departmentInfoDTO);
        } else
            throw new DepartmentHasNotHireEmployees("Department " + id + " has employees. They don't wanna be hired.");
    }

    @Override
    public List<EmployeeDTO> getAllEmployeesByDepartmentById(Long id) {
        return employeeClient.getAllEmployeesFromDepartmentByDepartment(id);
    }


}