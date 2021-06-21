package br.com.zup.edu.ecommerce.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByLogin(String login);

    Optional<User> findByLogin(String username);
}
