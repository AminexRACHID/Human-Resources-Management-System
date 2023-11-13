package miaad.rh.stagiaire.service;

import miaad.rh.stagiaire.dto.StagaireDto;

import java.util.List;

public interface StagaireService {
    StagaireDto createStagaire(StagaireDto stagaireDto);

    StagaireDto getStagaireById(Long stagaireId);

    List<StagaireDto> getAllStagaires();

    StagaireDto updateStagaire(Long StagaireId, StagaireDto updatedStagaire);

    void deleteStagaire(Long stagaireId);
}
