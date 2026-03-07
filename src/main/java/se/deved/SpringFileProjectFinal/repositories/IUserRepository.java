package se.deved.SpringFileProjectFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.deved.SpringFileProjectFinal.models.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository <User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByOidcId(String oidcId);
}
