package com.formations.service_formation.Controllers;

import com.formations.service_formation.Entity.PlanFormation;
import com.formations.service_formation.Services.PlanFormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans-formation")
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
    public ResponseEntity<String> deletePlanFormation(@PathVariable Long planId) {
        planFormationService.deletePlanFormation(planId);
        return ResponseEntity.ok("PlanFormation deleted successfully");
    }

    @PutMapping("/{planId}")
    public ResponseEntity<PlanFormation> updatePlanFormation(
            @PathVariable Long planId,
            @RequestBody PlanFormation updatedPlanFormation
    ) {
        PlanFormation updatedPlan = planFormationService.updatePlanFormation(planId, updatedPlanFormation);
        return ResponseEntity.ok(updatedPlan);
    }
}