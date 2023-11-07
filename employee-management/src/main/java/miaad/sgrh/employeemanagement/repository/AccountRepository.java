package miaad.sgrh.employeemanagement.repository;

import jakarta.transaction.Transactional;
import miaad.sgrh.employeemanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Transactional
    void deleteByLogin(String email);
}
