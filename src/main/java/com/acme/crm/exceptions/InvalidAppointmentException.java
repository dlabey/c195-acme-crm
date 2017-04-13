package com.acme.crm.exceptions;

import com.acme.crm.enums.InvalidAppointmentTypeEnum;

public class InvalidAppointmentException extends Exception {
    
    private InvalidAppointmentException(String message) {
        super(message);
    }
    
    public InvalidAppointmentException(InvalidAppointmentTypeEnum type) throws InvalidAppointmentException {
        switch (type) {
            case INCOMPLETE:
                throw new InvalidAppointmentException("All appointment data required");
            case INVALID_TIME:
                throw new InvalidAppointmentException("Start cannot come after End");
            case OUTSIDE_BUSINESS_HOURS:
                throw new InvalidAppointmentException("Appointment outside business hours");
        }
    }
}