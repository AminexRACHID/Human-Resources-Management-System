package com.formations.service_formation.Services;

import com.formations.service_formation.Entity.Formation;
import com.formations.service_formation.Entity.PlanFormation;
import com.formations.service_formation.Exception.NotFoundException;
import com.formations.service_formation.Repositories.FormationRepository;
import com.formations.service_formation.Repositories.PlanFormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class FormationService {
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private PlanFormationRepository planFormationRepository;

    public List<Formation> getFormationsByCollaborateur(String collaborateur) {
        return formationRepository.findByCollaborateursContaining(collaborateur);
    }

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Formation addFormation(Formation formation) {
        // Add any additional logic before saving
        return formationRepository.save(formation);
    }
    public Formation addFormationToPlan(Formation formation, Long planId) {
        PlanFormation planFormation = planFormationRepository.findById(planId)
                .orElseThrow(() -> new NotFoundException("PlanFormation not found with id: " + planId));

        formation.setPlanFormation(planFormation);
        return formationRepository.save(formation);
    }
    public List<Formation> searchFormations(String keyword) {
        return formationRepository.findByNomFormationContainingOrObjectifContaining(keyword, keyword);
    }

    public Formation updateFormation(Long id, Formation updatedFormation) {
        Formation existingFormation = formationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Formation not found with id: " + id));

        // Update the fields you want to allow for modification
        existingFormation.setNomFormation(updatedFormation.getNomFormation());
        existingFormation.setObjectif(updatedFormation.getObjectif());
        existingFormation.setCollaborateurs(updatedFormation.getCollaborateurs());
        existingFormation.setDuree(updatedFormation.getDuree());
        existingFormation.setDate(updatedFormation.getDate());

        // Save the updated Formation
        return formationRepository.save(existingFormation);
    }
    public void deleteFormation(Long id) {
        Formation existingFormation = formationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Formation not found with id: " + id));

        // Delete the Formation
        formationRepository.delete(existingFormation);
    }

    public Formation updateFormationPlan(Long formationId, Long newPlanId) {
        Formation existingFormation = formationRepository.findById(formationId)
                .orElseThrow(() -> new NotFoundException("Formation not found with id: " + formationId));

        PlanFormation newPlanFormation = planFormationRepository.findById(newPlanId)
                .orElseThrow(() -> new NotFoundException("PlanFormation not found with id: " + newPlanId));

        existingFormation.setPlanFormation(newPlanFormation);

        return formationRepository.save(existingFormation);
    }
}
