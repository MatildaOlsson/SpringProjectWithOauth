package se.deved.SpringFileProjectFinal.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.deved.SpringFileProjectFinal.dtos.FileResponse;
import se.deved.SpringFileProjectFinal.dtos.FolderResponse;
import se.deved.SpringFileProjectFinal.exceptions.FolderNameAlreadyExists;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFolderFoundException;
import se.deved.SpringFileProjectFinal.models.Folder;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.services.FolderService;
import se.deved.SpringFileProjectFinal.services.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;


    @PostMapping("/{folderName}")
    public ResponseEntity<?> createFolder(@PathVariable String folderName, Authentication authentication) {
        String userid = authentication.getPrincipal() + "";
        System.out.println("userid:" + userid);

        try {
            folderService.saveFolder(folderName, userid);
            return ResponseEntity.ok("Folder with name: " + folderName + " was saved to user: " + userid);
        } catch (FolderNameAlreadyExists ignored) {
            return ResponseEntity.badRequest().body("Foldername already exists");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFolder(@PathVariable UUID id, Authentication authentication) {
        String userid = authentication.getPrincipal() + "";
        System.out.println("userid:" + userid);
        Folder folder;

        try {
            folder = folderService.getFolder(id, userid);
            System.out.println("Kommer foldern till controllern?: " + folder.getFolderName()) ;
            FolderResponse folderResponse = FolderResponse.fromModel(folder);
            return ResponseEntity.ok().body(folderResponse);
        } catch (NoSuchFolderFoundException ignored) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFolder(@PathVariable UUID id, Authentication authentication) {
        String userid = authentication.getPrincipal() + "";
        System.out.println("userid:" + userid);

        try {
            folderService.deleteFolder(id, userid);
            return ResponseEntity.ok("Folder deleted");
        } catch (NoSuchFolderFoundException ignored) {
            return ResponseEntity.notFound().build();
        }

    }


}

