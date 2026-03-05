package se.deved.SpringFileProjectFinal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.deved.SpringFileProjectFinal.dtos.DownloadFileResponse;
import se.deved.SpringFileProjectFinal.dtos.UploadFileRequest;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFileFoundException;
import se.deved.SpringFileProjectFinal.models.File;
import se.deved.SpringFileProjectFinal.models.Folder;
import se.deved.SpringFileProjectFinal.repositories.IFileRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final IFileRepository fileRepository;
    private final FolderService folderService;

    public void saveFile (UploadFileRequest request) {
        Folder folder = folderService.getFolder(request.getFolderName());

        File internFile = new File(request.getFileName(), folder, request.getDataInBytes());
        fileRepository.save(internFile);
    }

    public void deleteFile(UUID id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchFileFoundException("File with id: " + id + " was not found"));
        fileRepository.delete(file);
    }

    public DownloadFileResponse getFileById (UUID id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchFileFoundException("File with id: " + id + " was not found"));

        DownloadFileResponse fileResponse = new DownloadFileResponse(file.getFileName(), file.getDataInBytes());
        return fileResponse;

    }





}
