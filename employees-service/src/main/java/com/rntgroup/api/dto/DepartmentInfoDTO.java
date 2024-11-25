package com.rntgroup.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class DepartmentInfoDTO {
    private Long id;
    private Long departmentId;
    private String name;
    private String operation;

}
