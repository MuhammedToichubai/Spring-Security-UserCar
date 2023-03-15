package peaksoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.models.User;

import java.util.Optional;

/**
 * @author Mukhammed Asantegin
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(String email);
}
