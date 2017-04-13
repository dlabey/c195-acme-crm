package com.acme.crm.exceptions;

public class InvalidCustomerException extends Exception {
    public InvalidCustomerException() {
        super("Invalid customer data (all data is required)");
    }
}
