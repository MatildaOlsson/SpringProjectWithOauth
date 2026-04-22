package se.deved.SpringFileProjectFinal.dtos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import se.deved.SpringFileProjectFinal.controllers.UserController;
import se.deved.SpringFileProjectFinal.models.Folder;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.services.UserService;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
public class FolderResponse extends RepresentationModel<FolderResponse> {

    private final UUID id;
    private String folderName;
    private UserResponse user;

    public FolderResponse(UUID id, String folderName, User user) {
        this.id = id;
        this.folderName = folderName;
        this.user = UserResponse.fromModel(user);
    }

    public static FolderResponse fromModel(Folder folder) {

        FolderResponse response = new FolderResponse(folder.getId(), folder.getFolderName(), folder.getUser());
        System.out.println("userInresponse:" + folder.getFolderName());

        response.add(linkTo(
                methodOn(UserController.class).findUserById(folder.getUser().getId()))
                .withRel("user")
        );
        return response;
    }



}
