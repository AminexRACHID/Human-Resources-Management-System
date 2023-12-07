package com.formations.service_formation.Repositories;

import com.formations.service_formation.Entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation, Long> {
    List<Formation> findByCollaborateursContaining(String collaborateur);
    List<Formation> findByNomFormationContainingOrObjectifContaining(String nom, String objectif);
}