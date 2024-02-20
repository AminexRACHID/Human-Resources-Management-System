package miaad.sgrh.user.service;

import miaad.sgrh.user.dto.StagiaireDto;
import miaad.sgrh.user.entity.Stagiaire;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StagiaireService {
    StagiaireDto getStagiaireInfoById(Long id);
    StagiaireDto getStagiaireInfoByEmail(String email);

    List<StagiaireDto> getStagiaireByFirstAndLastName(String lastName,String firstName);

    List<StagiaireDto> getAllStagiaires();
//    List<StagiaireDto> getStagiaireByStatus(String status);
    void deleteStagiaires(Long id);
    StagiaireDto updateStagiaire(Long id, StagiaireDto updatedStagiaire, MultipartFile cvFile);

    Stagiaire getStagiaireDocument(Long id);
}
