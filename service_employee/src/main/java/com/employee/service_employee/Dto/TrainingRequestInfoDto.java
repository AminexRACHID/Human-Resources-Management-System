package com.employee.service_employee.Dto;

import com.employee.service_employee.Entity.TrainingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequestInfoDto {
    private TrainingRequest trainingRequestDto;
    private EmployeeDto employeeDto;
    private FormationDto formationDto;
}
