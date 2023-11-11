package miaad.rh.stagiaire.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import miaad.rh.stagiaire.dto.StagaireDto;
import miaad.rh.stagiaire.service.StagaireService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/stagaires")
public class StagaireController {
    private StagaireService stagaireService;

    @PostMapping
    public ResponseEntity<StagaireDto> createStagaire(@RequestBody StagaireDto stagaireDto) {
        StagaireDto savedStagaire = stagaireService.createStagaire(stagaireDto);
        return new ResponseEntity<>(savedStagaire, HttpStatus.CREATED);
    }

    @GetMapping("{stagaireId}")
    public ResponseEntity<StagaireDto> getStagaireById(@PathVariable("stagaireId")Long stagaireId) {
        StagaireDto stagaireDto = stagaireService.getStagaireById(stagaireId);
        return ResponseEntity.ok(stagaireDto);
    }

    @GetMapping
    public ResponseEntity<List<StagaireDto>> getAllStagaires() {
        List<StagaireDto> stagaires = stagaireService.getAllStagaires();
        return ResponseEntity.ok(stagaires);
    }

    @PutMapping("{id}")
    public ResponseEntity<StagaireDto> updateStagaire(@PathVariable("id") Long stagaireId,
                                                      @RequestBody StagaireDto updatedStagaire) {
        StagaireDto stagaireDto = stagaireService.updateStagaire(stagaireId, updatedStagaire);
        return ResponseEntity.ok(stagaireDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStagaire(@PathVariable("id") Long stagaireId){
        stagaireService.deleteStagaire(stagaireId);
        return ResponseEntity.ok("Stagaire suprimé avec succés");
    }
}
