package miaad.sgrh.user.mapper;

import miaad.sgrh.user.dto.StagiaireDto;
import miaad.sgrh.user.dto.UserDto;
import miaad.sgrh.user.entity.Stagiaire;

import java.io.IOException;

public class StagiaireMapper {
    public static StagiaireDto mapToStagiaireDto(Stagiaire stagiaire){
        return new StagiaireDto(
                stagiaire.getId(),
                stagiaire.getCity(),
                stagiaire.getLevelStudies(),
                stagiaire.getLinkedin(),
                null,
                stagiaire.getStatus(),
                stagiaire.getFirstName(),
                stagiaire.getLastName(),
                stagiaire.getEmail(),
                stagiaire.getGender(),
                stagiaire.getPhone()
        );
    }

    public static Stagiaire mapToStagiaire(StagiaireDto stagiaireDto) throws IOException {
        return new Stagiaire(
                stagiaireDto.getId(),
                stagiaireDto.getCity(),
                stagiaireDto.getLevelStudies(),
                stagiaireDto.getLinkedin(),
                stagiaireDto.getCv().getBytes(),
                stagiaireDto.getStatus()
        );
    }

}
