package miaad.sgrh.offrestage.serviceImpl;

import lombok.AllArgsConstructor;
import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.entity.Stage;
import miaad.sgrh.offrestage.exception.RessourceNotFoundException;
import miaad.sgrh.offrestage.mapper.StageMapper;
import miaad.sgrh.offrestage.repository.StageRepository;
import miaad.sgrh.offrestage.service.StageService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StageServiceImpl implements StageService {
    private StageRepository stageRepository;
    @Override
    public StageDto createStage(StageDto stageDto) {
        Stage stage = StageMapper.mapToStage(stageDto);
        return StageMapper.mapToStageDto(stageRepository.save(stage));
    }

    @Override
    public StageDto getStageById(Long id) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Stage not exists with given id: "+ id));

        return StageMapper.mapToStageDto(stage);
    }

    @Override
    public StageDto getStageByTitle(String title) {
        Stage stage = stageRepository.findStagesByTitle(title);
        if (stage == null) {
            throw new RessourceNotFoundException("Stage not exists with given title: " + title);
        }
        return StageMapper.mapToStageDto(stage);
    }

    @Override
    public StageDto updateStage(Long id, StageDto updatedStage) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Stage not exists with given id: "+ id));

        if (updatedStage.getDescription() != null) stage.setDescription(updatedStage.getDescription());
        if (updatedStage.getDuration() != 0) stage.setDuration(updatedStage.getDuration());
        if (updatedStage.getTitle() != null) stage.setTitle(updatedStage.getTitle());
        if (updatedStage.getDiploma() != null) stage.setDiploma(updatedStage.getDiploma());
        if (updatedStage.getType() != null) stage.setType(updatedStage.getType());
        if (updatedStage.getStartDate() != null) stage.setStartDate(updatedStage.getStartDate());
        stage.setRemuneration(updatedStage.isRemuneration());

        return StageMapper.mapToStageDto(stage);
    }




}
