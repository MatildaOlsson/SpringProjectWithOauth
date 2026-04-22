package se.deved.SpringFileProjectFinal.dtos;

import org.springframework.hateoas.RepresentationModel;
import se.deved.SpringFileProjectFinal.models.User;

import java.util.UUID;

public class UserResponse extends RepresentationModel<UserResponse> {
    private final UUID id;
    private String username;

    public UserResponse(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserResponse fromModel(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername()
        );
    }
}
