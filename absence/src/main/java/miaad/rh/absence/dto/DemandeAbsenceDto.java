package miaad.rh.absence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DemandeAbsenceDto {
    private Long id;
    private Long colaborateurId;
    private boolean employee;
    private Date absenceDate;
    private String duration;
    private String absenceNature;
    private String justifie;
    private MultipartFile justificationFile;
}
