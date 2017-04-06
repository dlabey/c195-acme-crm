package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.inject.Inject;

import com.acme.crm.services.DatabaseService;

public class AppointmentDAOImpl implements AppointmentDAO {
    
    @Inject
    private DatabaseService dbService;
    
    @Override
    public int createAppointment(int customerId, String title,
            String description, String location, String contact, String url,
            LocalDateTime startRaw, LocalDateTime endRaw, String createdBy)
            throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("INSERT INTO `appointment` "
                        + "(customerId, title, description, location, contact, "
                        + "url, start, end, createDate, createdBy, lastUpdate, "
                        + "lastUpdateBy)"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        Timestamp start = Timestamp.valueOf(startRaw);
        Timestamp end = Timestamp.valueOf(endRaw);
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        
        
        ps.setInt(1, customerId);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, contact);
        ps.setString(6, url);
        ps.setTimestamp(7, start);
        ps.setTimestamp(8, end);
        ps.setTimestamp(9, dateTime);
        ps.setString(10, createdBy);
        ps.setTimestamp(11, dateTime);
        ps.setString(12, createdBy);
        ps.executeUpdate();
        
        ResultSet generatedKeys = ps.getGeneratedKeys();
        int newId = -1;
        
        if (generatedKeys.next()) {
            newId = generatedKeys.getInt(1);
        }
        
        return newId;
    }
}
