package se.deved.SpringFileProjectFinal.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.AuthException;
import org.springframework.stereotype.Service;
import se.deved.SpringFileProjectFinal.exceptions.FolderNameAlreadyExists;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchFolderFoundException;
import se.deved.SpringFileProjectFinal.exceptions.NoSuchUserFoundException;
import se.deved.SpringFileProjectFinal.models.Folder;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.repositories.IFolderRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final IFolderRepository folderRepository;
    private final UserService userService;


    //TODO Se över exceptions...

    public Folder saveFolder(String folderName, String userid) {
        User user;
        try {
            user = userService.findUserByOidcId(userid);
            System.out.println("User found with id: " + user.getOidcId());
        } catch (NoSuchUserFoundException e) {
            throw new RuntimeException(e);
        }

        Optional<Folder> folderOptional = folderRepository.findByFolderName(folderName);
        Folder folder;
        if (folderOptional.isPresent() && folderOptional.get().getUser().equals(user)) {
            throw new FolderNameAlreadyExists("Foldername already exists");
        } else {
            return folderRepository.save(new Folder(folderName, user));
        }
    }

    public Folder getFolder(UUID folderId, String userid) {
        User user;
        try {
            user = userService.findUserByOidcId(userid);
            System.out.println("User found with id: " + user.getOidcId());
        } catch (Exception e) {
            throw new NoSuchUserFoundException("");
        }
        Optional<Folder> folderOptional = folderRepository.findById(folderId);
        Folder folder;
        if (folderOptional.isPresent() && folderOptional.get().getUser().equals(user)) {
            folder = folderOptional.get();
            System.out.println("Folder:" + folder.getFolderName());
            return folder;
        } else {
            throw new NoSuchFolderFoundException("");
        }


    }

    public void deleteFolder(UUID id, String userid) {
        User user;
        try {
            user = userService.findUserByOidcId(userid);
            System.out.println("User found with id: " + user.getOidcId());
        } catch (Exception e) {
            throw new NoSuchUserFoundException("");
        }

        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new NoSuchFolderFoundException("Folder with name: " + id + " was not found"));

        if (!folder.getUser().equals(user)) {
            throw new NoSuchFolderFoundException("Folder doesn't belongs to user");
        }
        else {
            folderRepository.delete(folder);
        }
    }
}
//            Folder newFolder = new Folder(folderName);     // Excetion, folderNameAlreadyExists
//            folderRepository.save(newFolder);
//            return newFolder;
//    }
//
//   public Optional<Folder> getFolderIfExists (String folderName) {
//       Optional<Folder> folderOptional = folderRepository.findByFolderName(folderName);
//       return folderOptional;
//   }
//
//    public void deleteFolder(UUID id) {
//        Folder folder = folderRepository.findById(id)
//                .orElseThrow(() -> new NoSuchFolderFoundException("Folder with name: " + id + " was not found"));
//
//        folderRepository.delete(folder);
//     }
//    }










