package com.formations.service_formation.Repositories;

import com.formations.service_formation.Entity.PlanFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PlanFormationRepository extends JpaRepository<PlanFormation, Long> {
    List<PlanFormation> findByNomContainingOrResponsableContaining(String nom, String responsable);

    PlanFormation findPlanFormationById(Long id);
}
