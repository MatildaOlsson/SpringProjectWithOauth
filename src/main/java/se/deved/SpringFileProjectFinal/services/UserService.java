package se.deved.SpringFileProjectFinal.services;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.deved.SpringFileProjectFinal.exceptions.CreateOidcUserException;
import se.deved.SpringFileProjectFinal.exceptions.UsernameAlreadyExistsException;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.repositories.IUserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser (String username, String password) {
        if (username.isBlank() || username.length() < 2){
            throw new IllegalArgumentException("Username must have at least 2 characters");
        }

       else if (password.isBlank() || password.length() < 6) {
            throw new IllegalArgumentException("Password must have at least 6 characters");
        }
       //Addera mer password-säkerhet senare


        else if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("User with that name already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User(username, hashedPassword);
        userRepository.save(newUser);
        System.out.println("User saved to database");
        return newUser;

    }

    public User createOidcUser(String username, String oidcId, String oidcProvider) throws Exception {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("User with that name already exists");
        }

//        if (userRepository.findByOidcId(oidcId).isPresent()) {
//            throw new CreateOidcUserException("User already exists");
//        }

        else {

            User newUser = new User(username, null);
            newUser.setOidcId(oidcId);
            newUser.setOidcProvider(oidcProvider);

            newUser = userRepository.save(newUser);

            return newUser;
        }
    }


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
}
