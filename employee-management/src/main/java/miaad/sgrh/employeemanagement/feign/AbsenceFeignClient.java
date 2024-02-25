package miaad.sgrh.employeemanagement.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ABSENCE-SERVICE")
public interface AbsenceFeignClient {

    @DeleteMapping("/api/absences/deleteByCollaborateurId/{id}")
    ResponseEntity<?> deleteDemandeAbsenceByCollaborateurId(@PathVariable Long id);

    @DeleteMapping("/api/absences/deleteAbsenceByCollaborateurId/{id}")
    ResponseEntity<?> deleteAbsenceByCollaborateurId(@PathVariable Long id);

}