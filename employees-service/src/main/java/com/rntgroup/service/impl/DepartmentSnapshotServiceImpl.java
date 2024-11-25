package com.rntgroup.service.impl;

import com.rntgroup.api.dto.DepartmentDTO;
import com.rntgroup.api.dto.DepartmentInfoDTO;
import com.rntgroup.db.entity.DepartmentInfoEntity;
import com.rntgroup.db.repository.DepartmentSnapshotRepository;
import com.rntgroup.mappers.DepartmentMapper;
import com.rntgroup.service.DepartmentClient;
import com.rntgroup.service.DepartmentSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DepartmentSnapshotServiceImpl implements DepartmentSnapshotService {
    private final DepartmentSnapshotRepository departmentSnapshotRepository;
    private final DepartmentMapper departmentMapper;
    private final DepartmentClient departmentClient;

    @Override
    public DepartmentInfoDTO findByName(String name) {
        return departmentMapper.departmentKafkaToDTO(departmentSnapshotRepository.findByName(name));
    }

    @Override
    public void save(DepartmentInfoEntity departmentInfoEntity) {
        departmentSnapshotRepository.save(departmentInfoEntity);
    }

    @Override
    public DepartmentDTO checkForDepartmentFromSnapshot(Long departmentId) {
        Optional<DepartmentInfoEntity> departmentInfoKafkaEntity = departmentSnapshotRepository.findByDepartmentId(departmentId);
        DepartmentDTO departmentFromFeignClient = new DepartmentDTO();
        if (departmentInfoKafkaEntity.isEmpty()) {
            departmentFromFeignClient = departmentClient.findById(departmentId);
            saveDepartmentFromFeignClient(departmentFromFeignClient);
        } else {
            DepartmentInfoDTO departmentInfoDTO = departmentMapper.departmentKafkaToDTO(departmentInfoKafkaEntity.get());
            departmentFromFeignClient.setId(departmentInfoDTO.getDepartmentId());
            departmentFromFeignClient.setName(departmentInfoDTO.getName());
        }
        return departmentFromFeignClient;
    }

    @Override
    public void saveDepartmentFromFeignClient(DepartmentDTO departmentFromFeignClient) {
        if (departmentFromFeignClient.getName() != null)
            departmentSnapshotRepository.save(departmentMapper.departmentToKafkaEntity(departmentFromFeignClient));
    }
}
