package miaad.sgrh.employeemanagement.repository;

import miaad.sgrh.employeemanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
