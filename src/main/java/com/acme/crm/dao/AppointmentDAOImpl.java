package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;

import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.AppointmentTypeByMonthEntity;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.services.DatabaseService;

public class AppointmentDAOImpl implements AppointmentDAO {

    @Inject
    private DatabaseService dbService;

    @Override
    public PreparedStatement createAppointment(int customerId, String title,
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
        ZonedDateTime start = ZonedDateTime.ofInstant(startRaw,
                OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                ZoneOffset.UTC);
        ZonedDateTime end = ZonedDateTime.ofInstant(endRaw,
                OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                ZoneOffset.UTC);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));

        ps.setInt(1, customerId);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, contact);
        ps.setString(6, url);
        ps.setTimestamp(7, Timestamp.valueOf(start.toLocalDateTime()));
        ps.setTimestamp(8, Timestamp.valueOf(end.toLocalDateTime()));
        ps.setTimestamp(9, now);
        ps.setString(10, createdBy);
        ps.setTimestamp(11, now);
        ps.setString(12, createdBy);
        
        return ps;
    }

    @Override
    public PreparedStatement updateAppointment(int appointmentId, int customerId,
            String title, String description, String location, String contact,
            String url, LocalDateTime startRaw, LocalDateTime endRaw,
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
        ZonedDateTime start = ZonedDateTime.ofInstant(startRaw,
                OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                ZoneOffset.UTC);
        ZonedDateTime end = ZonedDateTime.ofInstant(endRaw,
                OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                ZoneOffset.UTC);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));

        ps.setInt(1, customerId);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, contact);
        ps.setString(6, url);
        ps.setTimestamp(7, Timestamp.valueOf(start.toLocalDateTime()));
        ps.setTimestamp(8, Timestamp.valueOf(end.toLocalDateTime()));
        ps.setTimestamp(9, now);
        ps.setString(10, updatedBy);
        ps.setInt(11, appointmentId);
        
        return ps;
    }

    @Override
    public PreparedStatement deleteAppointment(int appointmentId)
            throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("DELETE FROM `appointment` "
                        + "WHERE `appointmentId` = ?");
        
        ps.setInt(1, appointmentId);
        
        return ps;
    }
    
    @Override
    public List<AppointmentEntity> getAppointments(Timestamp start,
            Timestamp end) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("SELECT * FROM `appointment` `a` "
                        + "JOIN `customer` `c` ON `c`.`customerId` = `a`.`customerId` "
                        + "WHERE `a`.`start` BETWEEN ? AND ? "
                        + "OR `a`.`end` BETWEEN ? AND ? "
                        + "ORDER BY `a`.`start`, `a`.`end`");

        if (start == null) {
            start = Timestamp.valueOf("1000-01-01 01:01:01");
        }

        if (end == null) {
            end = Timestamp.valueOf("9999-12-31 01:01:01");
        }

        ps.setTimestamp(1, start);
        ps.setTimestamp(2, end);
        ps.setTimestamp(3, start);
        ps.setTimestamp(4, end);

        ResultSet rs = ps.executeQuery();

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
    public List<AppointmentEntity> getAppointmentScheduleByUserName(
        String userName) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("SELECT * FROM `appointment` `a` "
                        + "JOIN `customer` `c` ON `c`.`customerId` = `a`.`customerId` "
                        + "WHERE `a`.`createdBy` = COALESCE(?, `a`.`createdBy`) "
                        + "ORDER BY `a`.`start`, `a`.`end`");

        ps.setString(1, userName);

        ResultSet rs = ps.executeQuery();

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
    public List<AppointmentEntity> getAppointmentScheduleByCustomerId(
        String customerId) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("SELECT * FROM `appointment` `a` "
                        + "JOIN `customer` `c` ON `c`.`customerId` = `a`.`customerId` "
                        + "WHERE `a`.`customerId` = COALESCE(?, `a`.`customerId`) "
                        + "ORDER BY `a`.`start`, `a`.`end`");
        
        ps.setString(1, customerId);

        ResultSet rs = ps.executeQuery();

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
    public AppointmentEntity getAppointment(int appointmentId)
            throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("SELECT * FROM `appointment` `a` "
                        + "JOIN `customer` `c` ON `c`.`customerId` = `a`.`customerId` "
                        + "WHERE `a`.`appointmentId` = ?");
        
        ps.setInt(1, appointmentId);
        
        ResultSet rs = ps.executeQuery();
        
        AppointmentEntity appointment = null;
        
        if (rs.next()) {
            CustomerEntity customer = new CustomerEntity();
            customer.setCustomerId(rs.getInt("c.customerId"));
            customer.setCustomerName(rs.getString("c.customerName"));
            customer.setAddressId(rs.getInt("c.addressId"));
            customer.setActive(rs.getBoolean("c.active"));
            customer.setCreateDate(rs.getTimestamp("c.createDate"));
            customer.setCreatedBy(rs.getString("c.createdBy"));
            customer.setLastUpdate(rs.getTimestamp("c.lastUpdate"));
            customer.setLastUpdatedBy(rs.getString("c.lastUpdateBy"));

            appointment = new AppointmentEntity();
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
        }
        
        return appointment;
    }
    
    @Override
    public boolean isOverlappingAppointment(int appointmentId, int customerId,
            LocalDateTime startRaw, LocalDateTime endRaw, String createdBy)
            throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("SELECT * FROM `appointment` "
                        + "WHERE `appointmentId` != ? "
                        + "AND (`customerId` = ? OR `createdBy` = ?) "
                        + "AND `start` >= ? AND `end` <= ?");
        ZonedDateTime start = ZonedDateTime.ofInstant(startRaw,
                OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                ZoneOffset.UTC);
        ZonedDateTime end = ZonedDateTime.ofInstant(endRaw,
                OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                ZoneOffset.UTC);
        
        ps.setInt(1, appointmentId);
        ps.setInt(2, customerId);
        ps.setString(3, createdBy);
        ps.setTimestamp(4, Timestamp.valueOf(start.toLocalDateTime()));
        ps.setTimestamp(5, Timestamp.valueOf(end.toLocalDateTime()));
        
        ResultSet rs = ps.executeQuery();
        
        boolean overlapping = false;
        
        if (rs.next()) {
            overlapping = true;
        }
        
        return overlapping;
    }
    
    @Override
    public List<AppointmentTypeByMonthEntity>
        getAppointmentTypesByMonth(String offset) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("SELECT "
                        + "MONTH(CONVERT_TZ(`a`.`start`, '+00:00', ?)) AS `month`, "
                        + "`ci`.`city` AS `type`, "
                        + "COUNT(TRUE) AS `count` "
                        + "FROM `appointment` `a` "
                        + "JOIN `customer` `c` ON `c`.`customerId` = `a`.`customerId` "
                        + "JOIN `address` `ad` ON `ad`.`addressId` = `c`.`addressId` "
                        + "JOIN `city` `ci` ON `ci`.`cityId` = `ad`.`cityId` "
                        + "GROUP BY `month`, `type` "
                        + "ORDER BY `month`, `count`");
        
        ps.setString(1, offset);
        
        ResultSet rs = ps.executeQuery();

        List<AppointmentTypeByMonthEntity> appointmentTypesByMonth =
                new LinkedList<>();

        while (rs.next()) {
            AppointmentTypeByMonthEntity appointmentTypeByMonth = 
                new AppointmentTypeByMonthEntity();
            appointmentTypeByMonth.setMonth(rs.getInt("month"));
            appointmentTypeByMonth.setType(rs.getString("type"));
            appointmentTypeByMonth.setCount(rs.getInt("count"));
            
            appointmentTypesByMonth.add(appointmentTypeByMonth);
        }
        
        return appointmentTypesByMonth;
    }
}
