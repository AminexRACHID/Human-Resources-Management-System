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
import java.util.ResourceBundle;

@RestController
@RequestMapping("/attachement")
public class AttachementController {
    @Autowired
    private AttachementService attachementService;

    @PostMapping
    public String addAttachement(@RequestParam("attach") MultipartFile attach) throws IOException {
        String id = attachementService.addAttachement(attach.getOriginalFilename(), attach);
        return id;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadAttachement(@PathVariable("id") String id) {
        Attachement attachement = attachementService.getAttachement(id);
        Resource resource = new ByteArrayResource(attachement.getAttachement().getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement ; filname =\"" + attachement.getTitle() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

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