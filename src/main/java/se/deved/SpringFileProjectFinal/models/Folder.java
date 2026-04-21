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
    @Entity(name = "folders")
    public class Folder {

        @Id
        private UUID id = UUID.randomUUID();

        @Column
        private String folderName;

        @ManyToOne(fetch = FetchType.LAZY)
        private User user;

        @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<File> fileList = new ArrayList<>();

    public Folder(String folderName, User user) {
        this.id = UUID.randomUUID();
        this.folderName = folderName;
        this.user = user;
        this.fileList = new ArrayList<>();
    }
}
