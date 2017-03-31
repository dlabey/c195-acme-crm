package com.acme.crm.entities;

import java.sql.Timestamp;

public class CityEntity {
    
    private int cityId;
    
    private String city;
    
    private int countryId;
    
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
