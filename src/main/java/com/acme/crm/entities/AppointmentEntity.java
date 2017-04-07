package com.acme.crm.entities;

import java.sql.Timestamp;

public class AppointmentEntity {
    
    private int appointmentId;
    
    private int customerId;
    
    private CustomerEntity customer;
    
    private String title;
    
    private String description;
    
    private String location;
    
    private String contact;
    
    private String url;
    
    private Timestamp start;
    
    private Timestamp end;
    
    private String createdBy;
    
    private Timestamp createDate;
    
    private Timestamp lastUpdate;
    
    private String lastUpdatedBy;
    
    public int getAppointmentId() {
        return this.appointmentId;
    }
    
    public int setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
        
        return this.appointmentId;
    }
    
    public int getCustomerId() {
        return this.customerId;
    }
    
    public int setCustomerId(int customerId) {
        this.customerId = customerId;
        
        return this.customerId;
    }
    
    public CustomerEntity getCustomer() {
        return this.customer;
    }
    
    public CustomerEntity setCustomer(CustomerEntity customer) {
        this.customer = customer;
        
        return this.customer;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String setTitle(String title) {
        this.title = title;
        
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String setDescription(String description) {
        this.description = description;
        
        return this.description;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public String setLocation(String location) {
        this.location = location;
        
        return this.location;
    }
    
    public String getContact() {
        return this.contact;
    }
    
    public String setContact(String contact) {
        this.contact = contact;
        
        return this.contact;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String setUrl(String url) {
        this.url = url;
        
        return this.url;
    }
    
    public Timestamp getStart() {
        return this.start;
    }
    
    public Timestamp setStart(Timestamp start) {
        this.start = start;
        
        return this.start;
    }
    
    public Timestamp getEnd() {
        return this.end;
    }
    
    public Timestamp setEnd(Timestamp end) {
        this.end = end;
        
        return this.end;
    }
    
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public String setCreatedBy(String username) {
        this.createdBy = username;
        
        return this.createdBy;
    }
    
    public Timestamp getCreateDate() {
        return this.createDate;
    }
    
    public Timestamp setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
        
        return this.createDate;
    }
    
    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }
    
    public Timestamp setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        
        return this.lastUpdate;
    }
    
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    
    public String setLastUpdatedBy(String username) {
        this.lastUpdatedBy = username;
        
        return this.lastUpdatedBy;
    }
}
