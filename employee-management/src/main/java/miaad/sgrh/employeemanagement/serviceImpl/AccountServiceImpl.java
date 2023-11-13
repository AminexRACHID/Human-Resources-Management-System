package miaad.sgrh.employeemanagement.serviceImpl;

import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.dto.UserDto;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.repository.AccountRepository;
import miaad.sgrh.employeemanagement.service.AccountService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;


    @Override
    public Account createAccount(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = new Account();
        account.setLogin(userDto.getEmail());
        account.setRole("stagiaire");
        account.setConfirmation(false);
        account.setPassword(passwordEncoder.encode(userDto.getPassword()));

        account.setUserId(userDto.getId());

        return accountRepository.save(account);
    }
}
