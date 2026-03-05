package se.deved.SpringFileProjectFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.deved.SpringFileProjectFinal.models.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository <User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByOidcId(String oidcId);
}
