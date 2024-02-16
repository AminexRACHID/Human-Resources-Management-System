package miaad.sgrh.employeemanagement.repository;

import jakarta.transaction.Transactional;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Transactional
    void deleteByLogin(String email);

    Account findAccountByLogin(String email);
}