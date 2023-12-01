package miaad.sgrh.offrestage.mapper;

import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.entity.Stage;

public class StageMapper {
    public static StageDto mapToStageDto(Stage stage){
        return new StageDto(
                stage.getId(),
                stage.getTitle(),
                stage.getType(),
                stage.getDuration(),
                stage.getStartDate(),
                stage.isRemuneration(),
                stage.getDiploma(),
                stage.getDescription()
        );
    }

    public static Stage mapToStage(StageDto stageDto){
        return new Stage(
                stageDto.getId(),
                stageDto.getTitle(),
                stageDto.getType(),
                stageDto.getDuration(),
                stageDto.getStartDate(),
                stageDto.isRemuneration(),
                stageDto.getDiploma(),
                stageDto.getDescription()
        );
    }
}
