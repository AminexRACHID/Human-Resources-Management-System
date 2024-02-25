package com.employee.service_employee.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequestStatusDto {
    private Long pending;
    private Long rejected;
    private Long accepted;
}
