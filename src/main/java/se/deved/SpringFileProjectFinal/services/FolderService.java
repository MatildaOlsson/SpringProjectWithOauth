package se.deved.SpringFileProjectFinal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFolderFoundException;
import se.deved.SpringFileProjectFinal.models.Folder;
import se.deved.SpringFileProjectFinal.repositories.IFolderRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final IFolderRepository folderRepository;

    public Folder getFolder(String folderName) {
        Optional<Folder> folderOptional = folderRepository.findByFolderName(folderName);
        Folder folder;
        if (folderOptional.isEmpty()) {
            folder = new Folder(folderName);
            folderRepository.save(folder);
            return folder;
        }
        else {
            folder = folderOptional.get();
            return folder;
        }
//            Folder newFolder = new Folder(folderName);     // Excetion, folderNameAlreadyExists
//            folderRepository.save(newFolder);
//            return newFolder;
    }

   public Optional<Folder> getFolderIfExists (String folderName) {
       Optional<Folder> folderOptional = folderRepository.findByFolderName(folderName);
       return folderOptional;
   }

    public void deleteFolder(UUID id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new NoSuchFolderFoundException("Folder with name: " + id + " was not found"));

        folderRepository.delete(folder);
        }
    }










