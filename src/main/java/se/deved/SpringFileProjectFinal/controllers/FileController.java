package se.deved.SpringFileProjectFinal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.deved.SpringFileProjectFinal.dtos.DownloadFileResponse;
import se.deved.SpringFileProjectFinal.dtos.FileResponse;
import se.deved.SpringFileProjectFinal.dtos.UploadFileRequest;
import se.deved.SpringFileProjectFinal.exceptions.AutenticationException;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFileFoundException;
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
     * Endpoint to upload file to a selected Folder using the UUID
     * @param file The selected file (pdf. txt etc)
     * @param folderId The UUID of the Folder
     * @return Returns response to the client
     */

    @PostMapping("/{folderId}/{fileName}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable UUID folderId, @PathVariable String fileName, Authentication authentication) {
        String userid = authentication.getPrincipal() + "";
        System.out.println("userid:" + userid);
        FileEntity fileObject;

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File Empty");
        }

        try {
            UploadFileRequest request = new UploadFileRequest(fileName, folderId, file.getBytes());
            fileObject = fileService.saveFile(request, userid);
            FileResponse fileResponse = FileResponse.fromModel(fileObject);
            return ResponseEntity.ok().body(fileResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    /**
     * Endpoint to download file
     * @param id the id (UUID) of the file
     * @return This method will return the files bytes as a text representation in Bruno, to get the file "properly" use the browser as client
     */
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getFileById(@PathVariable UUID id) {
//       DownloadFileResponse file = fileService.getFileById(id);
//
//            try {
//                return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION,
//                            "attachment; filename=\"" + file.getFileName() + "\"")
//                    .body(file.getDataInBytes());
//        }
//
//        catch (NoSuchFileFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//            }

    /**
     * Endpoint to delete selected file
     * @param fileId the id (UUID) of the file
     * @return returns response to the client with status code "Not Found" or "Ok"
     */

    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteFileById(@PathVariable UUID fileId, Authentication authentication) {
        String userid = authentication.getPrincipal() + "";
        System.out.println("userid:" + userid);

        try {
            fileService.deleteFile(fileId, userid);
//            FileEntity fileObject = fileService.getFileById(fileId);
//            FileResponse fileResponse = FileResponse.fromModel(fileObject);
            return ResponseEntity.ok().body("YES");
//            return ResponseEntity.ok().body(fileResponse);
        }
        catch (NoSuchFileFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (AutenticationException e) {
            return ResponseEntity.badRequest().body("Not authorized");
        }
    }

    }






