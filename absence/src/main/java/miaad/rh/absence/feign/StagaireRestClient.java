package miaad.rh.absence.feign;

import miaad.rh.absence.dto.StagaireDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface StagaireRestClient {
    @GetMapping("/manage/stagiaire/{id}")
    StagaireDto getStagiaireById(@PathVariable("id") Long stagaireId);
}