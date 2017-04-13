package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.acme.crm.entities.AppointmentEntity;

public interface AppointmentDAO {
    
    public PreparedStatement createAppointment(int customerId, String title,
            String description, String location, String contact, String url,
            LocalDateTime start, LocalDateTime end, String createdBy)
            throws SQLException;
    
    public PreparedStatement updateAppointment(int appointmentId, int customerId,
            String title, String description, String location, String contact,
            String url, LocalDateTime start, LocalDateTime end,
            String updatedBy) throws SQLException;
    
    public PreparedStatement deleteAppointment(int appointmentId)
            throws SQLException;
    
    public List<AppointmentEntity> getAppointments(Timestamp start,
            Timestamp end) throws SQLException;
    
    public AppointmentEntity getAppointment(int appointmentId)
            throws SQLException;
    
    public boolean isOverlappingAppointment(int appointmentId, int customerId,
            LocalDateTime startRaw, LocalDateTime endRaw, String createdBy)
            throws SQLException;
}
