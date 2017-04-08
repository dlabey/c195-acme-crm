package com.acme.crm.entities;

import java.time.ZonedDateTime;

public class WeekEntity {
    
    private ZonedDateTime startDateTime;
    
    private ZonedDateTime endDateTime;
    
    private String week;
    
    private int weekInt;
    
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
    
    public String getWeek() {
        return this.week;
    }
    
    public String setWeek(String week) {
        this.week = week;
        
        return this.week;
    }
    
    public int getWeekAsInt() {
        return this.weekInt;
    }
    
    public int setWeekAsInt(int weekInt) {
        this.weekInt = weekInt;
        
        return this.weekInt;
    }
}
