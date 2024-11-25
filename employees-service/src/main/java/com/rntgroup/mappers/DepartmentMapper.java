package com.rntgroup.mappers;

import com.rntgroup.api.dto.DepartmentInfoDTO;
import com.rntgroup.db.entity.Department;
import com.rntgroup.api.dto.DepartmentDTO;
import com.rntgroup.db.entity.DepartmentInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department departmentToEntity(DepartmentDTO departmentDTO);

    DepartmentDTO departmentToDTO(Department department);

    DepartmentInfoDTO departmentKafkaToDTO(DepartmentInfoEntity department);

    DepartmentInfoEntity departmentToKafkaEntity(DepartmentInfoDTO departmentDTO);

    @Mapping(target = "departmentId", source = "id")
    DepartmentInfoEntity departmentToKafkaEntity(DepartmentDTO departmentDTO);
}
