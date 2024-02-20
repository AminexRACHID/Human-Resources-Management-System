package miaad.rh.stagiaire.mapper;

import miaad.rh.stagiaire.dto.DemandeDto;
import miaad.rh.stagiaire.entity.Demande;

public class DemandeMapper {
    public static DemandeDto mapToDemandeDto(Demande demande){
        return new DemandeDto(
                demande.getId(),
                demande.getNomStagaire(),
                demande.getDateDebut(),
                demande.getDateFin(),
                demande.getEmail()
        );
    }

    public static Demande mapToDemande(DemandeDto demandeDto){
        return new Demande(
                demandeDto.getId(),
                demandeDto.getNomStagaire(),
                demandeDto.getDateDebut(),
                demandeDto.getDateFin(),
                demandeDto.getEmail()
        );
    }
}
