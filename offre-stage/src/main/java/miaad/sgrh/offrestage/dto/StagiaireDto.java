package miaad.sgrh.offrestage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StagiaireDto implements Serializable {
    private Long stageId;
    private Long id;
    private String city;
    private String levelStudies;
    private String linkedin;
    private MultipartFile cv;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String phone;
}