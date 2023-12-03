package miaad.rh.absence.service.impl;

import lombok.AllArgsConstructor;
import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.dto.EmployeeDto;
import miaad.rh.absence.dto.StagaireDto;
import miaad.rh.absence.entity.Absence;
import miaad.rh.absence.exception.ResourceNotFoundException;
import miaad.rh.absence.feign.EmployeeRestClient;
import miaad.rh.absence.feign.StagaireRestClient;
import miaad.rh.absence.mapper.AbsenceMapper;
import miaad.rh.absence.repository.AbsenceRepository;
import miaad.rh.absence.service.AbsenceService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AbsenceServiceImpl implements AbsenceService {
    private AbsenceRepository absenceRepository;
    private JavaMailSender javaMailSender;
    private EmployeeRestClient employeeRestClient;
    private StagaireRestClient stagaireRestClient;

    @Override
    public AbsenceDto createAbsence(AbsenceDto absenceDto) throws IOException {

        byte[] justification = absenceDto.getJustificationFile().getBytes();

        Absence absence = new Absence();
        absence.setColaborateurId(absenceDto.getColaborateurId());
        absence.setEmployee(absenceDto.isEmployee());
        absence.setAbsenceDate(absenceDto.getAbsenceDate());
        absence.setAbsenceNature(absenceDto.getAbsenceNature());
        absence.setJustifie(absenceDto.getJustifie());
        absence.setJustification(justification);


        Absence savedAbsence = absenceRepository.save(absence);
        return AbsenceMapper.mapToAbsenceDto(savedAbsence);
    }


    @Override
    public List<AbsenceDto> getAllAbsences() {
        List<Absence> absences = absenceRepository.findAll();
        return absences.stream().map((absence -> AbsenceMapper.mapToAbsenceDto(absence)))
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsenceBycollaborateurId(Long collaborateurId) {
        List<Absence> absences = absenceRepository.findAll();

        return absences.stream()
                .filter(absence -> absence.getColaborateurId().equals(collaborateurId))
                .map(absence -> AbsenceMapper.mapToAbsenceDto(absence))
                .collect(Collectors.toList());
    }


    @Override
    public AbsenceDto updateAbsence(Long absenceId, AbsenceDto updateAbsence) {
        Absence absence = absenceRepository.findById(absenceId).orElseThrow(
                () -> new ResourceNotFoundException("Pas d'absence avec cette id : " + absenceId)
        );

        absence.setAbsenceDate(updateAbsence.getAbsenceDate());
        absence.setColaborateurId(updateAbsence.getColaborateurId());
        absence.setAbsenceNature(updateAbsence.getAbsenceNature());
        absence.setJustifie(updateAbsence.getJustifie());

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


    // Envoyer un email
    public void sendEmailToCollaborateur(Long collaborateurId, String subject, String message, boolean employee) {
        // Récupérez l'adresse e-mail du collaborateur à partir d'une fonction
        String collaborateurEmail = "";
        if (employee == true){
            EmployeeDto employeeDto = employeeRestClient.getEmployeeById(collaborateurId);
            collaborateurEmail = employeeDto.getEmail();
        } else {
            StagaireDto stagaireDto = stagaireRestClient.getStagiaireById(collaborateurId);
            collaborateurEmail = stagaireDto.getEmail();
        }


        // Envoyer l'e-mail
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(collaborateurEmail);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);
        javaMailSender.send(emailMessage);
    }

    public Optional<Absence> getAbsenceById(Long absenceId) {
        Optional<Absence> absence = Optional.ofNullable(absenceRepository.findById(absenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Absence not Found with given id" + absenceId)));

        return  absence;
    }
}








