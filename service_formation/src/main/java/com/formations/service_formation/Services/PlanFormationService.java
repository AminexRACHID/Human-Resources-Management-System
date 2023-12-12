package com.formations.service_formation.Services;

import com.formations.service_formation.Entity.PlanFormation;
import com.formations.service_formation.Exception.NotFoundException;
import com.formations.service_formation.Repositories.PlanFormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanFormationService {
    @Autowired
    private PlanFormationRepository planFormationRepository;

    public List<PlanFormation> getAllPlansFormation() {
        return planFormationRepository.findAll();
    }

    public PlanFormation addPlanFormation(PlanFormation planFormation) {
        // Add any additional logic before saving
        return planFormationRepository.save(planFormation);
    }

    public List<PlanFormation> searchPlanFormations(String keyword) {
        return planFormationRepository.findByNomContainingOrResponsableContaining(keyword, keyword);
    }

    public void deletePlanFormation(Long planId) {
        planFormationRepository.deleteById(planId);
    }

    public PlanFormation updatePlanFormation(Long planId, PlanFormation updatedPlanFormation) {
        PlanFormation existingPlanFormation = planFormationRepository.findById(planId)
                .orElseThrow(() -> new NotFoundException("PlanFormation not found with id: " + planId));

        existingPlanFormation.setNom(updatedPlanFormation.getNom());
        existingPlanFormation.setDate(updatedPlanFormation.getDate());
        existingPlanFormation.setResponsable(updatedPlanFormation.getResponsable());
        // Update other properties as needed

        return planFormationRepository.save(existingPlanFormation);
    }
}
