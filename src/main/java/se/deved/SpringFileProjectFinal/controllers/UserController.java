package se.deved.SpringFileProjectFinal.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.deved.SpringFileProjectFinal.dtos.CreateUserRequest;
import se.deved.SpringFileProjectFinal.exceptions.UsernameAlreadyExistsException;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @PostMapping("/register")
    public ResponseEntity<?> registerUser (@RequestBody CreateUserRequest userRequest) {
        try {
            User user = userService.createUser(userRequest.getUsername(), userRequest.getPassword());
            return ResponseEntity.ok("User created");
        }
        catch (UsernameAlreadyExistsException ignored) {
            return ResponseEntity.badRequest().body("User with name " + userRequest.getUsername() + " already exists");
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
