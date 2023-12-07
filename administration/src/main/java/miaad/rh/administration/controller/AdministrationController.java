package miaad.rh.administration.controller;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import miaad.rh.administration.dto.AttestationInfoDto;
import miaad.rh.administration.service.AttestationGeneratorService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("api/administration")
public class AdministrationController {
    private AttestationGeneratorService attestationGeneratorService;

    @PostMapping(value = "/generateAttestation", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateAttestationPDF(@RequestBody AttestationInfoDto attestationInfoDto) throws IOException, DocumentException {
        byte[] pdfContent = attestationGeneratorService.generateAttestation(attestationInfoDto);

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pdfContent));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attestation.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
