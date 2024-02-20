package miaad.rh.stagiaire.repository;

import miaad.rh.stagiaire.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
}
