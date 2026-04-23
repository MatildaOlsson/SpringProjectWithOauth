package se.deved.SpringFileProjectFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.deved.SpringFileProjectFinal.models.FileEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface IFileRepository extends JpaRepository<FileEntity, UUID> {
    List<FileEntity> findAllByUser_id(UUID userId);
    List<FileEntity> findAllByFolder_id(UUID folderId);
    boolean existsByFolder_IdAndFileName(UUID folderId, String fileName);

}
