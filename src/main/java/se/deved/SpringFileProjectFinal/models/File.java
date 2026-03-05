package se.deved.SpringFileProjectFinal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity(name = "files")
@Getter
@Setter
@NoArgsConstructor
public class File {

    @Id
    private UUID id = UUID.randomUUID();

    @Column
    private String fileName;

    @Column
    private Date savedAt;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @Lob
    private byte[] dataInBytes;

    public File(String fileName, Folder folderName, byte[] bytes) {
        this.fileName = fileName;
        this.folder = folderName;
        this.dataInBytes = bytes;
        this.id = UUID.randomUUID();
        this.savedAt = new Date();
    }


}
