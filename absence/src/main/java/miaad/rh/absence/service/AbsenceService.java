package miaad.rh.absence.service;

import miaad.rh.absence.dto.AbsenceDto;

import java.util.List;

public interface AbsenceService {
    AbsenceDto createAbsence(AbsenceDto absenceDto);
    AbsenceDto getAbsencebyId(Long absenceId);
    List<AbsenceDto> getAllAbsences();
    AbsenceDto updateAbsence(Long absenceId, AbsenceDto updateAbsence);
    void deleteAbsence(Long absenceId);
}






