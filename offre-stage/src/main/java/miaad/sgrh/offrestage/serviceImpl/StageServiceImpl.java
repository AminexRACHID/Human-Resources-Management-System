package miaad.sgrh.offrestage.serviceImpl;

import lombok.AllArgsConstructor;
import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.dto.StagiaireDto;
import miaad.sgrh.offrestage.entity.IntershipApply;
import miaad.sgrh.offrestage.entity.Stage;
import miaad.sgrh.offrestage.exception.RessourceNotFoundException;
import miaad.sgrh.offrestage.feign.UserRestClient;
import miaad.sgrh.offrestage.mapper.StageMapper;
import miaad.sgrh.offrestage.repository.IntershipApplyRepository;
import miaad.sgrh.offrestage.repository.StageRepository;
import miaad.sgrh.offrestage.service.StageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StageServiceImpl implements StageService {
    private StageRepository stageRepository;
    private IntershipApplyRepository intershipApplyRepository;
    private UserRestClient userRestClient;

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
    public List<StageDto> getStageByTitle(String title) {
        List<Stage> stages = stageRepository.findStagesByTitle(title);
        if (stages == null) {
            throw new RessourceNotFoundException("Stage not exists with given title: " + title);
        }

        return stages.stream().map((stage) -> StageMapper.mapToStageDto(stage))
                .collect(Collectors.toList());
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

        return StageMapper.mapToStageDto(stageRepository.save(stage));
    }

    @Override
    public void deleteStage(Long id) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Stage not found with given id"+ id));
        if (stage != null){
            stageRepository.deleteById(id);
        }
    }

    @Override
    public List<StageDto> getAllStage() {
        List<Stage> stages = stageRepository.findAll();
        if (stages == null) {
            throw new RessourceNotFoundException("No stages at the moment");
        }

        return stages.stream().map((stage) -> StageMapper.mapToStageDto(stage))
                .collect(Collectors.toList());
    }

    @Override
    public StagiaireDto applyIntership(StagiaireDto stagiaireDto) {
        IntershipApply intershipApply = intershipApplyRepository.getIntershipApplies(stagiaireDto.getStageId(), stagiaireDto.getId());

        if (intershipApply != null){
            throw new RessourceNotFoundException("You have already apply to this intership");
        }
        try {
            ResponseEntity<?> ok = userRestClient.updateStagiaireByEmail(stagiaireDto.getEmail(), stagiaireDto.getCv(), stagiaireDto);
            IntershipApply intershipApply1 = new IntershipApply();
            intershipApply1.setStageId(stagiaireDto.getStageId());
            intershipApply1.setStagiaireId(stagiaireDto.getId());
            intershipApply1.setStatus("Pending");
            intershipApplyRepository.save(intershipApply1);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return stagiaireDto;
    }

}
