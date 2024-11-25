package com.rntgroup.service.impl;

import com.rntgroup.api.dto.DepartmentInfoDTO;
import com.rntgroup.mappers.DepartmentMapper;
import com.rntgroup.service.DepartmentSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class KafkaDepartmentConsumerService {
    private final DepartmentMapper departmentMapper;
    private final DepartmentSnapshotService departmentSnapshotService;

    @KafkaListener(topics = "department-topic", groupId = "department-group")
    public void listen(DepartmentInfoDTO departmentInfoDTO) {
        if (departmentInfoDTO.getName() != null) {
            DepartmentInfoDTO getOldDepartmentInfoDTO = departmentSnapshotService.findByName(departmentInfoDTO.getName());
            if (getOldDepartmentInfoDTO == null)
                departmentSnapshotService.save(departmentMapper.departmentToKafkaEntity(departmentInfoDTO));
        }
    }


}
