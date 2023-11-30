package miaad.rh.absence.feign;

import miaad.rh.absence.dto.EmployeeDto;
import miaad.rh.absence.dto.StagaireDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface StagaireRestClient {
    @GetMapping("/api/stagaires/{id}")
    public StagaireDto getStagaireById(@PathVariable("id") Long stagaireId);
}