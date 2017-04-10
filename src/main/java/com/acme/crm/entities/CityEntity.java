package com.acme.crm.entities;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CityEntity {
    
    private int cityId;
    
    private String city;
    
    private int countryId;
    
    private CountryEntity country;
    
    private String createdBy;
    
    private Timestamp createDate;
    
    private Timestamp lastUpdate;
    
    private String lastUpdatedBy;
    
    public int getCityId() {
        return this.cityId;
    }
    
    public int setCityId(int cityId) {
        this.cityId = cityId;
        
        return this.cityId;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public String setCity(String city) {
        this.city = city;
        
        return this.city;
    }
    
    public int getCountryId() {
        return this.countryId;
    }
    
    public int setCountryId(int countryId) {
        this.countryId = countryId;
        
        return this.countryId;
    }
    
    public CountryEntity getCountry() {
        return this.country;
    }
    
    public CountryEntity setCountry(CountryEntity country) {
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
