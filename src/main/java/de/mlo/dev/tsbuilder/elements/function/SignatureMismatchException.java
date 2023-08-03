package de.mlo.dev.tsbuilder.elements.function;

public class SignatureMismatchException extends RuntimeException{
    public SignatureMismatchException(String message) {
        super(message);
    }
}
