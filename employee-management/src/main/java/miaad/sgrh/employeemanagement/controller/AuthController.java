package miaad.sgrh.employeemanagement.controller;

import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.dto.AuthRequest;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.repository.AccountRepository;
import miaad.sgrh.employeemanagement.serviceImpl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    private AccountRepository accountRepository;

//    @PostMapping("/register")
//    public String addNewUser(@RequestBody Account user) {
//        return service.saveUser(user);
//    }

    @PostMapping("/login")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            Account account = accountRepository.findAccountByLogin(authRequest.getUsername());
            return service.generateToken(account);
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
