package com.employee.service_employee.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FormationDto {

    private Long id;

    private String nomFormation;
    private String objectif;
    private String collaborateurs;
    private int duree;
    private LocalDate date;
}
