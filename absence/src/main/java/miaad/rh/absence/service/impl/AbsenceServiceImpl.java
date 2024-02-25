package miaad.rh.absence.service.impl;

import lombok.AllArgsConstructor;
import miaad.rh.absence.dto.*;
import miaad.rh.absence.entity.Absence;
import miaad.rh.absence.entity.DemandeAbsence;
import miaad.rh.absence.exception.ResourceNotFoundException;
import miaad.rh.absence.feign.EmployeeRestClient;
import miaad.rh.absence.feign.StagaireRestClient;
import miaad.rh.absence.mapper.AbsenceMapper;
import miaad.rh.absence.mapper.DemandeMapper;
import miaad.rh.absence.repository.AbsenceRepository;
import miaad.rh.absence.repository.DemandeRepository;
import miaad.rh.absence.service.AbsenceService;
import org.apache.catalina.mapper.Mapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private DemandeRepository demandeRepository;

    @Override
    public AbsenceDto createAbsence(AbsenceDto absenceDto, MultipartFile file) throws IOException {

        System.out.println(absenceDto.getColaborateurId()+" "+absenceDto.getAbsenceDate());

        Absence absence = new Absence();
        absence.setColaborateurId(absenceDto.getColaborateurId());
        absence.setEmployee(absenceDto.isEmployee());
        absence.setAbsenceDate(absenceDto.getAbsenceDate());
        absence.setDuration(absenceDto.getDuration());
        absence.setAbsenceNature(absenceDto.getAbsenceNature());
        if (file != null){
            byte[] justification = file.getBytes();
            absence.setJustification(justification);
        }
        absence.setJustifie(absenceDto.getJustifie());



        Absence savedAbsence = absenceRepository.save(absence);
        return AbsenceMapper.mapToAbsenceDto(savedAbsence);
    }


    @Override
    public AbsenceDto createAbsenceFromDemande(Long id) throws IOException {

        DemandeAbsence demandeAbsenceDto = demandeRepository.findDemandeAbsenceById(id);
        Absence absence = AbsenceMapper.mapFromDemandeDtoToAbsence1(demandeAbsenceDto);


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
    public List<AbsenceDto> getAllAbsencesJustier() {
        List<Absence> absences = absenceRepository.findByAbsenceNature("Justifier");
        return absences.stream()
                .map(absence -> AbsenceMapper.mapToAbsenceDto(absence))
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAllAbsencesNonJustifier() {
        List<Absence> absences = absenceRepository.findByAbsenceNature("Non Justifier");
        return absences.stream()
                .map(absence -> AbsenceMapper.mapToAbsenceDto(absence))
                .collect(Collectors.toList());
    }


    @Override
    public List<AbsenceDto> getAbsenceByEmployeeId(Long collaborateurId) {
        List<Absence> absences = absenceRepository.findAll();

        return absences.stream()
                .filter(absence -> absence.getColaborateurId().equals(collaborateurId) && absence.isEmployee() )
                .map(absence -> AbsenceMapper.mapToAbsenceDto(absence))
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsenceByStagiaireId(Long collaborateurId) {
        List<Absence> absences = absenceRepository.findAll();

        return absences.stream()
                .filter(absence -> absence.getColaborateurId().equals(collaborateurId) && !absence.isEmployee() )
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

    @Override
    public DemandeAbsenceDto createDemande(DemandeAbsenceDto demandeAbsenceDto) throws IOException {
        byte[] justification = demandeAbsenceDto.getJustificationFile().getBytes();

        DemandeAbsence demandeAbsence = new DemandeAbsence();
        demandeAbsence.setColaborateurId(demandeAbsenceDto.getColaborateurId());
        demandeAbsence.setEmployee(demandeAbsenceDto.isEmployee());
        demandeAbsence.setAbsenceDate(demandeAbsenceDto.getAbsenceDate());
        demandeAbsence.setDuration(demandeAbsenceDto.getDuration());
        demandeAbsence.setAbsenceNature(demandeAbsenceDto.getAbsenceNature());
        demandeAbsence.setJustifie(demandeAbsenceDto.getJustifie());
        demandeAbsence.setJustification(justification);


        DemandeAbsence savedDemande = demandeRepository.save(demandeAbsence);
        return DemandeMapper.mapToDemandeDto(savedDemande);
    }

    @Override
    public List<DemandeAbsenceDto> getAllDemandes() {
        List<DemandeAbsence> demandeAbsences = demandeRepository.findAll();
        return demandeAbsences.stream().map((demandeAbsence -> DemandeMapper.mapToDemandeDto(demandeAbsence)))
                .collect(Collectors.toList());    }

    @Override
    public void deleteDemande(Long demandeId) {
        DemandeAbsence demandeAbsence = demandeRepository.findById(demandeId).orElseThrow(
                () -> new ResourceNotFoundException("Pas de demande avec cette id : " + demandeId)
        );

        demandeRepository.deleteById(demandeId);
    }

    @Override
    public AbsenceJustifierNonJutifierDto getNobreAbsencesJustifierNonJustifier(Long id) {
        List<Absence> absences = absenceRepository.findByColaborateurId(id);

        long absencesJustifiees = absences.stream()
                .filter(absence -> absence.isEmployee() && "Justifier".equals(absence.getAbsenceNature()))
                .count();

        long absencesNonJustifiees = absences.stream()
                .filter(absence -> absence.isEmployee() && "Non Justifier".equals(absence.getAbsenceNature()))
                .count();

        AbsenceJustifierNonJutifierDto abs = new AbsenceJustifierNonJutifierDto();
        abs.setNbrJustifier(absencesJustifiees);
        abs.setNbrNonJustifier(absencesNonJustifiees);

        return abs;
    }

    @Override
    public AbsenceJustifierNonJutifierDto getNobreAbsencesJustifierNonJustifierStagaire(Long id) {
        List<Absence> absences = absenceRepository.findByColaborateurId(id);

        long absencesJustifiees = absences.stream()
                .filter(absence -> !absence.isEmployee() && "Justifier".equals(absence.getAbsenceNature()))
                .count();

        long absencesNonJustifiees = absences.stream()
                .filter(absence -> !absence.isEmployee() && "Non Justifier".equals(absence.getAbsenceNature()))
                .count();

        AbsenceJustifierNonJutifierDto abs = new AbsenceJustifierNonJutifierDto();
        abs.setNbrJustifier(absencesJustifiees);
        abs.setNbrNonJustifier(absencesNonJustifiees);

        return abs;
    }

    @Override
    public void deleteDemandeAbsenceByCollaborateurId(Long id) {
        List<DemandeAbsence> demandeAbsences = demandeRepository.findByColaborateurId(id);

        for (DemandeAbsence demandeAbsence : demandeAbsences) {
            demandeRepository.delete(demandeAbsence);
        }
    }

    @Override
    public void deleteAbsenceByCollaborateurId(Long id) {
        List<Absence> absences = absenceRepository.findByColaborateurId(id);

        for (Absence absence : absences) {
            absenceRepository.delete(absence);
        }
    }


}








