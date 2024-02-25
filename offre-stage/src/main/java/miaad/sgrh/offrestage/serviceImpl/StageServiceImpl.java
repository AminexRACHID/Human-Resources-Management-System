package miaad.sgrh.offrestage.serviceImpl;

import lombok.AllArgsConstructor;
import miaad.sgrh.offrestage.dto.IntershipApplyStagiaireDto;
import miaad.sgrh.offrestage.dto.StageDto;
import miaad.sgrh.offrestage.dto.Stagiaire;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StageServiceImpl implements StageService {
    private StageRepository stageRepository;
    private IntershipApplyRepository intershipApplyRepository;
    private UserRestClient userRestClient;
    private JavaMailSender javaMailSender;

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
        List<Stage> stages = stageRepository.findByTitleContainingIgnoreCase(title);
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
            deleteIntershipApply(stage.getId());
        }
    }

    @Override
    public void deleteIntershipApply(Long id) {
        List<IntershipApply> intershipApplies = intershipApplyRepository.findByStageId(id);
        for (IntershipApply intershipApply : intershipApplies) {
            intershipApplyRepository.delete(intershipApply);
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
            Stagiaire stagiaire = userRestClient.getStagiaireById(intershipApply1.getStagiaireId());
            StageDto stage = getStageById(intershipApply1.getStageId());
            String subj = "Application Received - "+ stage.getTitle();
            String message = "Dear " + stagiaire.getLastName() + " " + stagiaire.getFirstName() + ",\n\n"
                    + "Thank you for applying for the internship position in our company. Your application has been received and is currently under review.\n\n"
                    + "We will contact you shortly to discuss further details, including the possibility of an interview. Please be available for a phone call in the coming days.\n\n"
                    + "If you have any questions or concerns, feel free to reach out.\n\n"
                    + "Best regards,\nMaster IAAD";
            sendEmail(stagiaire.getEmail(), subj, message);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return stagiaireDto;
    }
    @Override
    public List<IntershipApplyStagiaireDto> getCandidatesForStage() {
        List<IntershipApply> applies = intershipApplyRepository.findAll();

        List<IntershipApplyStagiaireDto> candidates = applies.stream()
                .map(intershipApply -> {
                    Stagiaire stagiaire = userRestClient.getStagiaireById(intershipApply.getStagiaireId());
                    Stage stage = stageRepository.findStagesById(intershipApply.getStageId());
                    IntershipApplyStagiaireDto dto = new IntershipApplyStagiaireDto();
                    dto.setIntershipApplyId(intershipApply.getId());
                    dto.setStatus(intershipApply.getStatus());
                    dto.setStagiaire(stagiaire);
                    dto.setStage(stage);
                    return dto;
                })
                .collect(Collectors.toList());

        return candidates;
    }

    @Override
    public IntershipApply acceptIntershipApplyForInterview(Long intershipApplyId) {
        IntershipApply intershipApply = intershipApplyRepository.findById(intershipApplyId)
                .orElseThrow(() -> new RessourceNotFoundException("Intership Apply not found"));
        intershipApply.setStatus("Interview");
        intershipApplyRepository.save(intershipApply);
        Stagiaire stagiaire = userRestClient.getStagiaireById(intershipApply.getStagiaireId());
        StageDto stage = getStageById(intershipApply.getStageId());
        String subj = "Invitation to Interview for Internship - "+ stage.getTitle();
        String message = "Dear " + stagiaire.getLastName() + " " + stagiaire.getFirstName() + ",\n\n"
                + "Congratulations! We are pleased to inform you that your application for the internship in our company has been successful, and we would like to invite you for an interview.\n\n"
                + "We will contact you shortly to schedule a phone call to discuss the interview details, including the date, time, and location. Please be available for a call in the coming days.\n\n"
                + "We look forward to meeting you and discussing your potential contribution to our team.\n\n"
                + "Best regards,\nMaster IAAD";
        sendEmail(stagiaire.getEmail(), subj, message);
        return intershipApply;
    }
    @Override
    public IntershipApply acceptIntershipApply(Long intershipApplyId) {
        IntershipApply intershipApply = intershipApplyRepository.findById(intershipApplyId)
                .orElseThrow(() -> new RessourceNotFoundException("Intership Apply not found"));
        intershipApply.setStatus("Accepted");
        intershipApplyRepository.save(intershipApply);
        Stagiaire stagiaire = userRestClient.getStagiaireById(intershipApply.getStagiaireId());
        StageDto stage = getStageById(intershipApply.getStageId());
        String subj = "Confirmation of Internship Placement";
        String message = "Dear "+stagiaire.getLastName()+" "+stagiaire.getFirstName() + ",\n\n"
                + "I am pleased to inform you that your application for the internship "+ stage.getTitle() +" has been accepted. We look forward to welcoming you to our team.\n\n"
                + "Commencement Date: " + stage.getStartDate() + "\n\n"
                + "If you have any questions or need further information, feel free to contact us.\n\n"
                + "Best Regards,\nMaster IAAD";
        sendEmail(stagiaire.getEmail(), subj, message);
        return intershipApply;
    }
    @Override
    public IntershipApply rejectIntershipApply(Long intershipApplyId) {
        IntershipApply intershipApply = intershipApplyRepository.findById(intershipApplyId)
                .orElseThrow(() -> new RessourceNotFoundException("Intership Apply not found"));
        intershipApply.setStatus("Rejected");
        intershipApplyRepository.save(intershipApply);
        Stagiaire stagiaire = userRestClient.getStagiaireById(intershipApply.getStagiaireId());
        StageDto stage = getStageById(intershipApply.getStageId());
        String subj = "Internship Application Rejection - "+ stage.getTitle();
        String message = "Dear " + stagiaire.getLastName() + " " + stagiaire.getFirstName() + ",\n\n"
                + "We regret to inform you that your application for the internship in our company has been rejected.\n\n"
                + "Thank you for your interest in our organization. We appreciate the time and effort you invested in the application process.\n\n"
                + "If you have any questions or would like feedback on your application, feel free to reach out.\n\n"
                + "Best regards,\nMaster IAAD";
        sendEmail(stagiaire.getEmail(), subj, message);
        return intershipApply;
    }

    @Override
    public IntershipApply deleteIntershipAccepted(Long intershipApplyId) {
        IntershipApply intershipApply = intershipApplyRepository.findById(intershipApplyId)
                .orElseThrow(() -> new RessourceNotFoundException("Intership Apply not found"));
        intershipApply.setStatus("Deleted");
        intershipApplyRepository.save(intershipApply);
        return intershipApply;
    }

    @Override
    public void sendEmail(String email, String subj, String message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subj);
        emailMessage.setText(message);
        javaMailSender.send(emailMessage);
    }

    @Override
    public List<IntershipApply> getIntershipApplies() {
        List<IntershipApply> intershipApplies = intershipApplyRepository.findAll();
        return intershipApplies;
    }

    @Override
    public List<IntershipApplyStagiaireDto> findAllPendingIntershipApplies() {
        List<IntershipApply> applies = intershipApplyRepository.findAllPendingIntershipApplies();

        List<IntershipApplyStagiaireDto> candidates = applies.stream()
                .map(intershipApply -> {
                    Stagiaire stagiaire = userRestClient.getStagiaireById(intershipApply.getStagiaireId());
                    Stage stage = stageRepository.findStagesById(intershipApply.getStageId());
                    IntershipApplyStagiaireDto dto = new IntershipApplyStagiaireDto();
                    dto.setIntershipApplyId(intershipApply.getId());
                    dto.setStatus(intershipApply.getStatus());
                    dto.setStagiaire(stagiaire);
                    dto.setStage(stage);
                    return dto;
                })
                .collect(Collectors.toList());

        return candidates;
    }

    @Override
    public List<IntershipApplyStagiaireDto> findAllEntretienIntershipApplies() {
        List<IntershipApply> applies = intershipApplyRepository.findAllEntretienIntershipApplies();

        List<IntershipApplyStagiaireDto> candidates = applies.stream()
                .map(intershipApply -> {
                    Stagiaire stagiaire = userRestClient.getStagiaireById(intershipApply.getStagiaireId());
                    Stage stage = stageRepository.findStagesById(intershipApply.getStageId());
                    IntershipApplyStagiaireDto dto = new IntershipApplyStagiaireDto();
                    dto.setIntershipApplyId(intershipApply.getId());
                    dto.setStatus(intershipApply.getStatus());
                    dto.setStagiaire(stagiaire);
                    dto.setStage(stage);
                    return dto;
                })
                .collect(Collectors.toList());

        return candidates;
    }

    @Override
    public List<IntershipApplyStagiaireDto> findAllAcceptedIntershipApplies() {
        List<IntershipApply> applies = intershipApplyRepository.findAllAcceptedIntershipApplies();

        List<IntershipApplyStagiaireDto> candidates = applies.stream()
                .map(intershipApply -> {
                    Stagiaire stagiaire = userRestClient.getStagiaireById(intershipApply.getStagiaireId());
                    Stage stage = stageRepository.findStagesById(intershipApply.getStageId());
                    IntershipApplyStagiaireDto dto = new IntershipApplyStagiaireDto();
                    dto.setIntershipApplyId(intershipApply.getId());
                    dto.setStatus(intershipApply.getStatus());
                    dto.setStagiaire(stagiaire);
                    dto.setStage(stage);
                    return dto;
                })
                .collect(Collectors.toList());

        return candidates;
    }

    @Override
    public List<IntershipApplyStagiaireDto> findAllRejectedIntershipApplies() {
        List<IntershipApply> applies = intershipApplyRepository.findAllRejectedIntershipApplies();

        List<IntershipApplyStagiaireDto> candidates = applies.stream()
                .map(intershipApply -> {
                    Stagiaire stagiaire = userRestClient.getStagiaireById(intershipApply.getStagiaireId());
                    Stage stage = stageRepository.findStagesById(intershipApply.getStageId());
                    IntershipApplyStagiaireDto dto = new IntershipApplyStagiaireDto();
                    dto.setIntershipApplyId(intershipApply.getId());
                    dto.setStatus(intershipApply.getStatus());
                    dto.setStagiaire(stagiaire);
                    dto.setStage(stage);
                    return dto;
                })
                .collect(Collectors.toList());

        return candidates;

    }

    @Override
    public List<Stage> getCondidateStage(Long stagiaireId){
        List<IntershipApply> intershipApply = intershipApplyRepository.getCondidateStage(stagiaireId);

        List<Stage> stageDtos = intershipApply.stream()
                .map(intershipApply1 -> {
                    Stage stage = stageRepository.findStagesById(intershipApply1.getStageId());
                    return stage;
                })
                .collect(Collectors.toList());

        return stageDtos;

    }



}
