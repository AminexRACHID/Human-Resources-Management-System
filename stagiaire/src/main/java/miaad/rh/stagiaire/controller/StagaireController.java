package miaad.rh.stagiaire.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import miaad.rh.stagiaire.dto.*;
import miaad.rh.stagiaire.feign.OffreFeignClient;
import miaad.rh.stagiaire.feign.StagaireRestClient;
import miaad.rh.stagiaire.service.PDFGeneratorService;
import miaad.rh.stagiaire.service.StagaireService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/stagaires")
public class StagaireController {
    private StagaireService stagaireService;
    private JavaMailSender javaMailSender;  // Add this line
    private PDFGeneratorService pdfGeneratorService;
    private StagaireRestClient stagaireRestClient;
    private OffreFeignClient offreFeignClient;


    @PostMapping("/sendAttestation")
    public void sendEmailWithAttachment(@RequestBody EmailInfoDto emailInfoDto) {
        stagaireService.sendAttestation(emailInfoDto);
    }

    // Générer attestation et ne pas l'envoyer
    @PostMapping("/genererAttestationSansEnvoyer")
    public ResponseEntity<?> genererAttestationSansEnvoyer(@RequestBody DemandeDto demandeDto){
        try {
            byte[] attestation = stagaireService.generateAttestationPdf(demandeDto);

            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(attestation));


            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "filename; filename=attestation.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

//     la méthode pour envoyer l'attestaion et suprimer la demande pour accepter
@PostMapping("/sendAttestationAndDeleteDemande")
public ResponseEntity<?> sendAttestationAndDeleteDemande(@RequestBody DemandeDto demandeDto) {
    Map<String, String> responseMap = new HashMap<>();

    try {
        StagiaireDto stagaireDto = stagaireRestClient.getStagiaireByEmails(demandeDto.getEmail());
        String fullName = stagaireDto.getFirstName()+ " " +stagaireDto.getLastName();
        OffreDto offreDto = offreFeignClient.getStageById(demandeDto.getIdStage());
        Date dateF = stagaireService.getDateFin(offreDto.getStartDate(), (long) offreDto.getDuration());

        // Convertir la dateF en LocalDate pour la comparaison
        LocalDate localDateF = dateF.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();



        // Récupérer la date actuelle
        LocalDate currentDate = LocalDate.now();

        System.out.println("currentDate :"+currentDate);
        System.out.println("datefin :"+localDateF);

        // Vérifier si la dateF est postérieure à la date actuelle
        if (!localDateF.isAfter(currentDate)) {
            EmailInfoDto emailInfoDto = new EmailInfoDto(fullName, offreDto.getStartDate(), dateF, demandeDto.getEmail());
            sendEmailWithAttachment(emailInfoDto);
//            stagaireService.deleteDemande(demandeDto.getId());

            responseMap.put("message", "Attestation envoyer par e-mail !");

            return ResponseEntity.ok(responseMap);
        } else {
            responseMap.put("message", "La date de fin n'est pas postérieure à la date actuelle.");
            return ResponseEntity.badRequest().body(responseMap);
        }
    } catch (Exception e){
        throw new RuntimeException(e.getMessage());
    }
}

    // la méthode pour créer une demande
    @PostMapping("/createDemande")
    public ResponseEntity<?> createDemande(@RequestBody DemandeDto demandeDto) {
        try{
            DemandeDto demande = stagaireService.createDemande(demandeDto);
            return new ResponseEntity<>(demande, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // la méthode pour suprimer une demande pour refuser
    @DeleteMapping("/deleteDemande/{id}")
    public ResponseEntity<?> deleteDemande(@PathVariable("id") Long id) {
        try {
            stagaireService.deleteDemande(id);
            return ResponseEntity.ok("Demande deleted successfully !");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // la méthode pour donner tout les demandes
    @GetMapping("/getAllDemandes")
    public ResponseEntity<?> getAllDemandes() {
        try {
            List<DemandeDto> demande = stagaireService.getAllDemandes();
            return new ResponseEntity<>(demande, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
