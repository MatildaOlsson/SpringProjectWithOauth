package se.deved.SpringFileProjectFinal.controllers;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.deved.SpringFileProjectFinal.dtos.CreateUserRequest;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.repositories.IUserRepository;
import se.deved.SpringFileProjectFinal.services.UserService;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final IUserRepository userRepository;

    @GetMapping("/public/hello")
    public String publicHello() {
        return "public";
    }

    //Test-endpoints
    @GetMapping("/private/hello")
    public String privateHello(Authentication authentication) {
        var user = authentication.getPrincipal();
        System.out.println("Användare:" + user);

        return "private";
    }


    @PostMapping("/{oidcId}/{username}")
    public ResponseEntity<?> SetUsernameToOautUser(@PathVariable String oidcId, @PathVariable String username) {
        try {
            System.out.println("Username:" + username);
            userService.setNameToUser(oidcId, username);
            return ResponseEntity.ok("Username updated");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    //TODO Lite oklart om dessa endpoints behövs (register och login)
    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody CreateUserRequest request) { //TODO denna och den nedanför ska vara public
        try {
            var user = userService.createUser(request.getUsername(), request.getPassword());
            return ResponseEntity.ok("User with name:" + request.getUsername() + " was created");
//            return ResponseEntity.created(URI.create("/user")).body(UserResponse.fromModel(user)); //TODO HEATEOAS
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> deleteUser (@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");

        }
    }
@GetMapping("/{id}")
    public User findUserById(@PathVariable UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }



}