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
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.entities.ReminderEntity;
import com.acme.crm.services.DatabaseService;
import org.apache.logging.log4j.LogManager;

public class ReminderDAOImpl implements ReminderDAO {
    
    private static final org.apache.logging.log4j.Logger logger =
            LogManager.getLogger(ReminderDAOImpl.class);
    
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
    
    @Override
    public List<ReminderEntity> getRemindersByUser(String createdBy)
            throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("SELECT * FROM `reminder` `r` "
                        + "JOIN `appointment` `a` ON `a`.`appointmentId` = `r`.`appointmentId` "
                        + "JOIN `customer` `c` ON `c`.`customerId` = `a`.`customerId` "
                        + "WHERE `r`.`createdBy` = ? "
                        + "AND `r`.`reminderDate` > ? "
                        + "ORDER BY `r`.`reminderDate`");
        
        Timestamp now = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));
        
        ps.setString(1, createdBy);
        ps.setTimestamp(2, now);

        ResultSet rs = ps.executeQuery();

        List<ReminderEntity> reminders = new LinkedList<>();

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
            
            ReminderEntity reminder = new ReminderEntity();
            reminder.setReminderId(rs.getInt("r.reminderId"));
            reminder.setReminderDate(rs.getTimestamp("r.reminderDate"));
            reminder.setAppointmentId(rs.getInt("r.appointmentId"));
            reminder.setAppointment(appointment);
            reminder.setCreatedBy(rs.getString("r.createdBy"));
            reminder.setCreateDate(rs.getTimestamp("r.createdDate"));

            reminders.add(reminder);
        }

        return reminders;
    }
}
