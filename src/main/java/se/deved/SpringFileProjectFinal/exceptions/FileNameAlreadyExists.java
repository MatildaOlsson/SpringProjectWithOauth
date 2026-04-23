package se.deved.SpringFileProjectFinal.exceptions;

public class FileNameAlreadyExists extends RuntimeException {
    public FileNameAlreadyExists(String message) {
        super(message);
    }
}
