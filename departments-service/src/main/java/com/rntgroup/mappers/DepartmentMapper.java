package com.rntgroup.mappers;

import com.rntgroup.db.entity.Department;
import com.rntgroup.api.dto.DepartmentDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department departmentToEntity(DepartmentDTO departmentDTO);

    DepartmentDTO departmentToDTO(Department department);

    Department DTOToEntity(DepartmentDTO departmentEntity);
}
