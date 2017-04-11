package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.inject.Inject;

import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.services.DatabaseService;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ReminderDAOImpl implements ReminderDAO {
    
    @Inject
    private DatabaseService dbService;
    
    @Override
    public PreparedStatement createReminder(AppointmentEntity appointment,
            String createdBy) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("INSERT INTO `reminder` "
                        + "(reminderDate, appointmentId, createdBy, "
                        + "createdDate) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        ZonedDateTime reminderDateTime = ZonedDateTime.ofInstant(
                appointment.getStart().toLocalDateTime(),
                OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                ZoneOffset.UTC).minusMinutes(15L);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));
        
        ps.setTimestamp(1, Timestamp.valueOf(
                reminderDateTime.toLocalDateTime()));
        ps.setInt(2, appointment.getAppointmentId());
        ps.setString(3, createdBy);
        ps.setTimestamp(4, now);
        
        return ps;
    }
    
    @Override
    public PreparedStatement updateReminder(AppointmentEntity appointment)
            throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("UPDATE `reminder` "
                        + "SET `reminderDate` = ? WHERE `appointmentId` = ?");
        ZonedDateTime reminderDateTime = ZonedDateTime.ofInstant(
                appointment.getStart().toLocalDateTime(),
                OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                ZoneOffset.UTC).minusMinutes(15L);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));
        
        ps.setTimestamp(1, Timestamp.valueOf(
                reminderDateTime.toLocalDateTime()));
        ps.setInt(2, appointment.getAppointmentId());
        
        return ps;
    }
    
    @Override
    public PreparedStatement deleteReminder(int appointmentId)
            throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("DELETE FROM `reminder` "
                        + "WHERE `appointmentId` = ?");
        
        ps.setInt(1, appointmentId);
        
        return ps;
    }
}
