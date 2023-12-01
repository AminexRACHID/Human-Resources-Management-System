package miaad.sgrh.offrestage.repository;

import miaad.sgrh.offrestage.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    Stage findStagesByTitle(String title);
}
