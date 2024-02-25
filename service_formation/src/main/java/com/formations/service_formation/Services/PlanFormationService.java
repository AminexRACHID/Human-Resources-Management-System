package com.formations.service_formation.Services;

import com.formations.service_formation.Entity.Formation;
import com.formations.service_formation.Entity.PlanFormation;
import com.formations.service_formation.Exception.NotFoundException;
import com.formations.service_formation.Repositories.FormationRepository;
import com.formations.service_formation.Repositories.PlanFormationRepository;
import com.formations.service_formation.dto.CollaborateursDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlanFormationService {
    @Autowired
    private PlanFormationRepository planFormationRepository;
    private FormationRepository formationRepository;
    private FormationService formationService;
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
        List<Formation> formations = updatedPlanFormation.getFormations();
        try {
            List<Formation> formationss = formations.stream()
                    .map(f -> {
                        if (f.getId() == null){
                            formationService.addFormationToPlan(f, existingPlanFormation.getId());
                        }
                        Formation formation = formationRepository.findFormationById(f.getId());
                        formation.setNomFormation(f.getNomFormation());
                        formation.setDate(f.getDate());
                        formation.setObjectif(f.getObjectif());
                        formation.setCollaborateurs(f.getCollaborateurs());
                        formation.setDuree(f.getDuree());
                        formation.setPlanFormation(existingPlanFormation);
                        formationRepository.save(formation);
                        return formation;
                    }).collect(Collectors.toList());
            existingPlanFormation.setFormations(formationss);
            return planFormationRepository.save(existingPlanFormation);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return planFormationRepository.save(existingPlanFormation);
    }

    public PlanFormation getPlanById(Long id){
        PlanFormation planFormation = planFormationRepository.findPlanFormationById(id);
        return planFormation;
    }

}
