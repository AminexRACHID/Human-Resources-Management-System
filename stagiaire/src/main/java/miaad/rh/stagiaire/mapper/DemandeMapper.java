package miaad.rh.stagiaire.mapper;

import miaad.rh.stagiaire.dto.DemandeDto;
import miaad.rh.stagiaire.entity.Demande;

public class DemandeMapper {
    public static DemandeDto mapToDemandeDto(Demande demande){
        return new DemandeDto(
                demande.getId(),
                demande.getIdStage(),
                demande.getEmail()
        );
    }

    public static Demande mapToDemande(DemandeDto demandeDto){
        return new Demande(
                demandeDto.getId(),
                demandeDto.getIdStage(),
                demandeDto.getEmail()
        );
    }
}
