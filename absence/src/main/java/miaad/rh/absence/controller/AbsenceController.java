package miaad.rh.absence.controller;

import lombok.AllArgsConstructor;
import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.entity.Absence;
import miaad.rh.absence.service.AbsenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/absences")
public class AbsenceController {
    private AbsenceService absenceService;

    @PostMapping
    public ResponseEntity<AbsenceDto> createAbsence(@RequestBody AbsenceDto absenceDto){
        AbsenceDto savedAbsence = absenceService.createAbsence(absenceDto);
        return new ResponseEntity<>(savedAbsence, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AbsenceDto> getAbsenceById(@PathVariable("id") Long absenceId){
        AbsenceDto absenceDto = absenceService.getAbsencebyId(absenceId);
        return ResponseEntity.ok(absenceDto);
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

















