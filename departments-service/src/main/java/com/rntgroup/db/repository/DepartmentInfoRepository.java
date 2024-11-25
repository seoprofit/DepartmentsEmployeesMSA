package com.rntgroup.db.repository;

import com.rntgroup.db.entity.DepartmentManipulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentInfoRepository extends JpaRepository<DepartmentManipulation, Long> {

}
