package com.labs;

public class MarkFieldValueException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;

    public static MarkFieldValueException DEFAULT_INSTANCE = new MarkFieldValueException("Field 'mark'" +
            " Field 'mark' should have one of these values: 'неудовлетворительно', " +
            "'удовлетворительно', 'хорошо', 'отлично'");

    public MarkFieldValueException(String message) {
        super(message);
    }
}