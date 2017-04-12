package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.ReminderEntity;

public interface ReminderDAO {
    
    public PreparedStatement createReminder(AppointmentEntity appointment,
            String createdBy) throws SQLException;
    
    public PreparedStatement updateReminder(AppointmentEntity appointment)
            throws SQLException;
    
    public PreparedStatement deleteReminder(int appointmentId)
            throws SQLException;
    
    public List<ReminderEntity> getRemindersByUser(String createdBy)
            throws SQLException;
}
