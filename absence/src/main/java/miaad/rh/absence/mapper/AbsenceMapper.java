package miaad.rh.absence.mapper;

import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.dto.DemandeAbsenceDto;
import miaad.rh.absence.entity.Absence;
import miaad.rh.absence.entity.DemandeAbsence;

import java.io.IOException;

public class AbsenceMapper {
    public static AbsenceDto mapToAbsenceDto(Absence absence){
        return new AbsenceDto(
                absence.getId(),
                absence.getColaborateurId(),
                absence.isEmployee(),
                absence.getAbsenceDate(),
                absence.getDuration(),
                absence.getAbsenceNature(),
                absence.getJustifie(),
                null
        );
    }

    public static Absence mapToAbsence(AbsenceDto absenceDto) throws IOException {
        return new Absence(
                absenceDto.getId(),
                absenceDto.getColaborateurId(),
                absenceDto.isEmployee(),
                absenceDto.getAbsenceDate(),
                absenceDto.getDuration(),
                absenceDto.getAbsenceNature(),
                absenceDto.getJustifie(),
                absenceDto.getJustificationFile().getBytes()
        );
    }

    public static AbsenceDto mapFromDemandeDtoToAbsence(DemandeAbsenceDto absenceDto) throws IOException {
        return new AbsenceDto(
                absenceDto.getId(),
                absenceDto.getColaborateurId(),
                absenceDto.isEmployee(),
                absenceDto.getAbsenceDate(),
                absenceDto.getDuration(),
                absenceDto.getAbsenceNature(),
                absenceDto.getJustifie(),
                absenceDto.getJustificationFile()
        );
    }

    public static Absence mapFromDemandeDtoToAbsence1(DemandeAbsence absenceDto) throws IOException {
        return new Absence(
                absenceDto.getId(),
                absenceDto.getColaborateurId(),
                absenceDto.isEmployee(),
                absenceDto.getAbsenceDate(),
                absenceDto.getDuration(),
                absenceDto.getAbsenceNature(),
                absenceDto.getJustifie(),
                absenceDto.getJustification()
        );
    }
}











