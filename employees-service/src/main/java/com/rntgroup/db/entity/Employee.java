package com.rntgroup.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String sex;
    private String number;
    @Column(name = "date_of_fire")
    private LocalDate dateOfFire;
    @Column(name = "date_of_hire")
    private LocalDate dateOfHire;
    private boolean chief;
    @NotNull
    @Min(value = 0)
    private double salary;
    @Column(name = "department_id")
    private Long department;

}
