package com.acme.crm.entities;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CountryEntity {
    
    private int countryid;
    
    private String country;
    
    private String createdBy;
    
    private Timestamp createDate;
    
    private Timestamp lastUpdate;
    
    private String lastUpdatedBy;
    
    public int getCountryId() {
        return this.countryid;
    }
    
    public int setCountryId(int userId) {
        this.countryid = userId;
        
        return this.countryid;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    public String setCountry(String country) {
        this.country = country;
        
        return this.country;
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
