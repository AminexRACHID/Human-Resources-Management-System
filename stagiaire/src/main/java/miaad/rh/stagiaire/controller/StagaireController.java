package miaad.rh.stagiaire.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import miaad.rh.stagiaire.dto.EmailInfoDto;
import miaad.rh.stagiaire.service.PDFGeneratorService;
import miaad.rh.stagiaire.service.StagaireService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
