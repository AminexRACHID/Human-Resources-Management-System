package miaad.sgrh.user.serviceImpl;

import lombok.AllArgsConstructor;
import miaad.sgrh.user.dto.StagiaireDto;
import miaad.sgrh.user.dto.UserDto;
import miaad.sgrh.user.entity.Stagiaire;
import miaad.sgrh.user.exception.RessourceNotFoundException;
import miaad.sgrh.user.feign.AccountRestClient;
import miaad.sgrh.user.mapper.StagiaireMapper;
import miaad.sgrh.user.repository.StagiaireRepository;
import miaad.sgrh.user.repository.UserRepository;
import miaad.sgrh.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private StagiaireRepository stagiaireRepository;
    private AccountRestClient accountRestClient;

    @Override
    public StagiaireDto createUser(UserDto userDto) {
        if (userRepository.existsUserByEmail(userDto.getEmail())) {
            throw new RessourceNotFoundException("Email already used. Please choose a different email address.");
        }

        Stagiaire stagiaire = new Stagiaire();
        stagiaire.setEmail(userDto.getEmail());
        stagiaire.setFirstName(userDto.getFirstName());
        stagiaire.setGender(userDto.getGender());
        stagiaire.setLastName(userDto.getLastName());
        stagiaire.setPhone(userDto.getPhone());

        Stagiaire sg = stagiaireRepository.save(stagiaire);
        userDto.setId(sg.getId());
        try{
            accountRestClient.createAccount(userDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return StagiaireMapper.mapToStagiaireDto(sg);
    }


}
