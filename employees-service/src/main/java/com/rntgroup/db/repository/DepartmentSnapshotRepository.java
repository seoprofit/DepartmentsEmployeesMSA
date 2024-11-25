package com.rntgroup.db.repository;

import com.rntgroup.db.entity.DepartmentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentSnapshotRepository extends JpaRepository<DepartmentInfoEntity, Long> {

    DepartmentInfoEntity findByName(String name);

    Optional<DepartmentInfoEntity> findByDepartmentId(Long departmentId);
}
