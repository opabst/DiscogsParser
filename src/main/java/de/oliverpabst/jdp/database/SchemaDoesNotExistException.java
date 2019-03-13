package de.oliverpabst.jdp.database;

public class SchemaDoesNotExistException extends Exception {
    public SchemaDoesNotExistException() {

    }

    public SchemaDoesNotExistException(String message) {
        super(message);
    }
}
