package com.rntgroup.service.impl;

import com.rntgroup.db.entity.DepartmentManipulation;
import com.rntgroup.db.repository.DepartmentInfoRepository;
import com.rntgroup.service.DepartmentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentInfoServiceImpl implements DepartmentInfoService {

    private final DepartmentInfoRepository departmentInfoRepository;

    @Override
    public void saveDepartmentInfo(DepartmentManipulation departmentManipulation) {
        departmentInfoRepository.save(departmentManipulation);
    }
}
