package miaad.rh.stagiaire.service.impl;

import lombok.AllArgsConstructor;
import miaad.rh.stagiaire.dto.StagaireDto;
import miaad.rh.stagiaire.entity.Stagaire;
import miaad.rh.stagiaire.exception.ResourceNotFoundException;
import miaad.rh.stagiaire.mapper.StagaireMapper;
import miaad.rh.stagiaire.repository.StagaireRepository;
import miaad.rh.stagiaire.service.StagaireService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StagaireServiceImpl implements StagaireService {
    private StagaireRepository stagaireRepository;
    @Override
    public StagaireDto createStagaire(StagaireDto stagaireDto) {
        Stagaire stagaire = StagaireMapper.mapToStagaire(stagaireDto);
        Stagaire savedStagaire = stagaireRepository.save(stagaire);
        return StagaireMapper.mapToStagaireDto(savedStagaire);
    }

    @Override
    public StagaireDto getStagaireById(Long stagaireId) {
        Stagaire stagaire = stagaireRepository.findById(stagaireId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Stagaire not existe"));
        return StagaireMapper.mapToStagaireDto(stagaire);
    }

    @Override
    public List<StagaireDto> getAllStagaires() {
        List<Stagaire> stagaires = stagaireRepository.findAll();
        return stagaires.stream().map((stagaire) -> StagaireMapper.mapToStagaireDto(stagaire))
                .collect(Collectors.toList());
    }

    @Override
    public StagaireDto updateStagaire(Long stagaireId, StagaireDto updatedStagaire) {
        Stagaire stagaire = stagaireRepository.findById(stagaireId).orElseThrow(
                () -> new ResourceNotFoundException("pas de stagaire avec cette id" + stagaireId)
        );
        stagaire.setId(updatedStagaire.getId());
        stagaire.setCity(updatedStagaire.getCity());
        stagaire.setLevelStadies(updatedStagaire.getLevelStadies());
        stagaire.setLinkedin(updatedStagaire.getLinkedin());
        stagaire.setStatus(updatedStagaire.getStatus());

        Stagaire updatedStagaireObj = stagaireRepository.save(stagaire);
        return StagaireMapper.mapToStagaireDto(updatedStagaireObj);
    }

    @Override
    public void deleteStagaire(Long stagaireId) {
        Stagaire stagaire = stagaireRepository.findById(stagaireId).orElseThrow(
                () -> new ResourceNotFoundException("Pas de stagaire avec cette id" +stagaireId)
        );
        stagaireRepository.deleteById(stagaireId);
    }
}

