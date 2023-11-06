package miaad.rh.absence.controller;

import lombok.AllArgsConstructor;
import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.entity.Absence;
import miaad.rh.absence.service.AbsenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/absences")
public class AbsenceController {
    private AbsenceService absenceService;
    private JavaMailSender javaMailSender;

    @PostMapping
    public ResponseEntity<AbsenceDto> createAbsence(@RequestBody AbsenceDto absenceDto) {
        AbsenceDto savedAbsence = absenceService.createAbsence(absenceDto);
        Long collaborateurId = absenceDto.getCollaborateurId();
        List<AbsenceDto> collaborateurAbsences = absenceService.getAbsenceBycollaborateurId(collaborateurId);
        int numberOfAbsencesForCollaborateur = collaborateurAbsences.size();

        if (numberOfAbsencesForCollaborateur >= 6) {
            String message = "Vous avez dépassé le seuil des absences.";
            sendEmailToCollaborateur(collaborateurId, message);
        }

        return new ResponseEntity<>(savedAbsence, HttpStatus.CREATED);
    }

    private void sendEmailToCollaborateur(Long collaborateurId, String message) {
        // Récupérez l'adresse e-mail du collaborateur à partir de sa base de données ou d'une autre source
        String collaborateurEmail = "amine.rcd.ar@gmail.com";

        // Envoyer l'e-mail
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(collaborateurEmail);
        emailMessage.setSubject("Dépassement du seuil d'absences");
        emailMessage.setText(message);
        javaMailSender.send(emailMessage);
    }

    @GetMapping("{collaborateurId}")
    public ResponseEntity<List<AbsenceDto>> getAbsenceBycollaborateurId(@PathVariable("collaborateurId")Long collaborateurId) {
        List<AbsenceDto> absences = absenceService.getAbsenceBycollaborateurId(collaborateurId);
        return ResponseEntity.ok(absences);
    }

    @GetMapping
    public ResponseEntity<List<AbsenceDto>> getAllAbsences(){
        List<AbsenceDto> absences = absenceService.getAllAbsences();
        return ResponseEntity.ok(absences);
    }

    @PutMapping("{id}")
    public ResponseEntity<AbsenceDto> updateAbsence(@PathVariable("id") Long absenceId,
                                                    @RequestBody AbsenceDto updatedAbsence){
        AbsenceDto absenceDto = absenceService.updateAbsence(absenceId, updatedAbsence);
        return ResponseEntity.ok(absenceDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAbsence(@PathVariable("id") Long absenceId){
        absenceService.deleteAbsence(absenceId);
        return ResponseEntity.ok("Absence suprimé avec sucées");
    }
}

















