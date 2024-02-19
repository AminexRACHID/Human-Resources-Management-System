package miaad.sgrh.employeemanagement.serviceImpl;

import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(Account credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String generateToken(Account credential) {
        return jwtService.generateToken(credential);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}