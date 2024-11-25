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
public class EmployeeDTO {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate dateOfBirth;
    private String sex;
    private String number;
    private LocalDate dateOfFire;
    private LocalDate dateOfHire;
    private boolean chief;
    private double salary;
    private Long department;
}


