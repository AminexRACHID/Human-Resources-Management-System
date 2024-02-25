package com.formations.service_formation.Repositories;

import com.formations.service_formation.Entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FormationRepository extends JpaRepository<Formation, Long> {

    Formation findFormationById(Long id);
    List<Formation> findByCollaborateursContaining(String collaborateur);
    List<Formation> findByNomFormationContainingOrObjectifContaining(String nom, String objectif);

    @Query("SELECT DISTINCT f.collaborateurs FROM Formation f")
    List<String> findAllUniqueCollaborateurs();

    List<Formation> findByCollaborateurs(String collaborateurs);

    @Query("SELECT f FROM Formation f WHERE LOWER(f.nomFormation) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Formation> findByTitleFormation(@Param("searchTerm") String searchTerm);
}