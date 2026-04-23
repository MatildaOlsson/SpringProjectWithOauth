package se.deved.SpringFileProjectFinal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.repositories.IUserRepository;
import se.deved.SpringFileProjectFinal.services.UserService;

import java.util.UUID;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final IUserRepository userRepository;

    @PostMapping("/{oidcId}/{username}")
    public ResponseEntity<?> SetUsernameToOauthUser(@PathVariable String oidcId, @PathVariable String username) {
        try {
            System.out.println("Username:" + username);
            userService.setNameToUser(oidcId, username);
            return ResponseEntity.ok("Username updated");
        } catch (Exception e) {
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