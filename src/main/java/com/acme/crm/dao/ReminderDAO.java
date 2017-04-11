package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.acme.crm.entities.AppointmentEntity;


public interface ReminderDAO {
    
    public PreparedStatement createReminder(AppointmentEntity appointment,
            String createdBy) throws SQLException;
    
    public PreparedStatement updateReminder(AppointmentEntity appointment)
            throws SQLException;
    
    public PreparedStatement deleteReminder(int appointmentId)
            throws SQLException;
}
