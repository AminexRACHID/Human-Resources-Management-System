package miaad.sgrh.employeemanagement.service;


import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Verification;

public interface VerificationService {

    Verification findByToken(String token);

    Verification findByAccount(Account account);

    void save(Account account, String token);
}
