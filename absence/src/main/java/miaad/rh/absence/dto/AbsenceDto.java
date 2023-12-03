package miaad.rh.absence.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
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
public class AbsenceDto {
    private Long id;
    private Long colaborateurId;
    private boolean employee;
    private Date absenceDate;
    private String absenceNature;
    private String justifie;
    private MultipartFile justificationFile;
}
