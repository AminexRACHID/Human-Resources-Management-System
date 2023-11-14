package miaad.sgrh.user.repository;

import miaad.sgrh.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsUserByEmail(String email);
}
