package miaad.sgrh.offrestage.controller;

import lombok.AllArgsConstructor;
import miaad.sgrh.offrestage.dto.IntershipApplyStagiaireDto;
import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.dto.StagiaireDto;
import miaad.sgrh.offrestage.exception.RessourceNotFoundException;
import miaad.sgrh.offrestage.service.StageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stage")
@AllArgsConstructor
public class StageController {
    private StageService stageService;

    @PostMapping
    public ResponseEntity<?> createStage(@RequestBody StageDto stageDto) {
        try {
            StageDto stage  = stageService.createStage(stageDto);
            return new ResponseEntity<>(stage, HttpStatus.OK);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getStageById(@RequestParam("id") Long id){
        try {
            StageDto stage  = stageService.getStageById(id);
            return new ResponseEntity<>(stage, HttpStatus.OK);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stage/{title}")
    public ResponseEntity<?> getStageByTitle(@RequestParam("title") String title){
        try {
            List<StageDto> stage  = stageService.getStageByTitle(title);
            return new ResponseEntity<>(stage, HttpStatus.OK);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateStage(@PathVariable("id") Long id, @RequestBody StageDto updatedStageDto){
        try{
            StageDto updatedStage = stageService.updateStage(id, updatedStageDto);
            return ResponseEntity.ok(updatedStage);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStage(@PathVariable("id") Long id){
        try{
            stageService.deleteStage(id);
            return ResponseEntity.ok("Stage deleted successfully.");
        } catch (RessourceNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStage(){
        try {
            List<StageDto> stage  = stageService.getAllStage();
            return new ResponseEntity<>(stage, HttpStatus.OK);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/applyIntership")
    public ResponseEntity<?> applyIntership(@ModelAttribute StagiaireDto stagiaireDto) {
        try {
            stageService.applyIntership(stagiaireDto);
            return ResponseEntity.ok("Applied to this internship successfully.");
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stagiaire/{id}")
    public ResponseEntity<?> getCandidatesForStage(@PathVariable Long id){
        try {
            List<IntershipApplyStagiaireDto>  stagiaires  = stageService.getCandidatesForStage(id);
            return new ResponseEntity<>(stagiaires, HttpStatus.OK);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/interview/{intershipApplyId}")
    public ResponseEntity<?> acceptIntershipApplyForInterview(@PathVariable Long intershipApplyId) {
        stageService.acceptIntershipApplyForInterview(intershipApplyId);
        return ResponseEntity.ok("Intership apply set to interview successfully");
    }

    @PutMapping("/accept/{intershipApplyId}")
    public ResponseEntity<?> acceptIntershipApply(@PathVariable Long intershipApplyId) {
        stageService.acceptIntershipApply(intershipApplyId);
        return ResponseEntity.ok("Intership apply accepted successfully");
    }

    @PutMapping("/reject/{intershipApplyId}")
    public ResponseEntity<?> rejectIntershipApply(@PathVariable Long intershipApplyId) {
        stageService.rejectIntershipApply(intershipApplyId);
        return ResponseEntity.ok("Intership apply rejected successfully");
    }
}
