package miaad.sgrh.offrestage.repository;

import miaad.sgrh.offrestage.entity.IntershipApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntershipApplyRepository extends JpaRepository<IntershipApply, Long> {

    @Query("SELECT i FROM IntershipApply i WHERE i.stageId = :stageId AND i.stagiaireId = :stagiaireId")
    IntershipApply getIntershipApplies(@Param("stageId") Long stageId,@Param("stagiaireId") Long stagiaireId);

    List<IntershipApply> findIntershipAppliesByStageId(Long stageId);

    @Query("SELECT ia FROM IntershipApply ia WHERE ia.status = 'Pending'")
    List<IntershipApply> findAllPendingIntershipApplies();

    @Query("SELECT ia FROM IntershipApply ia WHERE ia.status = 'Interview'")
    List<IntershipApply> findAllEntretienIntershipApplies();

    @Query("SELECT ia FROM IntershipApply ia WHERE ia.status = 'Accepted'")
    List<IntershipApply> findAllAcceptedIntershipApplies();

    @Query("SELECT ia FROM IntershipApply ia WHERE ia.status = 'Accepted' AND ia.stagiaireId = :stagiaireId")
    List<IntershipApply> getCondidateStage(@Param("stagiaireId") Long stagiaireId);
    @Query("SELECT ia FROM IntershipApply ia WHERE ia.status = 'Rejected'")
    List<IntershipApply> findAllRejectedIntershipApplies();
}
