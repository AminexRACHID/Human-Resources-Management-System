package miaad.sgrh.employeemanagement.repository;

import jakarta.transaction.Transactional;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VerificationRepository extends JpaRepository<Verification,Long> {

    Verification findByToken(String token);

    Verification findByAccount(Account account);
    @Transactional
    @Modifying
    @Query("DELETE FROM Verification v WHERE v.account.id = :accountId")
    void deleteByAccountId(@Param("accountId")Long accountId);

}
