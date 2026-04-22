package se.deved.SpringFileProjectFinal.exceptions;

public class FolderNameAlreadyExists extends RuntimeException {
    public FolderNameAlreadyExists(String message) {
        super(message);
    }
}
