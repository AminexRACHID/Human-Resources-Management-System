package miaad.rh.absence.controller;

import lombok.AllArgsConstructor;
import miaad.rh.absence.dto.*;
import miaad.rh.absence.dto.AbsenceDto;
import miaad.rh.absence.dto.DemandeAbsenceDto;
import miaad.rh.absence.entity.Absence;
import miaad.rh.absence.entity.DemandeAbsence;
import miaad.rh.absence.feign.EmployeeRestClient;
import miaad.rh.absence.feign.StagaireRestClient;
import miaad.rh.absence.mapper.AbsenceMapper;
import miaad.rh.absence.repository.AbsenceRepository;
import miaad.rh.absence.repository.DemandeRepository;
import miaad.rh.absence.service.AbsenceService;
import miaad.rh.absence.service.impl.AbsenceServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/absences")
public class AbsenceController {
    private AbsenceService absenceService;
    private AbsenceServiceImpl absenceServiceImpl;
    private EmployeeRestClient employeeRestClient;
    private StagaireRestClient stagaireRestClient;
    private DemandeRepository demandeRepository;
    private AbsenceRepository absenceRepository;



    // Créer une absence et faire un test si le colaborateur à dépacer le max des absence on l'envoie un email
    @PostMapping
    public ResponseEntity<AbsenceDto> createAbsence(@ModelAttribute AbsenceDto absenceDto, @RequestParam("file") MultipartFile file) throws IOException {
        AbsenceDto savedAbsence = absenceService.createAbsence(absenceDto, file);
        if(savedAbsence.isEmployee()){
            Long collaborateurId = absenceDto.getColaborateurId();
            List<AbsenceDto> collaborateurAbsences = absenceService.getAbsenceByEmployeeId(collaborateurId);
            int numberOfAbsencesForCollaborateur = collaborateurAbsences.size();

            if (numberOfAbsencesForCollaborateur >= 3) {
                String message = "Vous avez dépassé le seuil des absences.";
                String subject = "Dépassement du seuil d'absences";
                absenceServiceImpl.sendEmailToCollaborateur(collaborateurId, subject, message, absenceDto.isEmployee());
            }

            return new ResponseEntity<>(savedAbsence, HttpStatus.CREATED);
        } else {
            Long collaborateurId = absenceDto.getColaborateurId();
            List<AbsenceDto> collaborateurAbsences = absenceService.getAbsenceByStagiaireId(collaborateurId);
            int numberOfAbsencesForCollaborateur = collaborateurAbsences.size();

            if (numberOfAbsencesForCollaborateur >= 3) {
                String message = "Vous avez dépassé le seuil des absences.";
                String subject = "Dépassement du seuil d'absences";
                absenceServiceImpl.sendEmailToCollaborateur(collaborateurId, subject, message, absenceDto.isEmployee());
            }

            return new ResponseEntity<>(savedAbsence, HttpStatus.CREATED);
        }
    }



    // retourner les absences d'un colaborateur
    @GetMapping("/employeeAbsence/{id}")
    public ResponseEntity<List<AbsenceDto>> getAbsenceByEmployeeId(@PathVariable("id")Long collaborateurId) {
        List<AbsenceDto> absences = absenceService.getAbsenceByEmployeeId(collaborateurId);
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/stagiaireAbsence/{id}")
    public ResponseEntity<List<AbsenceDto>> getAbsenceByStagiaireId(@PathVariable("id")Long collaborateurId) {
        List<AbsenceDto> absences = absenceService.getAbsenceByStagiaireId(collaborateurId);
        return ResponseEntity.ok(absences);
    }

    // retourner tous les absences
    @GetMapping
    public ResponseEntity<List<AbsenceDto>> getAllAbsences(){
        List<AbsenceDto> absences = absenceService.getAllAbsences();
        return ResponseEntity.ok(absences);
    }


    // retourner tous les absences Justifie
    @GetMapping("/allJustified")
    public List<AbsenceNonJustufiee> getAllJustifierAbsences(){
        List<AbsenceDto> absences = absenceService.getAllAbsencesJustier();
        List<AbsenceNonJustufiee> absenceEmployees = absences.stream()
                .map(absence -> {
                    if (absence.isEmployee()) {
                        EmployeeDto employeeDto = employeeRestClient.getEmployeeById(absence.getColaborateurId());
                        AbsenceNonJustufiee absenceEmployees1 = new AbsenceNonJustufiee();
                        absenceEmployees1.setAbsence(absence);
                        absenceEmployees1.setFirstName(employeeDto.getFirstName());
                        absenceEmployees1.setLastName(employeeDto.getLastName());
                        absenceEmployees1.setEmail(employeeDto.getEmail());
                        return absenceEmployees1;
                    } else {
                        StagaireDto employeeDto = stagaireRestClient.getStagiaireById(absence.getColaborateurId());
                        AbsenceNonJustufiee absenceEmployees1 = new AbsenceNonJustufiee();
                        absenceEmployees1.setAbsence(absence);
                        absenceEmployees1.setFirstName(employeeDto.getFirstName());
                        absenceEmployees1.setLastName(employeeDto.getLastName());
                        absenceEmployees1.setEmail(employeeDto.getEmail());
                        return absenceEmployees1;
                    }
                }).collect(Collectors.toList());

        return absenceEmployees;
    }

    // retourner tous les absences Non Justifie
    @GetMapping("/allNonJustified")
    public List<AbsenceNonJustufiee> getAllNonJustifierAbsences(){
        List<AbsenceDto> absences = absenceService.getAllAbsencesNonJustifier();
        List<AbsenceNonJustufiee> absenceEmployees = absences.stream()
                .map(absence -> {
                    if (absence.isEmployee()) {
                        EmployeeDto employeeDto = employeeRestClient.getEmployeeById(absence.getColaborateurId());
                        AbsenceNonJustufiee absenceEmployees1 = new AbsenceNonJustufiee();
                        absenceEmployees1.setAbsence(absence);
                        absenceEmployees1.setFirstName(employeeDto.getFirstName());
                        absenceEmployees1.setLastName(employeeDto.getLastName());
                        absenceEmployees1.setEmail(employeeDto.getEmail());
                        return absenceEmployees1;
                    } else {
                        StagaireDto employeeDto = stagaireRestClient.getStagiaireById(absence.getColaborateurId());
                        AbsenceNonJustufiee absenceEmployees1 = new AbsenceNonJustufiee();
                        absenceEmployees1.setAbsence(absence);
                        absenceEmployees1.setFirstName(employeeDto.getFirstName());
                        absenceEmployees1.setLastName(employeeDto.getLastName());
                        absenceEmployees1.setEmail(employeeDto.getEmail());
                        return absenceEmployees1;
                    }
                }).collect(Collectors.toList());

        return absenceEmployees;
    }

    // modifier une absence
    @PutMapping("{id}")
    public ResponseEntity<AbsenceDto> updateAbsence(@PathVariable("id") Long absenceId,
                                                    @RequestBody AbsenceDto updatedAbsence) throws IOException {
        AbsenceDto absenceDto = absenceService.updateAbsence(absenceId, updatedAbsence);
        return ResponseEntity.ok(absenceDto);
    }

    // Suprimer une absence
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAbsence(@PathVariable("id") Long absenceId){
        absenceService.deleteAbsence(absenceId);
        return ResponseEntity.ok("Absence suprimé avec sucées");
    }


    @GetMapping("/{absenceId}/justification")
    public ResponseEntity<?> downloadJustification(@PathVariable Long absenceId) {
        Optional<Absence> absenceOptional = absenceService.getAbsenceById(absenceId);

        if (absenceOptional.isPresent()) {
            Absence absence = absenceOptional.get();

            byte[] justificationBytes = absence.getJustification();

            if (justificationBytes != null) {
                HttpHeaders headers = new HttpHeaders();

                MediaType contentType;
                if (isImage(justificationBytes)) {
                    contentType = MediaType.IMAGE_JPEG;
                } else {
                    contentType = MediaType.APPLICATION_PDF;
                }

                headers.setContentType(contentType);

                return new ResponseEntity<>(justificationBytes, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return ResponseEntity.ok("Absence not found");
    }

    @GetMapping("/{absenceId}/demende/justification")
    public ResponseEntity<?> downloadJustificationDemande(@PathVariable Long absenceId) {
        Optional<DemandeAbsence> absenceOptional = demandeRepository.findById(absenceId);

        if (absenceOptional.isPresent()) {
            DemandeAbsence absence = absenceOptional.get();

            byte[] justificationBytes = absence.getJustification();

            if (justificationBytes != null) {
                HttpHeaders headers = new HttpHeaders();

                MediaType contentType;
                if (isImage(justificationBytes)) {
                    contentType = MediaType.IMAGE_JPEG;
                } else {
                    contentType = MediaType.APPLICATION_PDF;
                }

                headers.setContentType(contentType);

                return new ResponseEntity<>(justificationBytes, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return ResponseEntity.ok("Absence not found");
    }

    @GetMapping("/{absenceId}/absence/justification")
    public ResponseEntity<?> downloadJustificationAbsence(@PathVariable Long absenceId) {
        Optional<Absence> absenceOptional = absenceRepository.findById(absenceId);

        if (absenceOptional.isPresent()) {
            Absence absence = absenceOptional.get();

            byte[] justificationBytes = absence.getJustification();

            if (justificationBytes != null) {
                HttpHeaders headers = new HttpHeaders();

                MediaType contentType;
                if (isImage(justificationBytes)) {
                    contentType = MediaType.IMAGE_JPEG;
                } else {
                    contentType = MediaType.APPLICATION_PDF;
                }

                headers.setContentType(contentType);

                return new ResponseEntity<>(justificationBytes, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return ResponseEntity.ok("Absence not found");
    }

    private boolean isImage(byte[] bytes) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
            return bufferedImage != null;
        } catch (IOException e) {
            return false;
        }
    }

    // la fonction pour créer une demande d'absence
    @PostMapping("/demandeAbsence")
    public ResponseEntity<DemandeAbsenceDto> createDemandeAbsence(@ModelAttribute DemandeAbsenceDto demandeAbsenceDto) throws IOException {
        DemandeAbsenceDto demandeAbsence = absenceService.createDemande(demandeAbsenceDto);
        return new ResponseEntity<>(demandeAbsence, HttpStatus.CREATED);
    }

    // methode pour retourner tous les demandes
    @GetMapping("/allDemandes")
    public List<AbsenceEmployee> getAllDemandes(){
        List<DemandeAbsenceDto> absences = absenceService.getAllDemandes();
        List<AbsenceEmployee> absenceEmployees = absences.stream()
                .map(absence -> {
                    EmployeeDto employeeDto = employeeRestClient.getEmployeeById(absence.getColaborateurId());
                    AbsenceEmployee absenceEmployees1 = new AbsenceEmployee();
                    try {
                        absenceEmployees1.setAbsence(AbsenceMapper.mapFromDemandeDtoToAbsence(absence));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    absenceEmployees1.setEmployee(employeeDto);
                    return absenceEmployees1;
                }).collect(Collectors.toList());

        return absenceEmployees;
    }

    // méthode pour suprimer une demande
    @DeleteMapping("/demandes/{id}")
    public ResponseEntity<?> deleteDemande(@PathVariable("id") Long demandeId){
        Map<String, String> responseMap = new HashMap<>();
        try {
            absenceService.deleteDemande(demandeId);

            responseMap.put("message", "Demande d'absence refusée.");

            return ResponseEntity.ok(responseMap);
        } catch(Exception e){
            responseMap.put("message", "Une erreur est survenue. Veuillez réessayer ultérieurement.");
            return ResponseEntity.badRequest().body(responseMap);
        }

    }

    // la méthode pour enregistrer l'absence et suprimer la demande accéptrer
    @PostMapping("/accepterDemande")
    public ResponseEntity<?> accepterDemande(@RequestBody Long id){
        Map<String, String> responseMap = new HashMap<>();
        try {
            absenceService.createAbsenceFromDemande(id);
            absenceService.deleteDemande(id);
            responseMap.put("message", "La demande d'absence a été acceptée.");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e){
            responseMap.put("message", "Une erreur est survenue. Veuillez réessayer ultérieurement.");
            return ResponseEntity.badRequest().body(responseMap);
        }

    }

    @GetMapping("/absenceDoc/{id}")
    public Optional<Absence> getAbsenceDoc(@PathVariable("id")Long id) {
        Optional<Absence> absences = absenceService.getAbsenceById(id);
        return ResponseEntity.ok(absences).getBody();
    }

    // retourner le nombre d'absences justifier et non justifier d'un employee
    @GetMapping("/getAbsencesJustifierNonJustiifer/{collaborateurid}")
    public ResponseEntity<AbsenceJustifierNonJutifierDto> getAbsencesJustifierNonJustifier(@PathVariable("collaborateurid") Long colaborateurId){
        AbsenceJustifierNonJutifierDto nbrs = absenceService.getNobreAbsencesJustifierNonJustifier(colaborateurId);
        return ResponseEntity.ok(nbrs);
    }

    // retourner le nombre d'absences justifier et non justifier d'un stagaire
    @GetMapping("/getAbsencesJustifierNonJustiiferStagaire/{collaborateurid}")
    public ResponseEntity<AbsenceJustifierNonJutifierDto> getAbsencesJustifierNonJustifierStagaire(@PathVariable("collaborateurid") Long colaborateurId){
        AbsenceJustifierNonJutifierDto nbrs = absenceService.getNobreAbsencesJustifierNonJustifierStagaire(colaborateurId);
        return ResponseEntity.ok(nbrs);
    }

}

















