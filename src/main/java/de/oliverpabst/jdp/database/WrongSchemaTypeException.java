package de.oliverpabst.jdp.database;

public class WrongSchemaTypeException extends Exception {

    public WrongSchemaTypeException() {

    }

    public WrongSchemaTypeException(String message) {
        super(message);
    }
}
