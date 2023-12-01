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
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id,@RequestBody EmployeeDto updatedEmployeeDto){
        try{
            EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeId, updatedEmployeeDto);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
