package miaad.rh.stagiaire.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import miaad.rh.stagiaire.service.PDFGeneratorService;
import miaad.rh.stagiaire.service.StagaireService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/api/stagaires")
public class StagaireController {
    private StagaireService stagaireService;
    private JavaMailSender javaMailSender;  // Add this line
    private PDFGeneratorService pdfGeneratorService;


    private void sendEmailAcceptation(String email, Date date) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject("acceptation du stage");
        emailMessage.setText("Bonjour, \nJe tiens à vous informer que vous avez été accepté pour commencer votre stage dans le département informatique. \nLa date de début est le "+date);
        javaMailSender.send(emailMessage);
    }

    @GetMapping("/sendAttestation/{email}")
    private void sendEmailWithAttachment(@PathVariable("email") String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String subject = "attestation de stage";
        String message = "Je vous félicite pour la terminaison de votre stage.";
        String attachmentName = "attestation.pdf";

        try {
            // Générer le PDF
            byte[] attestationPdf = pdfGeneratorService.generateAttestation("TOUZOUZ Adnane", Date.valueOf("2023-12-09"), Date.valueOf("2024-12-09"));

            // Préparer le message MIME
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
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

}
