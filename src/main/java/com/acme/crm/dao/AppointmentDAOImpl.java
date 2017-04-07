package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.inject.Inject;

import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.services.DatabaseService;
import java.util.LinkedList;
import java.util.List;

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
    
    @Override
    public List<AppointmentEntity> getAppointments() throws SQLException {
        Statement stmnt = this.dbService.getConnection().createStatement();
        
        ResultSet rs = stmnt.executeQuery("SELECT * FROM `appointment` `a` "
                + "JOIN `customer` `c` ON `c`.`customerId` = `a`.`customerId`");
        
        List<AppointmentEntity> appointments = new LinkedList<>();
        
        while (rs.next()) {
            CustomerEntity customer = new CustomerEntity();
            customer.setCustomerId(rs.getInt("c.customerId"));
            customer.setCustomerName(rs.getString("c.customerName"));
            customer.setAddressId(rs.getInt("c.addressId"));
            customer.setActive(rs.getBoolean("c.active"));
            customer.setCreateDate(rs.getTimestamp("c.createDate"));
            customer.setCreatedBy(rs.getString("c.createdBy"));
            customer.setLastUpdate(rs.getTimestamp("c.lastUpdate"));
            customer.setLastUpdatedBy(rs.getString("c.lastUpdateBy"));
            
            AppointmentEntity appointment = new AppointmentEntity();
            appointment.setAppointmentId(rs.getInt("a.appointmentId"));
            appointment.setCustomer(customer);
            appointment.setTitle(rs.getString("a.title"));
            appointment.setDescription(rs.getString("a.description"));
            appointment.setLocation(rs.getString("a.location"));
            appointment.setContact(rs.getString("a.contact"));
            appointment.setUrl(rs.getString("a.url"));
            appointment.setStart(rs.getTimestamp("a.start"));
            appointment.setEnd(rs.getTimestamp("a.end"));
            appointment.setCreateDate(rs.getTimestamp("c.createDate"));
            appointment.setCreatedBy(rs.getString("c.createdBy"));
            appointment.setLastUpdate(rs.getTimestamp("c.lastUpdate"));
            appointment.setLastUpdatedBy(rs.getString("c.lastUpdateBy"));
            
            appointments.add(appointment);
        }
        
        return appointments;
    }
    
    @Override
    public void updateAppointment(int appointmentId, int customerId,
            String title, String description, String location, String contact,
            String url, LocalDateTime start, LocalDateTime end,
            String updatedBy) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("UPDATE `appointment` "
                        + "SET `customerId` = ?, "
                        + "`title` = ?, "
                        + "`description` = ?, "
                        + "`location` = ?, "
                        + "`contact` = ?, "
                        + "`url` = ?, "
                        + "`start` = ?, "
                        + "`end` = ?, "
                        + "`lastUpdate` = ?, "
                        + "`lastUpdateBy` = ? "
                        + "WHERE `appointmentId` = ?");
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        
        ps.setInt(1, customerId);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, contact);
        ps.setString(6, url);
        ps.setTimestamp(7, Timestamp.valueOf(start));
        ps.setTimestamp(8, Timestamp.valueOf(end));
        ps.setTimestamp(9, dateTime);
        ps.setString(10, updatedBy);
        ps.setInt(11, appointmentId);
        ps.executeUpdate();
    }
    
    @Override
    public void deleteAppointment(int appointmentId) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("DELETE FROM `appointment` "
                        + "WHERE `appointmentId` = ?");
        
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }
}
