package miaad.rh.absence.service.impl;

import lombok.AllArgsConstructor;
import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.entity.Absence;
import miaad.rh.absence.exception.ResourceNotFoundException;
import miaad.rh.absence.mapper.AbsenceMapper;
import miaad.rh.absence.repository.AbsenceRepository;
import miaad.rh.absence.service.AbsenceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AbsenceServiceImpl implements AbsenceService {
    private AbsenceRepository absenceRepository;
    @Override
    public AbsenceDto createAbsence(AbsenceDto absenceDto) {
        Absence absence = AbsenceMapper.mapToAbsence(absenceDto);
        Absence savedAbsence = absenceRepository.save(absence);
        return AbsenceMapper.mapToAbsenceDto(savedAbsence);
    }

    @Override
    public AbsenceDto getAbsencebyId(Long absenceId) {
        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Pas d'absence avec cette id : " + absenceId));


        return AbsenceMapper.mapToAbsenceDto(absence);
    }

    @Override
    public List<AbsenceDto> getAllAbsences() {
        List<Absence> absences = absenceRepository.findAll();
        return absences.stream().map((absence -> AbsenceMapper.mapToAbsenceDto(absence)))
                .collect(Collectors.toList());
    }

    @Override
    public AbsenceDto updateAbsence(Long absenceId, AbsenceDto updateAbsence) {
        Absence absence = absenceRepository.findById(absenceId).orElseThrow(
                () -> new ResourceNotFoundException("Pas d'absence avec cette id : " + absenceId)
        );

        absence.setAbsenceDate(updateAbsence.getAbsenceDate());
        absence.setCollaborateurId(updateAbsence.getCollaborateurId());
        absence.setAbsenceNature(updateAbsence.getAbsenceNature());
        absence.setJustifie(updateAbsence.getJustifie());
        absence.setJustification(updateAbsence.getJustification());

        Absence updatedAbsenceObj = absenceRepository.save(absence);

        return AbsenceMapper.mapToAbsenceDto(updatedAbsenceObj);
    }

    @Override
    public void deleteAbsence(Long absenceId) {
        Absence absence = absenceRepository.findById(absenceId).orElseThrow(
                () -> new ResourceNotFoundException("Pas d'absence avec cette id : " + absenceId)
        );

        absenceRepository.deleteById(absenceId);
    }
}








