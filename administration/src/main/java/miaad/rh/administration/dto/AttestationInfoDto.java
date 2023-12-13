package miaad.rh.administration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttestationInfoDto {
    private String nomPrenom;
    private String cin;
    private String poste;
    private Date dateOccupation;
    private String nomEtablissement;
}
