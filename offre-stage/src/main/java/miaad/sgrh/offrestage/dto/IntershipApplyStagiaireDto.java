package miaad.sgrh.offrestage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miaad.sgrh.offrestage.entity.Stage;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IntershipApplyStagiaireDto {
    private Long intershipApplyId;
    private String status;
    private Stagiaire stagiaire;
    private Stage stage;

}
