package miaad.sgrh.employeemanagement.controller;

import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.dto.AuthRequest;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.repository.AccountRepository;
import miaad.sgrh.employeemanagement.serviceImpl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponse;
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
    public ResponseEntity<?> getToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                Account account = accountRepository.findAccountByLogin(authRequest.getUsername());
                if (account.isConfirmation()){
                    return ResponseEntity.ok(service.generateToken(account));
                } else {
                    String errorMessage = "Vous devez Confirmer votre compte par Email !!";
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                String errorMessage = "Email ou mot de passe incorrect !!";
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            String errorMessage = "Email ou mot de passe incorrect !!";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
