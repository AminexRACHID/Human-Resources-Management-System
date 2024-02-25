package miaad.rh.absence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AbsenceNonJustufiee {
    private String firstName;
    private String lastName;
    private String email;
    private AbsenceDto absence;
}
