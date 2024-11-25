package com.rntgroup.mappers;

import com.rntgroup.db.entity.Employee;
import com.rntgroup.api.dto.EmployeeDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO employeeToDTO(Employee employeeEntity);
}
