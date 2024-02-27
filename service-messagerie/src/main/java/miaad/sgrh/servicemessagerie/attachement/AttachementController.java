package miaad.sgrh.servicemessagerie.attachement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/attachement")
public class AttachementController {
    @Autowired
    private AttachementService attachementService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public @ResponseBody String addAttachement(@RequestParam("attach") MultipartFile attach) throws IOException {
        String id = attachementService.addAttachement(attach.getOriginalFilename(), attach);
        return "{\"id\": \"" + id + "\"}";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadAttachement(@PathVariable("id") String id) {
        Attachement attachement = attachementService.getAttachement(id);
        Resource resource = new ByteArrayResource(attachement.getAttachement().getData());

        // Spécifier le nom du fichier dans le header Content-Disposition
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("adnane", attachement.getTitle());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/name/{id}")
    public String getAttachementName(@PathVariable("id") String id) {
        String name = attachementService.getAttachementName(id);
        return "{\"fileName\": \"" + name + "\"}";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttachement(@PathVariable("id") String id) {
        try {
            attachementService.deleteAttachement(id);
            return ResponseEntity.ok("Attachement supprimé avec succès");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attachement non trouvé avec cet ID : " + id);
        }

    }
}