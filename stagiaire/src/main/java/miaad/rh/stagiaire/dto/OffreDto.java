package miaad.rh.stagiaire.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffreDto {
    private Long id;
    private String title;
    private String type;
    private int duration;
    private Date startDate;
    private boolean remuneration;
    private String diploma;
    private String  description;
}
