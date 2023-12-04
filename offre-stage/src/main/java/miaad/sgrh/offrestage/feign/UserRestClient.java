package miaad.sgrh.offrestage.feign;

import miaad.sgrh.offrestage.dto.StagiaireDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("USER-SERVICE")
public interface UserRestClient {
    @PutMapping("/api/stagiaire/update/{email}")
    ResponseEntity<?> updateStagiaireByEmail(
            @PathVariable("email") String email,
            @RequestParam(value = "cv", required = false) MultipartFile cvFile,
            @ModelAttribute StagiaireDto updatedStagiaireDto);
}
