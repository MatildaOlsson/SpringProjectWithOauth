package se.deved.SpringFileProjectFinal.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.deved.SpringFileProjectFinal.dtos.FolderResponse;
import se.deved.SpringFileProjectFinal.exceptions.FolderNameAlreadyExists;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFolderFoundException;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchUserFoundException;
import se.deved.SpringFileProjectFinal.models.Folder;
import se.deved.SpringFileProjectFinal.services.FolderService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    /**
     * Endpoint to upload Folder to a user.
     * You need to use Header: "Authorization" with "Bearer: token/password from your user
     * @param folderName The folder name of the new folder
     * @return Returns response to the client using HateOas if created
     */
    @PostMapping("/{folderName}")
    public ResponseEntity<?> createFolder(@PathVariable String folderName, Authentication authentication) {
        Folder folder;

        try {
            folder = folderService.saveFolder(folderName, authentication.getPrincipal() + "");
            return ResponseEntity.created(URI.create("/folder")).body(FolderResponse.fromModel(folder));
        } catch (FolderNameAlreadyExists ignored) {
            return ResponseEntity.badRequest().body("Folder name already exists");
        } catch (NoSuchUserFoundException ignored) {
            return ResponseEntity.badRequest().body("User not found");
        }

    }

    /**
     * Endpoint to download a folder
     * You need to use Header: "Authorization" with "Bearer: token/password from your user
     * @param id The id of the folder to download
     * @return Returns response to the client using HateOas if found
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getFolder(@PathVariable UUID id, Authentication authentication) {
        Folder folder;

        try {
            folder = folderService.getFolder(id, authentication.getPrincipal() + "");
            return ResponseEntity.ok().body(FolderResponse.fromModel(folder));
        } catch (NoSuchFolderFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to delete folder
     * You need to use Header: "Authorization" with "Bearer: token/password from your user
     * @param id The id of the folder to delete
     * @return Returns response to the client
     */

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

