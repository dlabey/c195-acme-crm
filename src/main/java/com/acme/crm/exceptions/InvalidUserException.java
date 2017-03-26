package com.acme.crm.exceptions;

public class InvalidUserException extends Exception {
    public InvalidUserException() {
        super("Invalid user");
    }
}
