package se.deved.SpringFileProjectFinal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.deved.SpringFileProjectFinal.dtos.DownloadFileResponse;
import se.deved.SpringFileProjectFinal.dtos.UploadFileRequest;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFileFoundException;
import se.deved.SpringFileProjectFinal.models.FileObject;
import se.deved.SpringFileProjectFinal.models.Folder;
import se.deved.SpringFileProjectFinal.repositories.IFileRepository;
import se.deved.SpringFileProjectFinal.repositories.IFolderRepository;

import java.io.FileOutputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class FileService {

    private final IFileRepository fileRepository;
    private final FolderService folderService;

    public void saveFile (UploadFileRequest request) {
        Folder folder = folderService.getFolder(request.getFolderName());

        FileObject internFile = new FileObject(request.getFileName(), folder, request.getDataInBytes());
        fileRepository.save(internFile);
    }

    public void deleteFile(UUID id) {
        FileObject file = fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchFileFoundException("File with id: " + id + " was not found"));
        fileRepository.delete(file);
    }

    public DownloadFileResponse getFileById (UUID id) {
        FileObject file = fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchFileFoundException("File with id: " + id + " was not found"));

        DownloadFileResponse fileResponse = new DownloadFileResponse(file.getFileName(), file.getDataInBytes());
        return fileResponse;

    }





}
