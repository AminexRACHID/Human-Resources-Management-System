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
public class TrainingRequestDto {
    private Long id;
    private LocalDate requestedDate;
    private String status;
    private Long employeeId;
    private Long formationId;
}
