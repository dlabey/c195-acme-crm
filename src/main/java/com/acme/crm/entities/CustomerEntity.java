package com.acme.crm.entities;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CustomerEntity {
    
    private int customerId;
    
    private String customerName;
    
    private int addressId;
    
    private AddressEntity address;
    
    private boolean active;
    
    private String createdBy;
    
    private Timestamp createDate;
    
    private Timestamp lastUpdate;
    
    private String lastUpdatedBy;
    
    public int getCustomerId() {
        return this.customerId;
    }
    
    public int setCustomerId(int customerId) {
        this.customerId = customerId;
        
        return this.customerId;
    }
    
    public String getCustomerName() {
        return this.customerName;
    }
    
    public String setCustomerName(String customerName) {
        this.customerName = customerName;
        
        return this.customerName;
    }
    
    public int getAddressId() {
        return this.addressId;
    }
    
    public int setAddressId(int addressId) {
        this.addressId = addressId;
        
        return this.addressId;
    }
    
    public AddressEntity getAddress() {
        return this.address;
    }
    
    public AddressEntity setAddress(AddressEntity address) {
        this.address = address;
        
        return this.address;
    }
    
    public boolean getActive() {
        return this.active;
    }
    
    public boolean setActive(boolean active) {
        this.active = active;
        
        return this.active;
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
    
    public Timestamp getLastUpdate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(
                this.lastUpdate.toLocalDateTime(), ZoneOffset.UTC,
                ZoneOffset.systemDefault());
        
        return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
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
    
    @Override
    public int hashCode() {
        return this.customerId;
    }
    
    @Override
    public boolean equals(Object object) {
        CustomerEntity customer = (CustomerEntity) object;
        
        return customer == null ? false : this.customerId == customer.customerId;
    }
}
