package miaad.rh.absence.service;

import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.dto.AbsenceJustifierNonJutifierDto;
import miaad.rh.absence.dto.DemandeAbsenceDto;
import miaad.rh.absence.entity.Absence;
import miaad.rh.absence.entity.DemandeAbsence;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface AbsenceService {
    AbsenceDto createAbsence(AbsenceDto absenceDoc, MultipartFile file) throws IOException;

    AbsenceDto createAbsenceFromDemande(Long id) throws IOException;

    AbsenceJustifierNonJutifierDto getNobreAbsencesJustifierNonJustifier(Long id);
    AbsenceJustifierNonJutifierDto getNobreAbsencesJustifierNonJustifierStagaire(Long id);

    List<AbsenceDto> getAllAbsences();
    List<AbsenceDto> getAllAbsencesJustier();
    List<AbsenceDto> getAllAbsencesNonJustifier();
    List<AbsenceDto> getAbsenceByEmployeeId(Long collaborateurId);
    List<AbsenceDto> getAbsenceByStagiaireId(Long collaborateurId);
    AbsenceDto updateAbsence(Long absenceId, AbsenceDto updateAbsence);
    void deleteAbsence(Long absenceId);
    Optional<Absence> getAbsenceById(Long absenceId);
    DemandeAbsenceDto createDemande(DemandeAbsenceDto demandeAbsenceDto) throws IOException;
    List<DemandeAbsenceDto> getAllDemandes();
    void deleteDemande(Long demandeId);
}






