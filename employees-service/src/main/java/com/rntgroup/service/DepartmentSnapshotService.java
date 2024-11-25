package com.rntgroup.service;

import com.rntgroup.api.dto.DepartmentDTO;
import com.rntgroup.api.dto.DepartmentInfoDTO;
import com.rntgroup.db.entity.DepartmentInfoEntity;

public interface DepartmentSnapshotService {

    DepartmentInfoDTO findByName(String name);

    void save(DepartmentInfoEntity departmentInfoEntity);

    DepartmentDTO checkForDepartmentFromSnapshot(Long departmentId);

    void saveDepartmentFromFeignClient(DepartmentDTO departmentFromFeignClient);
}
