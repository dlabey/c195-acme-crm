package com.acme.crm.entities;

import java.sql.Timestamp;

public class UserEntity {
    
    private String userName;
    
    private String password;
    
    private boolean active;
    
    private String createdBy;
    
    private Timestamp createDate;
    
    private Timestamp lastUpdate;
    
    private String lastUpdatedBy;
    
    public String getUserName() {
        return this.userName;
    }
    
    public String setUserName(String userName) {
        this.userName = userName;
        
        return this.userName;
    }
}
