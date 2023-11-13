package miaad.sgrh.user.mapper;

import miaad.sgrh.user.dto.StagiaireDto;
import miaad.sgrh.user.dto.UserDto;
import miaad.sgrh.user.entity.Stagiaire;

public class StagiaireMapper {
    public static StagiaireDto mapToStagiaireDto(Stagiaire stagiaire){
        return new StagiaireDto(
                stagiaire.getId(),
                stagiaire.getCity(),
                stagiaire.getLevelStudies(),
                stagiaire.getLinkedin(),
                stagiaire.getCv(),
                stagiaire.getStatus()
        );
    }

    public static Stagiaire mapToStagiaire(StagiaireDto stagiaireDto){
        return new Stagiaire(
                stagiaireDto.getId(),
                stagiaireDto.getCity(),
                stagiaireDto.getLevelStudies(),
                stagiaireDto.getLinkedin(),
                stagiaireDto.getCv(),
                stagiaireDto.getStatus()
        );
    }

}
