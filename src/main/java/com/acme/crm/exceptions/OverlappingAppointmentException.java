package com.acme.crm.exceptions;

public class OverlappingAppointmentException extends Exception {
    public OverlappingAppointmentException() {
        super("Appointment overlaps with another");
    }
}
