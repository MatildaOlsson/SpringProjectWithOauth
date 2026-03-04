package se.deved.SpringFileProjectFinal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

    @Getter
    @Setter
    @NoArgsConstructor

    @Entity(name = "folder")
    public class Folder {

        @Id
        private UUID id = UUID.randomUUID();

        @Column
        private String folderName;

        @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<FileObject> fileList = new ArrayList<>();

    public Folder(String folderName) {
        this.folderName = folderName;
        this.id = UUID.randomUUID();
    }
}
