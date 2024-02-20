package miaad.rh.stagiaire.service.impl;

import lombok.AllArgsConstructor;
import miaad.rh.stagiaire.dto.DemandeDto;
import miaad.rh.stagiaire.entity.Demande;
import miaad.rh.stagiaire.exception.ResourceNotFoundException;
import miaad.rh.stagiaire.mapper.DemandeMapper;
import miaad.rh.stagiaire.repository.DemandeRepository;
import miaad.rh.stagiaire.service.StagaireService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StagaireServiceImpl implements StagaireService {
    private DemandeRepository demandeRepository;
    @Override
    public DemandeDto createDemande(DemandeDto demandeDto) {
        Demande demande = new Demande(
                demandeDto.getId(),
                demandeDto.getNomStagaire(),
                demandeDto.getDateDebut(),
                demandeDto.getDateFin(),
                demandeDto.getEmail()
        );
        Demande savedDemande = demandeRepository.save(demande);
        return DemandeMapper.mapToDemandeDto(savedDemande);
    }

    @Override
    public List<DemandeDto> getAllDemandes() {
        List<Demande> demandes = demandeRepository.findAll();
        return demandes.stream().map((demande -> DemandeMapper.mapToDemandeDto(demande)))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDemande(Long demandeId) {
        Demande demande = demandeRepository.findById(demandeId).orElseThrow(
                () -> new ResourceNotFoundException("Pas de demande avec cette id : " + demandeId)
        );
        demandeRepository.deleteById(demandeId);
    }
}

