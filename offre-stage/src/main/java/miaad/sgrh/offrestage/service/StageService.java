package miaad.sgrh.offrestage.service;

import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.entity.Stage;

public interface StageService {
    StageDto createStage(StageDto stageDto);
    StageDto getStageById(Long id);
    StageDto getStageByTitle(String title);

    StageDto updateStage(Long id, StageDto stageDto);



}
