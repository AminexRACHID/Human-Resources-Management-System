package miaad.rh.stagiaire.service;

import miaad.rh.stagiaire.dto.DemandeDto;
import miaad.rh.stagiaire.dto.EmailInfoDto;
import miaad.rh.stagiaire.dto.StagaireDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface StagaireService {
    DemandeDto createDemande(DemandeDto demandeDto);
    List<DemandeDto> getAllDemandes();
    void deleteDemande(Long demandeId);
    Date getDateFin(Date dateDebut, Long duration);
    void sendAttestation(EmailInfoDto emailInfoDto);
    ByteArrayResource generateAttestation(DemandeDto demandeDto);
    byte[] generateAttestationPdf(DemandeDto demandeDto);
}
