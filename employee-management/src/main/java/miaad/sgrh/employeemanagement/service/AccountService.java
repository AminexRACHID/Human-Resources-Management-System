package miaad.sgrh.employeemanagement.service;

import jakarta.mail.MessagingException;
import miaad.sgrh.employeemanagement.dto.UserDto;
import miaad.sgrh.employeemanagement.entity.Account;

public interface AccountService {
    Account createAccount(UserDto userDto);
    Account save(Account account);

    Account resendVerification(String email);

    void deleteAccount(String email);

    String getPasswordByEmail(String email);

    void changePassword(String email, String newPass);

    String recover(String email) throws MessagingException;


}
