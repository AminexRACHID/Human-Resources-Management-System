package miaad.sgrh.offrestage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StageDto {
    private Long id;
    private String title;
    private String type;
    private int duration;
    private Date startDate;
    private boolean remuneration;
    private String diploma;
    private String  description;
}
