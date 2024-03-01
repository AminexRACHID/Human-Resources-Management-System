package miaad.rh.stagiaire.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import miaad.rh.stagiaire.dto.*;
import miaad.rh.stagiaire.entity.Demande;
import miaad.rh.stagiaire.exception.ResourceNotFoundException;
import miaad.rh.stagiaire.feign.OffreFeignClient;
import miaad.rh.stagiaire.feign.StagaireRestClient;
import miaad.rh.stagiaire.mapper.DemandeMapper;
import miaad.rh.stagiaire.repository.DemandeRepository;
import miaad.rh.stagiaire.service.PDFGeneratorService;
import miaad.rh.stagiaire.service.StagaireService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StagaireServiceImpl implements StagaireService {
    private DemandeRepository demandeRepository;
    private JavaMailSender javaMailSender;  // Add this line
    private PDFGeneratorService pdfGeneratorService;
    private StagaireRestClient stagaireRestClient;
    private OffreFeignClient offreFeignClient;

    @Override
    public DemandeDto createDemande(DemandeDto demandeDto) {
        Demande demande = new Demande(
                demandeDto.getId(),
                demandeDto.getIdStage(),
                demandeDto.getEmail()
        );
        Demande savedDemande = demandeRepository.save(demande);
        return DemandeMapper.mapToDemandeDto(savedDemande);
    }

    @Override
    public List<DemandeDto> getAllDemandes() {
        List<Demande> demandes = demandeRepository.findAll();
        return demandes.stream().map((demande -> DemandeMapper.mapToDemandeDto(demande)))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDemande(Long demandeId) {
        Demande demande = demandeRepository.findById(demandeId).orElseThrow(
                () -> new ResourceNotFoundException("Pas de demande avec cette id : " + demandeId)
        );
        demandeRepository.deleteById(demandeId);
    }

    @Override
    public Date getDateFin(Date dateDebut, Long duration) {
        if (dateDebut == null || duration <= 0) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateDebut);
        calendar.add(Calendar.MONTH, Math.toIntExact(duration));

        return calendar.getTime();
    }

    @Override
    public ByteArrayResource generateAttestation(DemandeDto demandeDto) {
        String subject = "attestation de stage";
        String message = "Je vous félicite pour la terminaison de votre stage.";
        String attachmentName = "attestation.pdf";

        System.out.println(demandeDto.getEmail()+"  "+demandeDto.getIdStage());

        OffreDto offreDto = offreFeignClient.getStageById(demandeDto.getIdStage());
        StagiaireDto stagaireDto = stagaireRestClient.getStagiaireByEmails(demandeDto.getEmail());

        String nomStagaire = stagaireDto.getFirstName() + " " + stagaireDto.getLastName();
        Date dateFin = getDateFin((Date) offreDto.getStartDate(), (long) offreDto.getDuration());
        try {
            // Générer le PDF avec les informations de EmailInfoDto
            byte[] attestationPdf = pdfGeneratorService.generateAttestation(
                    nomStagaire,
                    offreDto.getStartDate(),
                    (java.sql.Date) dateFin
            );

            // Créer une ressource ByteArrayResource pour le PDF généré
            return new ByteArrayResource(attestationPdf);
        } catch (Exception e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
            return null; // Ou lancez une exception appropriée selon votre logique
        }
    }

    @Override
    public void sendAttestation(EmailInfoDto emailInfoDto) {
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

    @Override
    public byte[] generateAttestationPdf(DemandeDto demandeDto) {
        StagiaireDto stagaireDto = stagaireRestClient.getStagiaireByEmails(demandeDto.getEmail());
        OffreDto offreDto = offreFeignClient.getStageById(demandeDto.getIdStage());
        String nomStagaire = stagaireDto.getFirstName() + " " + stagaireDto.getLastName();
        Date dateFin = getDateFin((Date) offreDto.getStartDate(), (long) offreDto.getDuration());
        try {
            // Générer le PDF avec les informations de EmailInfoDto
            byte[] attestationPdf = pdfGeneratorService.generateAttestation(
                    nomStagaire,
                    offreDto.getStartDate(),
                    dateFin
            );

            return attestationPdf;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}

