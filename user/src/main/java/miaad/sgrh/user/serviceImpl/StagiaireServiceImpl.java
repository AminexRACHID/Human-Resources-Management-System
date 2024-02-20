package miaad.sgrh.user.serviceImpl;

import lombok.AllArgsConstructor;
import miaad.sgrh.user.dto.StagiaireDto;
import miaad.sgrh.user.entity.Stagiaire;
import miaad.sgrh.user.entity.User;
import miaad.sgrh.user.exception.RessourceNotFoundException;
import miaad.sgrh.user.feign.AccountRestClient;
import miaad.sgrh.user.mapper.StagiaireMapper;
import miaad.sgrh.user.repository.StagiaireRepository;
import miaad.sgrh.user.repository.UserRepository;
import miaad.sgrh.user.service.StagiaireService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StagiaireServiceImpl implements StagiaireService {
    private StagiaireRepository stagiaireRepository;
    private UserRepository userRepository;
    private AccountRestClient accountRestClient;

    @Override
    public StagiaireDto getStagiaireInfoById(Long id) {
        Stagiaire stagiaire = stagiaireRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Stagiaire not exists with given id: "+ id));
        return StagiaireMapper.mapToStagiaireDto(stagiaire);
    }

    @Override
    public StagiaireDto getStagiaireInfoByEmail(String email) {
        Stagiaire stagiaire = stagiaireRepository.findStagiaireByEmail(email);

        if (stagiaire == null) {
            throw new RessourceNotFoundException("Stagiaire not exists with given email: " + email);
        }
        return StagiaireMapper.mapToStagiaireDto(stagiaire);
    }

    @Override
    public List<StagiaireDto> getStagiaireByFirstAndLastName(String lastName, String firstName) {
        List<User> users = userRepository.getUserByLastNameAndFirstName(lastName, firstName);
        List<Stagiaire> stagiaires = new ArrayList<>();

        if (!users.isEmpty()) {
            for (User user : users) {
                Stagiaire stagiaire = stagiaireRepository.findStagiaireByEmail(user.getEmail());
                if (stagiaire != null) {
                    stagiaires.add(stagiaire);
                }
            }
        } else {
            throw new RessourceNotFoundException("Stagiaire not exists with given Name: " + lastName + " " + firstName);
        }
        return stagiaires.stream().map((s) -> StagiaireMapper.mapToStagiaireDto(s))
                .collect(Collectors.toList());
    }

    @Override
    public List<StagiaireDto> getAllStagiaires() {
        List<Stagiaire> stagiaires = stagiaireRepository.findAll();
        return stagiaires.stream().map((s) -> StagiaireMapper.mapToStagiaireDto(s))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<StagiaireDto> getStagiaireByStatus(String status) {
//        List<Stagiaire> stagiaireDtos = stagiaireRepository.getStagiaireByStatus(status);
//        return stagiaireDtos.stream().map((s) -> StagiaireMapper.mapToStagiaireDto(s))
//                .collect(Collectors.toList());
//    }

    @Override
    public void deleteStagiaires(Long id) {
        Stagiaire stagiaire = stagiaireRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Stagiaire not exists with given id: "+ id));

        String email = stagiaire.getEmail();

        try{
            stagiaireRepository.deleteById(id);
            accountRestClient.deleteAccount(email);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public StagiaireDto updateStagiaire(Long id, StagiaireDto updatedStagiaire, MultipartFile cvFile) {

        Stagiaire updatedStagiaireObj = null;
        try {
            Stagiaire stagiaire = stagiaireRepository.findById(id)
                    .orElseThrow(() -> new RessourceNotFoundException("Stagiaire not exists with given id: " + id));

            if (updatedStagiaire.getFirstName() != null) stagiaire.setFirstName(updatedStagiaire.getFirstName());
            if (updatedStagiaire.getLastName() != null) stagiaire.setLastName(updatedStagiaire.getLastName());
            if (updatedStagiaire.getCity() != null) stagiaire.setCity(updatedStagiaire.getCity());
            if (updatedStagiaire.getLevelStudies() != null)
                stagiaire.setLevelStudies(updatedStagiaire.getLevelStudies());
            if (updatedStagiaire.getLinkedin() != null) stagiaire.setLinkedin(updatedStagiaire.getLinkedin());
            if (updatedStagiaire.getGender() != null) stagiaire.setGender(updatedStagiaire.getGender());
            if (updatedStagiaire.getPhone() != null) stagiaire.setPhone(updatedStagiaire.getPhone());

            if (cvFile != null) {
                try {
                    stagiaire.setCv(cvFile.getBytes());
                } catch (IOException e) {
                    throw new RessourceNotFoundException("Failed to update CV file for Stagiaire with id: " + id);
                }
            }

            System.out.println("------------------------------------");

            updatedStagiaireObj = stagiaireRepository.save(stagiaire);
            Stagiaire stagiaire1 = userRepository.save(stagiaire);
            return StagiaireMapper.mapToStagiaireDto(stagiaire1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return StagiaireMapper.mapToStagiaireDto(updatedStagiaireObj);
    }

    @Override
    public Stagiaire getStagiaireDocument(Long id) {
        return stagiaireRepository.findStagiaireById(id);
    }
}
