package com.acme.crm.exceptions;

import java.util.ResourceBundle;

import com.acme.crm.services.ContextService;

public class InvalidUserException extends Exception {
    
    public InvalidUserException(String translation) {
        super(translation);
    }
    
    public InvalidUserException(ContextService contextService) throws InvalidUserException {
        ResourceBundle rb = ResourceBundle.getBundle("bundles.lang",
                contextService.getLocale());
        
        throw new InvalidUserException(rb.getString("Invalid_username_and-or_password"));
    }
}
