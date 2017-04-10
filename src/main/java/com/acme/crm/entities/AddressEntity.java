package com.acme.crm.entities;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class AddressEntity {
    
    private int addressId;
    
    private String address;
    
    private String address2;
    
    private CityEntity city;
    
    private String postalCode;
    
    private String phone;
    
    private String createdBy;
    
    private Timestamp createDate;
    
    private Timestamp lastUpdate;
    
    private String lastUpdatedBy;
    
    public int getAddressId() {
        return this.addressId;
    }
    
    public int setAddressId(int addressId) {
        this.addressId = addressId;
        
        return this.addressId;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public String setAddress(String address) {
        this.address = address;
        
        return this.address;
    }
    
    public String getAddress2() {
        return this.address2;
    }
    
    public String setAddress2(String address2) {
        this.address2 = address2;
        
        return this.address2;
    }
    
    public CityEntity getCity() {
        return this.city;
    }
    
    public CityEntity setCity(CityEntity city) {
        this.city = city;
        
        return this.city;
    }
    
    public String getPostalCode() {
        return this.postalCode;
    }
    
    public String setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        
        return this.postalCode;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public String setPhone(String phone) {
        this.phone = phone;
        
        return this.phone;
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
}
