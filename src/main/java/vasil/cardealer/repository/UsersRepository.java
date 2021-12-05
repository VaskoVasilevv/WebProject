package vasil.cardealer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasil.cardealer.models.entity.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,String> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username,String email);
}
