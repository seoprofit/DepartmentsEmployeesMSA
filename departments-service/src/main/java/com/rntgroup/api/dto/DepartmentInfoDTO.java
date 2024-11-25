package com.rntgroup.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentInfoDTO {
    private Long departmentId;
    private String name;
    private String operation;
}
