package com.rntgroup.service;

import com.rntgroup.db.entity.DepartmentSalary;

import java.util.List;

public interface DepartmentFundService {

    List<DepartmentSalary> findAllDepartmentFund();

    void saveDepartmentFund(DepartmentSalary departmentSalary);

    void deleteDepartmentFundById(Long id);

    DepartmentSalary findByName(String name);

    List<String> getAllDepartmentsSalaryNames();
}
