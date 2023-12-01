package miaad.rh.absence.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.dto.EmployeeDto;
import miaad.rh.absence.feign.EmployeeRestClient;
import miaad.rh.absence.service.AbsenceService;
import miaad.rh.absence.service.impl.AbsenceServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/absences")
public class AbsenceController {
    private AbsenceService absenceService;
    private AbsenceServiceImpl absenceServiceImpl;



    // Créer une absence et faire un test si le colaborateur à dépacer le max des absence on l'envoie un email
    @PostMapping
    public ResponseEntity<AbsenceDto> createAbsence(@RequestBody AbsenceDto absenceDto) {
        AbsenceDto savedAbsence = absenceService.createAbsence(absenceDto);
        Long collaborateurId = absenceDto.getColaborateurId();
        List<AbsenceDto> collaborateurAbsences = absenceService.getAbsenceBycollaborateurId(collaborateurId);
        int numberOfAbsencesForCollaborateur = collaborateurAbsences.size();

        if (numberOfAbsencesForCollaborateur >= 6) {
            String message = "Vous avez dépassé le seuil des absences.";
            String subject = "Dépassement du seuil d'absences";
            absenceServiceImpl.sendEmailToCollaborateur(collaborateurId, subject, message, absenceDto.isEmployee());
        }

        return new ResponseEntity<>(savedAbsence, HttpStatus.CREATED);
    }




    // retourner les absences d'un colaborateur
    @GetMapping("{collaborateurEmail}")
    public ResponseEntity<List<AbsenceDto>> getAbsenceBycollaborateurId(@PathVariable("collaborateurEmail")Long collaborateurId) {
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

















