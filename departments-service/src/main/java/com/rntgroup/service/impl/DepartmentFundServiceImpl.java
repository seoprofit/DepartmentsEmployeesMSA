package com.rntgroup.service.impl;

import com.rntgroup.db.entity.DepartmentSalary;
import com.rntgroup.db.repository.DepartmentSalaryFundRepository;
import com.rntgroup.mappers.DepartmentMapper;
import com.rntgroup.service.DepartmentFundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DepartmentFundServiceImpl implements DepartmentFundService {
    private final DepartmentSalaryFundRepository departmentSalaryFundRepository;
    private final DepartmentMapper departmentMapper;


    @Override
    public List<DepartmentSalary> findAllDepartmentFund() {
        return departmentSalaryFundRepository.findAll();
    }

    @Override
    public void saveDepartmentFund(DepartmentSalary departmentSalary) {
        departmentSalaryFundRepository.save(departmentSalary);
    }

    @Override
    public void deleteDepartmentFundById(Long id) {
        departmentSalaryFundRepository.deleteById(id);
    }

    @Override
    public DepartmentSalary findByName(String name) {
        Optional<DepartmentSalary> departmentSalary = departmentSalaryFundRepository.findByName(name);
        return departmentSalary.orElse(null);
    }

    @Override
    public List<String> getAllDepartmentsSalaryNames() {
        return departmentSalaryFundRepository.getDepartmentsSalaryNames();
    }


}