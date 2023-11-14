package miaad.sgrh.employeemanagement.repository;

import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<Verification,Long> {

    Verification findByToken(String token);

    Verification findByAccount(Account account);

}
