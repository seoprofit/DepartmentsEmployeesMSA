package com.rntgroup.db.repository;

import com.rntgroup.db.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);

    @Query("SELECT SUM(emp.salary) FROM Employee emp WHERE emp.department = :department")
    Double getAllEmployeesSalaryAtDepartment(@Param("department") Long department);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department = :department")
    Integer getCountAllEmployeesFromDepartmentByDepartment(@Param("department") Long department);

    @Query("SELECT EXISTS (SELECT 1 FROM Employee e WHERE e.department = :department)")
    Boolean CheckExistsEmployeesFromDepartmentByDepartment(@Param("department") Long department);

    @Query("SELECT e FROM Employee e WHERE e.department = :department")
    List<Employee> getAllEmployeesFromDepartmentByDepartment(@Param("department") Long department);

}

