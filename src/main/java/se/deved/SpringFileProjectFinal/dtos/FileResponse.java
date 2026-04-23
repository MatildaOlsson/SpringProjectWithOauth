package se.deved.SpringFileProjectFinal.dtos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import se.deved.SpringFileProjectFinal.controllers.UserController;
import se.deved.SpringFileProjectFinal.models.FileEntity;
import se.deved.SpringFileProjectFinal.models.User;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
public class FileResponse extends RepresentationModel<FileResponse> {

    private UUID id = UUID.randomUUID();
    private String fileName;

    private UserResponse user;
    private String folderName;

    public FileResponse(UUID id, String fileName, User user, String folderName) {
        this.id = id;
        this.fileName = fileName;
        this.user = UserResponse.fromModel(user);
        this.folderName = folderName;
    }

    public static FileResponse fromModel(FileEntity file) {

        FileResponse response = new FileResponse(file.getId(), file.getFileName(), file.getUser(), file.getFolder().getFolderName());

        response.add(linkTo(
                methodOn(UserController.class).
                        findUserById(file.getUser().getId()))
                .withRel("user")
        );
        return response;
    }

}
