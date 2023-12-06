package miaad.sgrh.offrestage.service;

import miaad.sgrh.offrestage.dto.IntershipApplyStagiaireDto;
import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.dto.Stagiaire;
import miaad.sgrh.offrestage.dto.StagiaireDto;
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

    List<IntershipApplyStagiaireDto> getCandidatesForStage(Long stageId);

    void rejectIntershipApply(Long intershipApplyId);
    void acceptIntershipApply(Long intershipApplyId);
    void acceptIntershipApplyForInterview(Long intershipApplyId);
    void sendEmail(String email, String subj, String message);

}
