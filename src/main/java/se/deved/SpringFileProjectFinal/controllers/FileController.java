package se.deved.SpringFileProjectFinal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.deved.SpringFileProjectFinal.dtos.FileResponse;
import se.deved.SpringFileProjectFinal.dtos.UploadFileRequest;
import se.deved.SpringFileProjectFinal.exceptions.AutenticationException;
import se.deved.SpringFileProjectFinal.exceptions.FileNameAlreadyExists;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFileFoundException;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFolderFoundException;
import se.deved.SpringFileProjectFinal.models.FileEntity;
import se.deved.SpringFileProjectFinal.services.FileService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * Endpoint to upload file to a selected Folder using the folder-UUID.
     * You need to use Header: "Authorization" with "Bearer: token/password from the user
     *
     * @param file     The selected file (pdf. txt etc)
     * @param folderId The UUID of the Folder
     * @return Returns response to the client with hateoas if succeeded, otherwise Exceptions
     */

    @PostMapping("/{folderId}/{fileName}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable UUID folderId, @PathVariable String fileName, Authentication authentication) {
        String userid = authentication.getName();
        System.out.println("userid:" + userid);
        FileEntity fileObject;

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File Empty");
        }

        try {
            UploadFileRequest request = new UploadFileRequest(fileName, folderId, file.getBytes());
            System.out.println("request" + request);
            fileObject = fileService.saveFile(request, userid);
            return ResponseEntity.ok().body(FileResponse.fromModel(fileObject));
        } catch (FileNameAlreadyExists e) {
            return ResponseEntity.internalServerError().body("Filename occupied");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("File related problem");
        } catch (NoSuchFolderFoundException e) {
            return ResponseEntity.badRequest().body("No folder found, try again with an existing folder");
        }
    }

    /**
     * Endpoint to download file
     * * You need to use Header: "Authorization" with "Bearer: token/password from the user
     *
     * @param fileId the id (UUID) of the file
     * @return This method will return the fileEntity (without bytes) -- > use download-endpoint to get bytes
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable UUID fileId, Authentication authentication) {
        try {
            FileEntity file = fileService.downloadFile(fileId, authentication.getName());
            return ResponseEntity.ok()
                    .body(FileResponse.fromModel(file));
        } catch (NoSuchFileFoundException e) {
            return ResponseEntity.badRequest().body("File not found");
        }
    }

    /**
     * Endpoint to download file (the bytes as a text representation in Bruno)
     * * You need to use Header: "Authorization" with "Bearer: token/password from the user
     * @param fileId the id (UUID) of the file
     * @return This method will return the files bytes as a text representation
     */

    @GetMapping("/{fileId}/download")
    public ResponseEntity<?> downloadFile(@PathVariable UUID fileId, Authentication authentication) {
        try {
            FileEntity file = fileService.downloadFile(fileId, authentication.getName());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .body(file.getDataInBytes());
        } catch (NoSuchFileFoundException e) {
            return ResponseEntity.badRequest().body("File not found");
        }
    }

    /**
     * Endpoint to delete selected file
     * You need to use Header: "Authorization" with "Bearer: token/password from the user
     * @param fileId the id (UUID) of the file
     * @return returns response to the client with status code
     */

    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteFileById(@PathVariable UUID fileId, Authentication authentication) {
        try {
            fileService.deleteFile(fileId, authentication.getName());
            return ResponseEntity.noContent().build();
        } catch (NoSuchFileFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AutenticationException e) {
            return ResponseEntity.badRequest().body("Not authorized");
        }
    }

}






