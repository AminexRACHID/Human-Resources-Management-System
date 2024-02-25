package com.formations.service_formation.Controllers;

import com.formations.service_formation.Entity.PlanFormation;
import com.formations.service_formation.Services.PlanFormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/miaad/plans-formation")
public class PlanFormationController {
    @Autowired
    private PlanFormationService planFormationService;

    @GetMapping
    public List<PlanFormation> getAllPlansFormation() {
        return planFormationService.getAllPlansFormation();
    }

    @PostMapping
    public PlanFormation addPlanFormation(@RequestBody PlanFormation planFormation) {
        return planFormationService.addPlanFormation(planFormation);
    }

    @GetMapping("/search")
    public List<PlanFormation> searchPlanFormations(@RequestParam String keyword) {
        return planFormationService.searchPlanFormations(keyword);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<?> deletePlanFormation(@PathVariable Long planId) {
        Map<String, String> responseMap = new HashMap<>();
        try{
            planFormationService.deletePlanFormation(planId);
            responseMap.put("message", "Suppression du programme de formation réalisée.");
            return ResponseEntity.ok(responseMap);

        } catch (Exception e){
            responseMap.put("message", "Une erreur est survenue. Veuillez réessayer ultérieurement.");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }

    @PutMapping("/{planId}")
    public ResponseEntity<PlanFormation> updatePlanFormation(
            @PathVariable Long planId,
            @RequestBody PlanFormation updatedPlanFormation
    ) {
        PlanFormation updatedPlan = planFormationService.updatePlanFormation(planId, updatedPlanFormation);
        return ResponseEntity.ok(updatedPlan);
    }

    @GetMapping("/planFormation/{id}")
    public PlanFormation getPlanFormationsId(@PathVariable("id") Long id) {
        return planFormationService.getPlanById(id);
    }
}