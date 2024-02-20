package miaad.rh.stagiaire.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DemandeDto {
    private Long id;
    private String nomStagaire;
    private Date dateDebut;
    private Date dateFin;
    private String email;
}
