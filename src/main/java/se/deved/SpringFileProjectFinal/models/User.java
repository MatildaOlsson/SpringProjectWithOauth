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
@Entity (name = "users")
public class User {
    @Id
    private UUID id = UUID.randomUUID();

    private String username;
    private String password;

    private String oidcId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Folder> folderList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List <FileEntity> fileList = new ArrayList<>();

    public User(String username,String password) {
        this.username = username;
        this.password = password;
        this.folderList = new ArrayList<>();
        this.fileList = new ArrayList<>();
    }
}
