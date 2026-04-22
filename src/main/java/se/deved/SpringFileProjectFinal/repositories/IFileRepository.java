package se.deved.SpringFileProjectFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.deved.SpringFileProjectFinal.models.FileEntity;

import java.util.UUID;

@Repository
public interface IFileRepository extends JpaRepository<FileEntity, UUID> {



}
