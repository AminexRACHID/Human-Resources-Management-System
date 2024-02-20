package miaad.sgrh.employeemanagement.serviceImpl;

import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.dto.UserDto;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Employee;
import miaad.sgrh.employeemanagement.exception.RessourceNotFoundException;
import miaad.sgrh.employeemanagement.repository.AccountRepository;
import miaad.sgrh.employeemanagement.repository.VerificationRepository;
import miaad.sgrh.employeemanagement.service.AccountService;
import miaad.sgrh.employeemanagement.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private VerificationService verificationService;
    private VerificationRepository verificationRepository;
    private EmailServiceImpl emailService;

    @Override
    public Account createAccount(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = new Account();
        account.setLogin(userDto.getEmail());
        account.setRole("Stagiaire");
        account.setConfirmation(false);
        account.setPassword(passwordEncoder.encode(userDto.getPassword()));
        account.setUserId(userDto.getId());
        Optional<Account> saved = Optional.of(accountRepository.save(account));

        saved.ifPresent( a -> {
            try {
                String token = UUID.randomUUID().toString();
                verificationService.save(saved.get(),token);
                // send verification email
                emailService.sendHtmlMail(a);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        return saved.get();
    }

    @Override
    public Account resendVerification(String email) {
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findAccountByLogin(email));

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            // Check if the account is already confirmed
            if (account.isConfirmation()) {
                throw new RessourceNotFoundException("Account is already confirmed.");
            }

            // Generate a new verification token
            String newToken = UUID.randomUUID().toString();

            // Update the verification token and save
            verificationService.save(account, newToken);

            // Send the verification email again
            try {
                emailService.sendHtmlMail(account);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return account;
        } else {
            throw new RessourceNotFoundException("Account not found.");
        }
    }

    @Override
    public void deleteAccount(String email) {
        Account account = accountRepository.findAccountByLogin(email);

        if (account != null){
            try{
                verificationRepository.deleteByAccountId(account.getId());
                accountRepository.deleteByLogin(email);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else {
            throw new RessourceNotFoundException("Account not found.");
        }
    }

    @Override
    public String getPasswordByEmail(String email) {
        Account account = accountRepository.findAccountByLogin(email);
        return account.getPassword();
    }

    @Override
    public void changePassword(String email, String newPass) {
        Account account = accountRepository.findAccountByLogin(email);
        account.setPassword(newPass);
        accountRepository.save(account);
    }


    @Override
    public Account save(Account account){
        return accountRepository.save(account);
    }


}
