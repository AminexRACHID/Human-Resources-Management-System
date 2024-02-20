package miaad.rh.absence.mapper;

import miaad.rh.absence.dto.DemandeAbsenceDto;
import miaad.rh.absence.entity.DemandeAbsence;

import java.io.IOException;

public class DemandeMapper {
    public static DemandeAbsenceDto mapToDemandeDto(DemandeAbsence demande){
        return new DemandeAbsenceDto(
                demande.getId(),
                demande.getColaborateurId(),
                demande.isEmployee(),
                demande.getAbsenceDate(),
                demande.getDuration(),
                demande.getAbsenceNature(),
                demande.getJustifie(),
                null
        );
    }

    public static DemandeAbsence mapToDemande(DemandeAbsenceDto demande) throws IOException {
        return new DemandeAbsence(
                demande.getId(),
                demande.getColaborateurId(),
                demande.isEmployee(),
                demande.getAbsenceDate(),
                demande.getDuration(),
                demande.getAbsenceNature(),
                demande.getJustifie(),
                demande.getJustificationFile().getBytes()
        );
    }
}
