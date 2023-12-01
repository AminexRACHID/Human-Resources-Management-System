package miaad.rh.absence.mapper;

import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.entity.Absence;

public class AbsenceMapper {
    public static AbsenceDto mapToAbsenceDto(Absence absence){
        return new AbsenceDto(
                absence.getId(),
                absence.getColaborateurId(),
                absence.isEmployee(),
                absence.getAbsenceDate(),
                absence.getAbsenceNature(),
                absence.getJustifie(),
                absence.getJustification()
        );
    }

    public static Absence mapToAbsence(AbsenceDto absenceDto){
        return new Absence(
                absenceDto.getId(),
                absenceDto.getColaborateurId(),
                absenceDto.isEmployee(),
                absenceDto.getAbsenceDate(),
                absenceDto.getAbsenceNature(),
                absenceDto.getJustifie(),
                absenceDto.getJustification()
        );
    }
}











