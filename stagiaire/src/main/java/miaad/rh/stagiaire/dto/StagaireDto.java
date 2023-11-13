package miaad.rh.stagiaire.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StagaireDto {
    private Long id;
    private String city;
    private String levelStadies;
    private String linkedin;
    private String status;
}
