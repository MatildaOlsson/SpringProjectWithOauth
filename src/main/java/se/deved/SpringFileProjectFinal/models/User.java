package se.deved.SpringFileProjectFinal.models;

import jakarta.persistence.Id;

import java.util.UUID;

public class User {

    @Id
    private final UUID id = UUID.randomUUID();

    private String username;
    private String password;

    private String oidcId;
    private String oidcProvider;
}
