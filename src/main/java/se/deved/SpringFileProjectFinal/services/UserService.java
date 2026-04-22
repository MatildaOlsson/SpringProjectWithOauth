package se.deved.SpringFileProjectFinal.services;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.deved.SpringFileProjectFinal.exceptions.CreateOidcUserException;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFolderFoundException;
import se.deved.SpringFileProjectFinal.exceptions.UsernameAlreadyExistsException;
import se.deved.SpringFileProjectFinal.models.Folder;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.repositories.IUserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;


    public User registerOAuthUser(String oidcId, String token) {
        if (oidcId.isBlank()) {
            throw new CreateOidcUserException("No Oidc-id was found");
        }
        //TODO Exception handling
//        if (userRepository.findByOidcId(oidcId).isPresent()) {
//            throw new CreateOidcUserException("User with that id already exists");
//        }

        User oauthUser = new User(null, token);
        oauthUser.setOidcId(oidcId);
        userRepository.save(oauthUser);
        System.out.println("User with name: " + null + "was saved to repo. Token: " + token);
        return oauthUser;

    }

    public User setNameToUser(String oidcId, String username) {
        User user = userRepository.findByOidcId(oidcId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(username);
        return userRepository.save(user);
    }

    //TODO Oklart om denna metod behövs
    public User createUser(String username, String password) {
        if (username.isBlank() || username.length() < 2) {
            throw new IllegalArgumentException("Username must have at least 2 characters");
        } else if (password.isBlank() || password.length() < 6) {
            throw new IllegalArgumentException("Password must have at least 6 characters");
        }
        //Addera mer password-säkerhet senare

        else if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("User with that name already exists");
        }

        User newUser = new User(username, password);
        userRepository.save(newUser);
        System.out.println("User saved to database");
        return newUser;

    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

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
                .orElseThrow(() -> new RuntimeException("Error"));
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}

//
//
//        public User createUser(String username, String password) {
//            if (username.isBlank() || username.length() < 2) {
//                throw new IllegalArgumentException("Username must have at least 2 characters");
//            } else if (password.isBlank() || password.length() < 6) {
//                throw new IllegalArgumentException("Password must have at least 6 characters");
//            }
//            //Addera mer password-säkerhet senare
//
//
//            else if (userRepository.findByUsername(username).isPresent()) {
//                throw new UsernameAlreadyExistsException("User with that name already exists");
//            }
//
////        String hashedPassword = passwordEncoder.encode(password);
//
//            User newUser = new User(username, null);
//            userRepository.save(newUser);
//            System.out.println("User saved to database");
//            return newUser;
//
//        }
//
//        public User createOidcUser(String username, String oidcId, String oidcProvider) {
//            if (userRepository.findByUsername(username).isPresent()) {
//                throw new UsernameAlreadyExistsException("User with that name already exists");
//            }
//
//            if (userRepository.findByOidcId(oidcId).isPresent()) {
//                throw new CreateOidcUserException("User already exists");
//            } else {
//
//                User newUser = new User(username, null);
//                newUser.setOidcId(oidcId);
//                newUser.setOidcProvider(oidcProvider);
//
//                newUser = userRepository.save(newUser);
//
//                return newUser;
//            }
//
//        }
//
//        public Optional<User> getUserById(UUID userId) {
//            return userRepository.findById(userId);
//        }
//
//        public Optional<User> getUserByOidc(String oidcId) {
//            return userRepository.findByOidcId((oidcId));
//        }
//
//    public String authenticateUser(String username, String password) throws AuthException {
//        var user = userRepository.findByUsername(username)
//                .orElseThrow(AuthException::new);
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new AuthException();
//        }
//
//        return jwtService.generateToken(user.getId());
//    }
//
//    }
//

