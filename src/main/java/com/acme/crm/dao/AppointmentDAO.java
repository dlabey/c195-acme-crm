package com.acme.crm.dao;

import java.time.LocalDateTime;

public interface AppointmentDAO {
    
    public int createAppointment(int customerId, String title,
            String description, String location, String contact, String url,
            LocalDateTime start, LocalDateTime end, String createdBy)
            throws Exception;
}
