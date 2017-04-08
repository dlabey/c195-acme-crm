package com.acme.crm.entities;

import java.time.ZonedDateTime;

public class YearEntity {
    
    private ZonedDateTime startDateTime;
    
    private ZonedDateTime endDateTime;
    
    private String year;
    
    private int yearAsInt;
    
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
    
    public String getYear() {
        return this.year;
    }
    
    public String setYear(String year) {
        this.year = year;
        
        return this.year;
    }
    
    public int getYearAsInt() {
        return this.yearAsInt;
    }
    
    public int setYearAsInt(int yearAsInt) {
        this.yearAsInt = yearAsInt;
        
        return this.yearAsInt;
    }
}
