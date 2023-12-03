package miaad.rh.absence.controller;

import lombok.AllArgsConstructor;
import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.entity.Absence;
import miaad.rh.absence.service.AbsenceService;
import miaad.rh.absence.service.impl.AbsenceServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/absences")
public class AbsenceController {
    private AbsenceService absenceService;
    private AbsenceServiceImpl absenceServiceImpl;



    // Créer une absence et faire un test si le colaborateur à dépacer le max des absence on l'envoie un email
    @PostMapping
    public ResponseEntity<AbsenceDto> createAbsence(@ModelAttribute AbsenceDto absenceDto) throws IOException {
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
                                                    @RequestBody AbsenceDto updatedAbsence) throws IOException {
        AbsenceDto absenceDto = absenceService.updateAbsence(absenceId, updatedAbsence);
        return ResponseEntity.ok(absenceDto);
    }

    // Suprimer une absence
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAbsence(@PathVariable("id") Long absenceId){
        absenceService.deleteAbsence(absenceId);
        return ResponseEntity.ok("Absence suprimé avec sucées");
    }

    @GetMapping("/{absenceId}/justification")
    public ResponseEntity<?> downloadJustification(@PathVariable Long absenceId) {
        Optional<Absence> absenceOptional = absenceService.getAbsenceById(absenceId);


        if (absenceOptional.isPresent()) {
            Absence absence = absenceOptional.get();

            byte[] justificationBytes = absence.getJustification();

            if (justificationBytes != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);

                return new ResponseEntity<>(justificationBytes, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return ResponseEntity.ok("Absence not found");
    }
}

















