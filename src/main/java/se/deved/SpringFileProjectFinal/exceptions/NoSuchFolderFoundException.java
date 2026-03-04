package se.deved.SpringFileProjectFinal.exceptions;

public class NoSuchFolderFoundException extends RuntimeException {
    public NoSuchFolderFoundException(String message) {
        super(message);
    }
}
