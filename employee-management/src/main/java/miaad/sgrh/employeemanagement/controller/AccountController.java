package miaad.sgrh.employeemanagement.controller;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.dto.UserDto;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Verification;
import miaad.sgrh.employeemanagement.exception.RessourceNotFoundException;
import miaad.sgrh.employeemanagement.repository.AccountRepository;
import miaad.sgrh.employeemanagement.service.AccountService;
import miaad.sgrh.employeemanagement.service.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private AccountService accountService;
    private VerificationService verificationService;
    private AccountRepository accountRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody UserDto userDto){
        try{
            Account account = accountService.createAccount(userDto);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/activation")
    public ModelAndView  activation(@RequestParam("token") String token, Model model){
        // create HTML page activation
        Verification verification = verificationService.findByToken(token);
        if(verification == null){
            model.addAttribute("message", "Your verification token is invalid");

        } else {
            Account account = verification.getAccount();

            // if the user account is not valid
            if(!account.isConfirmation()){
                // get the current time
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                // check if the token is expired
                if(verification.getExpiryDate().before(currentTime)){
                    model.addAttribute("message", "Your verification token has expired");
                } else {
                    // user is valid
                    account.setConfirmation(true);
                    accountService.save(account);
                    model.addAttribute("message", "Your account is successfully activated");
                }
            }else {
                model.addAttribute("message", "Your account is already activated");
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("activation");
        modelAndView.addObject("message", model.getAttribute("message"));
        return modelAndView;
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerification(@RequestParam("email") String email) {
        try {
            Account account = accountService.resendVerification(email);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{email}")
    public ResponseEntity<?> deleteAccount(@PathVariable("email") String email){
        try{
            accountService.deleteAccount(email);
            return ResponseEntity.ok("Account deleted successfully.");
        } catch (RessourceNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{email}")
    public ResponseEntity<?>  getPassword(@PathVariable("email") String email){
        try{
            String pass = accountService.getPasswordByEmail(email);
            return new ResponseEntity<>(pass, HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam("email") String email,@RequestBody String newPass){
        try{
            accountService.changePassword(email,newPass);
            return ResponseEntity.ok("Password changed successfully.");
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/acc/{email}")
    public ResponseEntity<?>  getaccount(@PathVariable("email") String email){
        try{
            Account account = accountRepository.findAccountByLogin(email);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/accc/{email}")
    public Account  getaccounte(@PathVariable("email") String email){
        return accountRepository.findAccountByLogin(email);
    }

    @GetMapping("/passwordRecovre/{email}")
    public ResponseEntity<?>  passwordRecovre(@PathVariable("email") String email) throws MessagingException {
        try {
            String var  = accountService.recover(email);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "pawssword sent to email");

            return ResponseEntity.ok(responseMap);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
