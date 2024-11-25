package com.rntgroup.db.repository;

import com.rntgroup.db.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

    Optional<Department> findById(Long id);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department = :department")
    Integer getCountEmployeesAtDepartment(Department department);

    @Query("SELECT department.name FROM Department department")
    List<String> getDepartmentsNames();
}
