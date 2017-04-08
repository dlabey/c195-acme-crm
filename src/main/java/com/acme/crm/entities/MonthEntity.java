package com.acme.crm.entities;

import java.time.ZonedDateTime;

public class MonthEntity {
    
    private ZonedDateTime startDateTime;
    
    private ZonedDateTime endDateTime;
    
    private String month;
    
    private int monthInt;
    
    public ZonedDateTime getStartDateTime() {
        return this.startDateTime;
    }
    
    public ZonedDateTime setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
        
        return this.startDateTime;
    }
    
    public ZonedDateTime getEndDateTime() {
        return this.endDateTime;
    }
    
    public ZonedDateTime setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
        
        return this.endDateTime;
    }
    
    public String getMonth() {
        return this.month;
    }
    
    public String setMonth(String month) {
        this.month = month;
        
        return this.month;
    }
    
    public int getMonthAsInt() {
        return this.monthInt;
    }
    
    public int setMonthAsInt(int monthInt) {
        this.monthInt = monthInt;
        
        return this.monthInt;
    }
}
