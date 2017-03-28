package com.acme.crm.entities;

import java.sql.Timestamp;

public class UserEntity {
    
    private int userId;
    
    private String userName;
    
    private boolean active;
    
    private String createdBy;
    
    private Timestamp createDate;
    
    private Timestamp lastUpdate;
    
    private String lastUpdatedBy;
    
    public int getUserId() {
        return this.userId;
    }
    
    public int setUserId(int userId) {
        this.userId = userId;
        
        return this.userId;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String setUserName(String userName) {
        this.userName = userName;
        
        return this.userName;
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
