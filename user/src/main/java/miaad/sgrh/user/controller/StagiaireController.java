package miaad.sgrh.user.controller;

import lombok.AllArgsConstructor;
import miaad.sgrh.user.dto.StagiaireDto;
import miaad.sgrh.user.exception.RessourceNotFoundException;
import miaad.sgrh.user.service.StagiaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/stagiaire")
public class StagiaireController {

    StagiaireService stagiaireService;

    @GetMapping("{id}")
    public ResponseEntity<?> getStagiaireById(@PathVariable("id") Long stagiaireId){
        try{
            StagiaireDto stagiaireDto = stagiaireService.getStagiaireInfoById(stagiaireId);
            return ResponseEntity.ok(stagiaireDto);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<StagiaireDto>> getAllStagiaires(){
        List<StagiaireDto> employees = stagiaireService.getAllStagiaires();
        return ResponseEntity.ok(employees);
    }

    //  GET http://localhost:8081/api/stagiaire/search/name?lastName=rachi&firstName=Amin
    @GetMapping("/search/name")
    public ResponseEntity<?> getStagiaireByLastNameAndFirstName(
            @RequestParam("lastName") String lastName,
            @RequestParam("firstName") String firstName){
        try{
            List<StagiaireDto> stagiaireDto = stagiaireService.getStagiaireByFirstAndLastName(lastName, firstName);
            return ResponseEntity.ok(stagiaireDto);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<?> getStagiaireByEmail(@PathVariable("email") String email){
        try{
            StagiaireDto stagiaireDto = stagiaireService.getStagiaireInfoByEmail(email);
            return ResponseEntity.ok(stagiaireDto);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("/search/status/{status}")
//    public ResponseEntity<?> getStagiaireByStatus(@PathVariable("status") String status){
//        try{
//            List<StagiaireDto> stagiaireDto = stagiaireService.getStagiaireByStatus(status);
//            return ResponseEntity.ok(stagiaireDto);
//        } catch (RessourceNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStagiaire(@PathVariable("id") Long id){
        try{
            stagiaireService.deleteStagiaires(id);
            return ResponseEntity.ok("Stagiaire deleted successfully.");
        } catch (RessourceNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateStagiaire(
            @PathVariable("id") Long id,
            @RequestPart(value = "cv", required = false) MultipartFile cvFile,
            @ModelAttribute StagiaireDto updatedStagiaireDto) {
        try {
            updatedStagiaireDto.setCv(cvFile);
            StagiaireDto updatedStagiaire = stagiaireService.updateStagiaire(id, updatedStagiaireDto, cvFile);
            return ResponseEntity.ok(updatedStagiaire);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<?> updateStagiaireByEmail(
            @PathVariable("email") String email,
            @RequestParam(value = "cv", required = false) MultipartFile cvFile,
            @ModelAttribute StagiaireDto updatedStagiaireDto) {
        try {
            updatedStagiaireDto.setCv(cvFile);
            StagiaireDto stagiaire = stagiaireService.getStagiaireInfoByEmail(email);
            StagiaireDto updatedStagiaire = stagiaireService.updateStagiaire(stagiaire.getId(), updatedStagiaireDto, cvFile);
            return ResponseEntity.ok(updatedStagiaire);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

