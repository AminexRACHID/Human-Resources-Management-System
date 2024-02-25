package miaad.sgrh.offrestage.service;

import miaad.sgrh.offrestage.dto.IntershipApplyStagiaireDto;
import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.dto.Stagiaire;
import miaad.sgrh.offrestage.dto.StagiaireDto;
import miaad.sgrh.offrestage.entity.IntershipApply;
import miaad.sgrh.offrestage.entity.Stage;

import java.sql.Date;
import java.util.List;

public interface StageService {
    StageDto createStage(StageDto stageDto);
    StageDto getStageById(Long id);
    List<StageDto> getStageByTitle(String title);

    StageDto updateStage(Long id, StageDto stageDto);

    void deleteStage(Long id);

    List<StageDto> getAllStage();

    StagiaireDto applyIntership(StagiaireDto stagiaireDto);

    List<IntershipApplyStagiaireDto> getCandidatesForStage();

    IntershipApply rejectIntershipApply(Long intershipApplyId);
    IntershipApply acceptIntershipApply(Long intershipApplyId);
    IntershipApply acceptIntershipApplyForInterview(Long intershipApplyId);

    IntershipApply deleteIntershipAccepted(Long intershipApplyId);

    void sendEmail(String email, String subj, String message);

    List<IntershipApply> getIntershipApplies();

    List<IntershipApplyStagiaireDto> findAllPendingIntershipApplies();

    List<IntershipApplyStagiaireDto> findAllEntretienIntershipApplies();

    List<IntershipApplyStagiaireDto> findAllAcceptedIntershipApplies();

    List<IntershipApplyStagiaireDto> findAllRejectedIntershipApplies();

    List<Stage> getCondidateStage(Long stagiaireId);

    void deleteIntershipApply(Long id);
}
