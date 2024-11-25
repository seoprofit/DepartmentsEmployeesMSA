package com.rntgroup.mappers;

import com.rntgroup.api.dto.DepartmentDTO;
import com.rntgroup.db.entity.Department;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DepartmentInfoMapper {

    Department departmentInfoToEntity(DepartmentDTO departmentDTO);

    DepartmentDTO departmentInfoToDTO(Department department);

}
