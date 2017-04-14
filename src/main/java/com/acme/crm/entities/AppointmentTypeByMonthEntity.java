package com.acme.crm.entities;

public class AppointmentTypeByMonthEntity {
    
    private int month;
    
    private String type;
    
    private int count;
    
    public int getMonth() {
        return this.month;
    }
    
    public int setMonth(int month) {
        this.month = month;
        
        return this.month;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String setType(String type) {
        this.type = type;
        
        return this.type;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public int setCount(int count) {
        this.count = count;
        
        return this.count;
    }
}
