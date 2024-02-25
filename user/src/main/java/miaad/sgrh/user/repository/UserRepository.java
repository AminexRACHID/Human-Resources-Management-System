package miaad.sgrh.user.repository;

import miaad.sgrh.user.dto.UserDto;
import miaad.sgrh.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsUserByEmail(String email);

    User findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.lastName = :lastName AND u.firstName = :firstName")
    List<User> getUserByLastNameAndFirstName(String lastName, String firstName);
}
