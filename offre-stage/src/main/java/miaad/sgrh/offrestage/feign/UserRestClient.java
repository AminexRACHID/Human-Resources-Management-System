package miaad.sgrh.offrestage.feign;

import miaad.sgrh.offrestage.dto.Stagiaire;
import miaad.sgrh.offrestage.dto.StagiaireDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("USER-SERVICE")
public interface UserRestClient {
    @RequestMapping(
            value = "/manage/stagiaire/update/{email}",
            method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseEntity<?> updateStagiaireByEmail(
            @PathVariable("email") String email,
            @RequestParam(value = "cv", required = false) MultipartFile cvFile,
            @ModelAttribute StagiaireDto updatedStagiaireDto
    );

    @GetMapping("/manage/stagiaire/{id}")
    Stagiaire getStagiaireById(@PathVariable("id") Long stagiaireId);
}
