package com.acme.crm.services;

import java.sql.SQLException;
import java.util.List;

import com.acme.crm.entities.MonthEntity;
import com.acme.crm.entities.WeekEntity;
import com.acme.crm.entities.YearEntity;

public interface DateTimeService {
    
    public List<YearEntity> getYears() throws SQLException;
    
    public List<MonthEntity> getMonths(int year);
    
    public List<WeekEntity> getWeeks(int year, int month);
}
