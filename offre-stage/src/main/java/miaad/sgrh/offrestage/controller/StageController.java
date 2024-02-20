package miaad.sgrh.offrestage.controller;

import lombok.AllArgsConstructor;
import miaad.sgrh.offrestage.dto.IntershipApplyStagiaireDto;
import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.dto.StagiaireDto;
import miaad.sgrh.offrestage.entity.IntershipApply;
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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<?> getStageById(@PathVariable("id") Long id){
        try {
            StageDto stage  = stageService.getStageById(id);
            return new ResponseEntity<>(stage, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/offers/title/{title}")
    public ResponseEntity<?> getStageByTitle(@PathVariable("title") String title){
        try {
            List<StageDto> stage  = stageService.getStageByTitle(title);
            return new ResponseEntity<>(stage, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateStage(@PathVariable("id") Long id, @RequestBody StageDto updatedStageDto){
        try{
            StageDto updatedStage = stageService.updateStage(id, updatedStageDto);
            return ResponseEntity.ok(updatedStage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStage(@PathVariable("id") Long id){
        try{
            stageService.deleteStage(id);
            return ResponseEntity.ok("Stage deleted successfully.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/intershipApplies")
    public ResponseEntity<?> getIntershipApplies(){
        try {
            List<IntershipApply> intershipApplies  = stageService.getIntershipApplies();
            return new ResponseEntity<>(intershipApplies, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/offers")
    public ResponseEntity<?> getAllStage(){
        try {
            List<StageDto> stage  = stageService.getAllStage();
            return new ResponseEntity<>(stage, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/applyIntership")
    public ResponseEntity<?> applyIntership(@ModelAttribute StagiaireDto stagiaireDto) {
        try {
            stageService.applyIntership(stagiaireDto);
            return ResponseEntity.ok("Applied to this internship successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stagiaire")
    public ResponseEntity<?> getCandidatesForStage(){
        try {
            List<IntershipApplyStagiaireDto>  stagiaires  = stageService.getCandidatesForStage();
            return new ResponseEntity<>(stagiaires, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stagiaire/pending")
    public ResponseEntity<?> getCandidatesForStagePending(){
        try {
            List<IntershipApplyStagiaireDto>  stagiaires  = stageService.findAllPendingIntershipApplies();
            return new ResponseEntity<>(stagiaires, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stagiaire/entretien")
    public ResponseEntity<?> getCandidatesForStageEntretien(){
        try {
            List<IntershipApplyStagiaireDto>  stagiaires  = stageService.findAllEntretienIntershipApplies();
            return new ResponseEntity<>(stagiaires, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stagiaire/accepted")
    public ResponseEntity<?> getCandidatesForStageAccepted(){
        try {
            List<IntershipApplyStagiaireDto>  stagiaires  = stageService.findAllAcceptedIntershipApplies();
            return new ResponseEntity<>(stagiaires, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stagiaire/rejected")
    public ResponseEntity<?> getCandidatesForStageRejected(){
        try {
            List<IntershipApplyStagiaireDto>  stagiaires  = stageService.findAllRejectedIntershipApplies();
            return new ResponseEntity<>(stagiaires, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/interview/{intershipApplyId}")
    public ResponseEntity<?> acceptIntershipApplyForInterview(@PathVariable Long intershipApplyId) {
        IntershipApply intershipApply = stageService.acceptIntershipApplyForInterview(intershipApplyId);
        return new ResponseEntity<>(intershipApply, HttpStatus.OK);
    }

    @PutMapping("/accept/{intershipApplyId}")
    public ResponseEntity<?> acceptIntershipApply(@PathVariable Long intershipApplyId) {
        IntershipApply intershipApply = stageService.acceptIntershipApply(intershipApplyId);
        return new ResponseEntity<>(intershipApply, HttpStatus.OK);
    }

    @PutMapping("/reject/{intershipApplyId}")
    public ResponseEntity<?> rejectIntershipApply(@PathVariable Long intershipApplyId) {
        IntershipApply intershipApply = stageService.rejectIntershipApply(intershipApplyId);
        return new ResponseEntity<>(intershipApply, HttpStatus.OK);
    }

    @PutMapping("/delete/{intershipApplyId}")
    public ResponseEntity<?> deleteInterAccepted(@PathVariable Long intershipApplyId) {
        IntershipApply intershipApply = stageService.deleteIntershipAccepted(intershipApplyId);
        return new ResponseEntity<>(intershipApply, HttpStatus.OK);
    }
}
