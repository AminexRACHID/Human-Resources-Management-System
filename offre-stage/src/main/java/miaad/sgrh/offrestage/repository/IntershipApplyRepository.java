package miaad.sgrh.offrestage.repository;

import miaad.sgrh.offrestage.entity.IntershipApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntershipApplyRepository extends JpaRepository<IntershipApply, Long> {

    @Query("SELECT i FROM IntershipApply i WHERE i.stageId = :stageId AND i.stagiaireId = :stagiaireId")
    IntershipApply getIntershipApplies(Long stageId, Long stagiaireId);

    List<IntershipApply> findIntershipAppliesByStageId(Long stageId);
}
