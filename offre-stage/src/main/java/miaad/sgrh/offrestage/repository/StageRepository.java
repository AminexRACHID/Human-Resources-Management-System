package miaad.sgrh.offrestage.repository;

import miaad.sgrh.offrestage.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findStagesByTitle(String title);
}
