package com.rntgroup.db.repository;

import com.rntgroup.db.entity.DepartmentSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentSalaryFundRepository extends JpaRepository<DepartmentSalary, Long> {

    Optional<DepartmentSalary> findById(Long id);

    Optional<DepartmentSalary> findByName(String department);

    List<DepartmentSalary> findAll();

    @Query("SELECT departmentSalary.name FROM DepartmentSalary departmentSalary")
    List<String> getDepartmentsSalaryNames();


}
