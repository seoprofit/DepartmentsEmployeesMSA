package com.rntgroup.service;

import com.rntgroup.api.dto.DepartmentInfoDTO;
import com.rntgroup.config.DepartmentServicePropertySource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class KafkaDepartmentProducerService {
    private final KafkaTemplate<String, DepartmentInfoDTO> kafkaTemplate;
    private final DepartmentServicePropertySource departmentServicePropertySource;

    public void sendMessage(DepartmentInfoDTO object) {
        kafkaTemplate.send(departmentServicePropertySource.getTopicName(), object);
    }

}
