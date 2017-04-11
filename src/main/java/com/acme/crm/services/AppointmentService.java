package com.acme.crm.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.scene.control.TreeTableView;

public interface AppointmentService {
    
    public int createAppointment(int customerId, String title,
            String description, String location, String contact, String url,
            LocalDateTime startRaw, LocalDateTime endRaw, String createdBy)
            throws SQLException;
    
    public boolean editAppointment(int appointmentId, int customerId,
            String title, String description, String location, String contact,
            String url, LocalDateTime startRaw, LocalDateTime endRaw,
            String updatedBy) throws SQLException;
    
    public boolean deleteAppointment(int appointmentId) throws SQLException;
    
    public void loadAppointments(TreeTableView appointmentsTable)
            throws SQLException;
}
