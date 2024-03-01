package miaad.rh.stagiaire.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StagiaireDto {
    private Long id;
    private String city;
    private String levelStudies;
    private String linkedin;
    private MultipartFile cv;
    private String status;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String phone;
}
