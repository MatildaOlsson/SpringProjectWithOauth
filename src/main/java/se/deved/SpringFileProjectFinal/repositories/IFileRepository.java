package se.deved.SpringFileProjectFinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.deved.SpringFileProjectFinal.models.FileObject;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Repository
public interface IFileRepository extends JpaRepository<FileObject, UUID> {



}
