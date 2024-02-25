package com.formations.service_formation.Services;

import com.formations.service_formation.Entity.Formation;
import com.formations.service_formation.Entity.PlanFormation;
import com.formations.service_formation.Exception.NotFoundException;
import com.formations.service_formation.Repositories.FormationRepository;
import com.formations.service_formation.Repositories.PlanFormationRepository;
import com.formations.service_formation.dto.CollaborateursDto;
import com.formations.service_formation.dto.FormationDto;
import com.formations.service_formation.mapper.FormationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class FormationService {
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private PlanFormationRepository planFormationRepository;

    public List<Formation> getFormationsByCollaborateur(String collaborateur) {
        return formationRepository.findByCollaborateursContaining(collaborateur);
    }

    public FormationDto getFormationsById(Long id) {
        Formation formation = formationRepository.findFormationById(id);
        FormationDto formationDto = FormationMapper.mapfromFormationToFormationDto(formation);
        return formationDto;
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

    public List<CollaborateursDto> getAllFormationByCollaborateurs() {

        List<String> collaborateurs = formationRepository.findAllUniqueCollaborateurs();
        List<CollaborateursDto> collaborateursDtos = collaborateurs.stream()
                .map(c -> {
                    List<Formation> formations = formationRepository.findByCollaborateurs(c);
                    CollaborateursDto collaborateursDto = new CollaborateursDto();
                    collaborateursDto.setCollaborateurs(c);
                    collaborateursDto.setFormations(formations);
                    return collaborateursDto;
                }).collect(Collectors.toList());

        return collaborateursDtos;
    }

    public List<Formation> searchFormation(String formation){
        List<Formation> formations = formationRepository.findByTitleFormation(formation);
        return formations;
    }
}
