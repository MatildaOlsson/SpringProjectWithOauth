package se.deved.SpringFileProjectFinal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.deved.SpringFileProjectFinal.dtos.UploadFileRequest;
import se.deved.SpringFileProjectFinal.exceptions.*;
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

    public FileEntity saveFile(UploadFileRequest request, String userid) {
        Folder folder;
        User user = userService.findUserByOidcId(userid);
        try {
            folder = folderService.getFolder(request.getFolderId(), userid);
            System.out.println("FOLDER-ID: " + folder.getId());
        } catch (NoSuchFolderFoundException e) {
            throw new NoSuchFolderFoundException("No folder found2");
        }

        if (fileRepository.existsByFolder_IdAndFileName(folder.getId(), request.getFileName())) {
            System.out.println("Matchande filnamn");
            throw new FileNameAlreadyExists("File with that name already exists in the folder");
        } else {
            FileEntity file = new FileEntity(request.getFileName(), request.getDataInBytes(), user, folder);
            fileRepository.save(file);
            System.out.println("Fileuploaded: " + file.getFileName());
            return file;
        }
    }

    public FileEntity downloadFile(UUID fileId, String userId) {
        User user = userService.findUserByOidcId(userId);

        Optional<FileEntity> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isPresent() && fileOptional.get().getUser().equals(user)) {
            return fileOptional.get();
        } else {
            throw new NoSuchFileFoundException("File with id: " + fileId + " was not found");
        }
    }


    public void deleteFile(UUID fileId, String userid) {
        User user;
        try {
            user = userService.findUserByOidcId(userid);
            System.out.println("User found with id: " + user.getOidcId());
        } catch (NoSuchUserFoundException e) {
            throw new NoSuchUserFoundException("");
        }

        Optional<FileEntity> optionalFile = fileRepository.findById(fileId);
        System.out.println("UserUUID: " + user.getId());

        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        System.out.println("FILE USER ENTITY: " + file.getUser());

        if (optionalFile.isPresent() && optionalFile.get().getUser().getId().equals(user.getId())) {
            fileRepository.delete(optionalFile.get());
        } else {
            throw new AutenticationException("Couldn't confirm user or folder");
        }

    }

    public FileEntity getFileById(UUID id) {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchFileFoundException("File with id: " + id + " was not found"));
        return file;

    }
}
