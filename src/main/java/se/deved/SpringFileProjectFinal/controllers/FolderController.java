package se.deved.SpringFileProjectFinal.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.deved.SpringFileProjectFinal.services.FolderService;

import java.util.UUID;

@RestController
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<?> createFolder(@RequestParam("folderName") String folderName) {
        if (folderName.isBlank()) {
            return ResponseEntity.badRequest().body("No folder name was included");
        }
            folderService.getFolder(folderName);
            return ResponseEntity.ok("Folder with name " + folderName + " created");

    }
    // Lägga till en "Get-metod"




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFolder (@PathVariable UUID id){
        folderService.deleteFolder(id);
        return ResponseEntity.ok("Folder deleted");

    }


    }

