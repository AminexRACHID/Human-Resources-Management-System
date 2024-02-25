package miaad.rh.absence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AbsenceEmployee {
    private AbsenceDto absence;
    private EmployeeDto employee;
}
