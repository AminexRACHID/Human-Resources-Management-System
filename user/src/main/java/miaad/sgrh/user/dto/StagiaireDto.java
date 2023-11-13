package miaad.sgrh.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StagiaireDto {
    private Long id;
    private String city;
    private String levelStudies;
    private String linkedin;
    private byte[] cv;
    private String status;
}