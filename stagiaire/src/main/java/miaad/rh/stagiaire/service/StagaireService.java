package miaad.rh.stagiaire.service;

import miaad.rh.stagiaire.dto.DemandeDto;
import miaad.rh.stagiaire.dto.StagaireDto;

import java.util.List;

public interface StagaireService {
    DemandeDto createDemande(DemandeDto demandeDto);
    List<DemandeDto> getAllDemandes();
    void deleteDemande(Long demandeId);
}
