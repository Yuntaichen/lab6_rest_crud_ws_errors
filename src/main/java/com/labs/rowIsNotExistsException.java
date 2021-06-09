package com.labs;

public class rowIsNotExistsException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;

    public static rowIsNotExistsException DEFAULT_INSTANCE = new rowIsNotExistsException("Row with this ID does not exist.");

    public rowIsNotExistsException(String message) {
        super(message);
    }
}