package com.rntgroup.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDTO {
    private Long id;
    private String name;
    private LocalDate dateOfCreation;
    private int countOfEmployees;
    private String chiefOfDepartment;
    private String parentDepartment;

}
