package miaad.rh.absence.repository;

import miaad.rh.absence.entity.DemandeAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<DemandeAbsence, Long> {

    DemandeAbsence findDemandeAbsenceById(Long id);

    List<DemandeAbsence> findByColaborateurId(Long id);
}
