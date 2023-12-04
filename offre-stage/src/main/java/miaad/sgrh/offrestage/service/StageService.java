package miaad.sgrh.offrestage.service;

import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.dto.StagiaireDto;
import miaad.sgrh.offrestage.entity.Stage;

import java.util.List;

public interface StageService {
    StageDto createStage(StageDto stageDto);
    StageDto getStageById(Long id);
    List<StageDto> getStageByTitle(String title);

    StageDto updateStage(Long id, StageDto stageDto);

    void deleteStage(Long id);

    List<StageDto> getAllStage();

    StagiaireDto applyIntership(StagiaireDto stagiaireDto);

}
