package miaad.rh.absence.controller;

import lombok.AllArgsConstructor;
import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.service.AbsenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/absences")
public class AbsenceController {
    private AbsenceService absenceService;
    private JavaMailSender javaMailSender;

    // Créer une absence et faire un test si le colaborateur à dépacer le max des absence on l'envoie un email
    @PostMapping
    public ResponseEntity<AbsenceDto> createAbsence(@RequestBody AbsenceDto absenceDto) {
        AbsenceDto savedAbsence = absenceService.createAbsence(absenceDto);
        Long collaborateurId = absenceDto.getCollaborateurId();
        List<AbsenceDto> collaborateurAbsences = absenceService.getAbsenceBycollaborateurId(collaborateurId);
        int numberOfAbsencesForCollaborateur = collaborateurAbsences.size();

        if (numberOfAbsencesForCollaborateur >= 6) {
            String message = "Vous avez dépassé le seuil des absences.";
            String subject = "Dépassement du seuil d'absences";
            sendEmailToCollaborateur(collaborateurId, subject, message);
        }

        return new ResponseEntity<>(savedAbsence, HttpStatus.CREATED);
    }


    // Envoyer un email
    private void sendEmailToCollaborateur(Long collaborateurId, String subject, String message) {
        // Récupérez l'adresse e-mail du collaborateur à partir d'une fonction
        String collaborateurEmail = "touzouzadnane0@gmail.com";

        // Envoyer l'e-mail
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(collaborateurEmail);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);
        javaMailSender.send(emailMessage);
    }

    // retourner les absences d'un colaborateur
    @GetMapping("{collaborateurId}")
    public ResponseEntity<List<AbsenceDto>> getAbsenceBycollaborateurId(@PathVariable("collaborateurId")Long collaborateurId) {
        List<AbsenceDto> absences = absenceService.getAbsenceBycollaborateurId(collaborateurId);
        return ResponseEntity.ok(absences);
    }

    // retourner tous les absences
    @GetMapping
    public ResponseEntity<List<AbsenceDto>> getAllAbsences(){
        List<AbsenceDto> absences = absenceService.getAllAbsences();
        return ResponseEntity.ok(absences);
    }

    // modifier une absence
    @PutMapping("{id}")
    public ResponseEntity<AbsenceDto> updateAbsence(@PathVariable("id") Long absenceId,
                                                    @RequestBody AbsenceDto updatedAbsence){
        AbsenceDto absenceDto = absenceService.updateAbsence(absenceId, updatedAbsence);
        return ResponseEntity.ok(absenceDto);
    }

    // Suprimer une absence
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAbsence(@PathVariable("id") Long absenceId){
        absenceService.deleteAbsence(absenceId);
        return ResponseEntity.ok("Absence suprimé avec sucées");
    }
}

















