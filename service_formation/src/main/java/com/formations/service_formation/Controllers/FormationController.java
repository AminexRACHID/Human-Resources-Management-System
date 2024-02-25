package com.formations.service_formation.Controllers;

import com.formations.service_formation.Entity.Formation;
import com.formations.service_formation.Services.FormationService;
import com.formations.service_formation.dto.CollaborateursDto;
import com.formations.service_formation.dto.FormationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/miaad/formations")
public class FormationController {
    @Autowired
    private FormationService formationService;

    @GetMapping
    public List<Formation> getAllFormations() {
        return formationService.getAllFormations();
    }

    @GetMapping("/by-collaborateur")
    public List<Formation> getFormationsByCollaborateur(@RequestParam String collaborateur) {
        return formationService.getFormationsByCollaborateur(collaborateur);
    }

    @GetMapping("/formation/{id}")
    public FormationDto getFormationsByid(@PathVariable("id") Long id) {
        return formationService.getFormationsById(id);
    }


    @PostMapping
    public Formation addFormation(@RequestBody Formation formation) {
        return formationService.addFormation(formation);
    }

    @PostMapping("/add-to-plan/{planId}")
    public Formation addFormationToPlan(@RequestBody Formation formation, @PathVariable Long planId) {
        return formationService.addFormationToPlan(formation, planId);
    }

    @GetMapping("/search/{keyword}")
    public List<Formation> searchFormations(@PathVariable("keyword") String keyword) {
        return formationService.searchFormation(keyword);
    }

    @PutMapping("/{id}")
    public Formation updateFormation(@PathVariable Long id, @RequestBody Formation updatedFormation) {
        return formationService.updateFormation(id, updatedFormation);
    }

    @DeleteMapping("/{id}")
    public void deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
    }

    @PutMapping("/{formationId}/update-plan/{newPlanId}")
    public Formation updateFormationPlan(
            @PathVariable Long formationId,
            @PathVariable Long newPlanId
    ) {
        return formationService.updateFormationPlan(formationId, newPlanId);
    }

    @GetMapping("/allformation/collaborateurs")
    public List<CollaborateursDto> getAllFormationByCollaborateurs() {
        return formationService.getAllFormationByCollaborateurs();
    }

}
