package se.deved.SpringFileProjectFinal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.deved.SpringFileProjectFinal.dtos.DownloadFileResponse;
import se.deved.SpringFileProjectFinal.dtos.UploadFileRequest;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFileFoundException;
import se.deved.SpringFileProjectFinal.models.FileObject;
import se.deved.SpringFileProjectFinal.repositories.IFileRepository;
import se.deved.SpringFileProjectFinal.repositories.IFolderRepository;
import se.deved.SpringFileProjectFinal.services.FileService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * Endpoint to upload file to a selected Folder
     * @param file The selected file (pdf. txt etc)
     * @param folderName The name of the Folder
     * @return Returns response to the client
     */

    @PostMapping("/{folderName}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String folderName) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File Empty");
        }

        try {
            UploadFileRequest request = new UploadFileRequest(file.getName(), folderName, file.getBytes());
            fileService.saveFile(request);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
        return ResponseEntity.ok().body("File uploaded");
    }

    /**
     * Endpoint to download file
     * @param id the id (UUID) of the file
     * @return This method will return the files bytes as a text representation in Bruno, to get the file "properly" use the browser as client
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable UUID id) {
       DownloadFileResponse file = fileService.getFileById(id);

            try {
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getDataInBytes());
        }

        catch (NoSuchFileFoundException e) {
            return ResponseEntity.notFound().build();
        }
            }

    /**
     * Endpoint to delete selected file
     * @param id the id (UUID) of the file
     * @return returns response to the client with status code "Not Found" or "Ok"
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFileById(@PathVariable UUID id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok("Selected file id: " + id + " was deleted");
        }
        catch (NoSuchFileFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    }






