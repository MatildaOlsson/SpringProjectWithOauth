package se.deved.SpringFileProjectFinal.services;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.deved.SpringFileProjectFinal.exceptions.CreateOidcUserException;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchUserFoundException;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.repositories.IUserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;


    public User registerOAuthUser(String oidcId) {
        if (oidcId.isBlank()) {
            throw new CreateOidcUserException("No Oidc-id was found");
        }

        User user;
        Optional<User> userOptional = userRepository.findByOidcId(oidcId);
        if (userOptional.isPresent()) {
            user = userOptional.get();
            System.out.println("Returning existing user");
            return user;
        }

        String token = generateToken();
        User oauthUser = new User(null, token);
        oauthUser.setOidcId(oidcId);
        userRepository.save(oauthUser);
        System.out.println("User with name: " + null + "was saved to repo. Token: " + token);
        return oauthUser;

    }

    private String generateToken() {
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 15);
        return token;
    }

    public User setNameToUser(String oidcId, String username) {
        User user = userRepository.findByOidcId(oidcId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(username);
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserFoundException("User not found"));

        userRepository.delete(user);
    }


    public User authenticateUser(String token) throws AuthException {
        User user = userRepository.findByPassword(token)
                .orElseThrow(AuthException::new);

        if (user.getPassword().equals(token)) {
            System.out.println("User validated");
            return user;
        } else {
            return null;
        }
    }

    public User findUserByOidcId(String oidcId) {
        return userRepository.findByOidcId(oidcId)
                .orElseThrow(() -> new NoSuchUserFoundException("No user found"));
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}


