package miaad.sgrh.user.serviceImpl;

import lombok.AllArgsConstructor;
import miaad.sgrh.user.repository.StagiaireRepository;
import miaad.sgrh.user.repository.UserRepository;
import miaad.sgrh.user.service.StagiaireService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StagiaireServiceImpl implements StagiaireService {
    private StagiaireRepository stagiaireRepository;
    private UserRepository userRepository;
}
