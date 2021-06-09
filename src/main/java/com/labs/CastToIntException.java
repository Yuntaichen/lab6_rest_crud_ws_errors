package com.labs;

public class CastToIntException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;

    public static CastToIntException DEFAULT_INSTANCE = new CastToIntException("Some fields values cannot be convert to integers");

    public CastToIntException(String message) {
        super(message);
    }
}