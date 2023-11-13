package miaad.sgrh.user.repository;

import miaad.sgrh.user.entity.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StagiaireRepository extends JpaRepository<Stagiaire,Long> {
}
