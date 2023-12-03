package miaad.sgrh.offrestage.controller;

import lombok.AllArgsConstructor;
import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.exception.RessourceNotFoundException;
import miaad.sgrh.offrestage.service.StageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            StageDto stage  = stageService.getStageByTitle(title);
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
}
