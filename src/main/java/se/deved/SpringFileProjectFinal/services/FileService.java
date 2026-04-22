package se.deved.SpringFileProjectFinal.services;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.deved.SpringFileProjectFinal.dtos.DownloadFileResponse;
import se.deved.SpringFileProjectFinal.dtos.UploadFileRequest;
import se.deved.SpringFileProjectFinal.exceptions.AutenticationException;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFileFoundException;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFolderFoundException;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchUserFoundException;
import se.deved.SpringFileProjectFinal.models.FileEntity;
import se.deved.SpringFileProjectFinal.models.Folder;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.repositories.IFileRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final IFileRepository fileRepository;
    private final FolderService folderService;
    private final UserService userService;

    public FileEntity saveFile (UploadFileRequest request, String userid) {
        Folder folder;
        User user; //TODO Hantera så att det inte går att sätta samma namn osv.. även om folders och användare
        try {
            folder = folderService.getFolder(request.getFolderId(), userid);
            user = userService.findUserByOidcId(userid);
            System.out.println("FOLDER-ID: " + folder.getId());
            FileEntity file = new FileEntity(request.getFileName(), request.getDataInBytes(), user, folder);
            fileRepository.save(file);
            System.out.println("Fileuploaded: " + file.getFileName());
            return file;

//            fileRepository.save(new FileEntity(request.getFileName(), request.getDataInBytes(), user, folder));
//            System.out.println("Fileuploaded: " + fileuploaded.getFileName());
        }
        catch (NoSuchFolderFoundException e) {
            throw new NoSuchFolderFoundException("No matching folder was found");
        }

    }

    public void deleteFile(UUID fileId, String userid) {
        User user;
        try {
            user = userService.findUserByOidcId(userid);
            System.out.println("User found with id: " + user.getOidcId());
        } catch (Exception e) {
            throw new NoSuchUserFoundException("");
        }

        Optional<FileEntity> optionalFile = fileRepository.findById(fileId);
        System.out.println("UserUUID: " + user.getId());

        if(optionalFile.isPresent() && optionalFile.get().getUser().equals(user)) {
            fileRepository.delete(optionalFile.get());
        }
        else {
            throw new AutenticationException("Couldn't confirm user or folder");
        }




    }

    public FileEntity getFileById (UUID id) {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchFileFoundException("File with id: " + id + " was not found"));
        return file;

    }





}
