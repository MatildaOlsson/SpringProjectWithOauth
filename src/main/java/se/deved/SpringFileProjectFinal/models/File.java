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

    @Lob
    private byte[] dataInBytes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public File(String fileName, byte[] bytes, User user, Folder folder) {
        this.id = UUID.randomUUID();
        this.fileName = fileName;
        this.dataInBytes = bytes;
        this.user = user;
        this.folder = folder;
    }


}
