package com.acme.crm.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.acme.crm.entities.AppointmentEntity;


public interface AppointmentDAO {
    
    public int createAppointment(int customerId, String title,
            String description, String location, String contact, String url,
            LocalDateTime start, LocalDateTime end, String createdBy)
            throws SQLException;
    
    public void updateAppointment(int appointmentId, int customerId,
            String title, String description, String location, String contact,
            String url, LocalDateTime start, LocalDateTime end,
            String updatedBy) throws SQLException;
    
    public void deleteAppointment(int appointmentId) throws SQLException;
    
    public List<AppointmentEntity> getAppointments() throws SQLException;
}
