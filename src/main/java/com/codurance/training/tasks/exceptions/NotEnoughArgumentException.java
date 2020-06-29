package com.codurance.training.tasks.exceptions;

public class NotEnoughArgumentException extends Exception {

    public static String ERROR_MESSAGE = "Not enough argument.";

    public NotEnoughArgumentException() {
        super(ERROR_MESSAGE);
    }
}
