package miaad.rh.absence.service;

import miaad.rh.absence.dto.AbsenceDto;

import java.io.IOException;
import java.util.List;


public interface AbsenceService {
    AbsenceDto createAbsence(AbsenceDto absenceDoc) throws IOException;
    List<AbsenceDto> getAllAbsences();
    List<AbsenceDto> getAbsenceBycollaborateurId(Long collaborateurId);
    AbsenceDto updateAbsence(Long absenceId, AbsenceDto updateAbsence);
    void deleteAbsence(Long absenceId);
}






