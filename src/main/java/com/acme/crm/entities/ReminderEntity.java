package com.acme.crm.entities;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ReminderEntity {
    
    private int reminderId;
    
    private Timestamp reminderDate;
    
    private int appointmentId;
    
    private String createdBy;
    
    private Timestamp createDate;
    
    public int getReminderId() {
        return this.reminderId;
    }
    
    public int setReminderId(int reminderId) {
        this.reminderId = reminderId;
        
        return this.reminderId;
    }
    
    public Timestamp getReminderDate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(
                this.reminderDate.toLocalDateTime(), ZoneOffset.UTC,
                ZoneOffset.systemDefault());
        
        return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
    }
    
    public Timestamp setReminderDate(Timestamp createDate) {
        this.reminderDate = createDate;
        
        return this.reminderDate;
    }
    
    public int getAppointmentId() {
        return this.appointmentId;
    }
    
    public int setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
        
        return this.appointmentId;
    }
    
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public String setCreatedBy(String username) {
        this.createdBy = username;
        
        return this.createdBy;
    }
    
    public Timestamp getCreateDate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(
                this.createDate.toLocalDateTime(), ZoneOffset.UTC,
                ZoneOffset.systemDefault());
        
        return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
    }
    
    public Timestamp setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
        
        return this.createDate;
    }
}
