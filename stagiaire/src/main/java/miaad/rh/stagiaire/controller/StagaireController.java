package miaad.rh.stagiaire.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import miaad.rh.stagiaire.dto.DemandeDto;
import miaad.rh.stagiaire.dto.EmailInfoDto;
import miaad.rh.stagiaire.service.PDFGeneratorService;
import miaad.rh.stagiaire.service.StagaireService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/stagaires")
public class StagaireController {
    private StagaireService stagaireService;
    private JavaMailSender javaMailSender;  // Add this line
    private PDFGeneratorService pdfGeneratorService;

    @PostMapping("/sendAttestation")
    public void sendEmailWithAttachment(@RequestBody EmailInfoDto emailInfoDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String subject = "attestation de stage";
        String message = "Je vous félicite pour la terminaison de votre stage.";
        String attachmentName = "attestation.pdf";

        try {
            // Générer le PDF avec les informations de EmailInfoDto
            byte[] attestationPdf = pdfGeneratorService.generateAttestation(
                    emailInfoDto.getNomStagaire(),
                    emailInfoDto.getDateDebut(),
                    emailInfoDto.getDateFin()
            );

            // Préparer le message MIME
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(emailInfoDto.getEmail());
            helper.setSubject(subject);
            helper.setText(message);

            // Ajouter le PDF en pièce jointe
            ByteArrayResource pdfAttachment = new ByteArrayResource(attestationPdf);
            helper.addAttachment(attachmentName, pdfAttachment);

            // Envoyer l'email
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        } catch (Exception e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }

    // la méthode pour envoyer l'attestaion et suprimer la demande pour accepter
    @PostMapping("/sendAttestationAndDeleteDemande")
    public ResponseEntity<?> sendAttestationAndDeleteDemande(@RequestBody DemandeDto demandeDto) {
        try {
            EmailInfoDto emailInfoDto = new EmailInfoDto(demandeDto.getNomStagaire(), demandeDto.getDateDebut(), demandeDto.getDateFin(), demandeDto.getEmail());
            sendEmailWithAttachment(emailInfoDto);
            stagaireService.deleteDemande(demandeDto.getId());
            return ResponseEntity.ok("Demande deleted successfully !");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
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
