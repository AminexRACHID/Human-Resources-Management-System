package miaad.rh.stagiaire.mapper;

import miaad.rh.stagiaire.dto.StagaireDto;
import miaad.rh.stagiaire.entity.Stagaire;

public class StagaireMapper {
    public static StagaireDto mapToStagaireDto(Stagaire stagaire){
        return new StagaireDto(
                stagaire.getId(),
                stagaire.getCity(),
                stagaire.getLevelStadies(),
                stagaire.getLinkedin(),
                stagaire.getStatus()
        );
    }

    public static Stagaire mapToStagaire(StagaireDto stagaireDto){
        return new Stagaire(
                stagaireDto.getId(),
                stagaireDto.getCity(),
                stagaireDto.getLevelStadies(),
                stagaireDto.getLinkedin(),
                stagaireDto.getStatus()
        );
    }
}
