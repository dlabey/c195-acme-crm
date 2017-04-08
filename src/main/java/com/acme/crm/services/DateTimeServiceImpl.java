package com.acme.crm.services;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.LinkedList;
import javax.inject.Inject;

import com.acme.crm.entities.MonthEntity;
import com.acme.crm.entities.WeekEntity;
import com.acme.crm.entities.YearEntity;

public class DateTimeServiceImpl implements DateTimeService {

    @Inject
    private DatabaseService dbService;

    public List<YearEntity> getYears() throws SQLException {
        Statement stmnt = this.dbService.getConnection().createStatement();

        ResultSet rs = stmnt.executeQuery("SELECT MIN(`start`) AS `min`, "
                + "MAX(`end`) AS `max` FROM `appointment`");

        List<YearEntity> years = new LinkedList<>();
        
        if (rs.next()) {
            int min = rs.getInt("min");
            int max = rs.getInt("max");

            for (int i = min; i <= max; i++) {
                LocalDate startOfYear = LocalDate.ofYearDay(i, 1);
                ZonedDateTime startDateTime = startOfYear
                        .atStartOfDay(ZoneOffset.UTC);
                ZonedDateTime endDateTime = startOfYear.with(lastDayOfYear())
                        .atTime(LocalTime.MAX).atZone(ZoneOffset.UTC);

                YearEntity year = new YearEntity();
                year.setYear(String.valueOf(i));
                year.setYearAsInt(i);
                year.setStartDateTime(startDateTime);
                year.setEndDateTime(endDateTime);

                years.add(year);
            }
        }
        
        return years;
    }
    
    public List<MonthEntity> getMonths(int year) {
        int min = 1;
        int max = 12;
        
        List<MonthEntity> months = new LinkedList<>();
        
        for (int i = min; i <= max; i++) {
            LocalDate startOfMonth = LocalDate.of(year, i, 1);
            ZonedDateTime startDateTime = startOfMonth
                    .atStartOfDay(ZoneOffset.UTC);
            ZonedDateTime endDateTime = startOfMonth.with(lastDayOfMonth())
                    .atTime(LocalTime.MAX).atZone(ZoneOffset.UTC);
            String monthName = new DateFormatSymbols().getMonths()[i-1];
            
            MonthEntity month = new MonthEntity();
            month.setMonth(monthName); // TODO: i18n
            month.setMonthAsInt(i);
            month.setStartDateTime(startDateTime);
            month.setEndDateTime(endDateTime);
            
            months.add(month);
        }
        
        return months;
    }
    
    public List<WeekEntity> getWeeks(int year, int month) {
        int weekInt = 1;
        
        List<WeekEntity> weeks = new LinkedList<>();
        
        LocalDate startOfWeek = LocalDate.of(year, month, 1);
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        
        while (startOfWeek.getMonthValue() == month) {
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            
            ZonedDateTime startDateTime = cal.getTime().toInstant()
                    .atZone(ZoneOffset.systemDefault()).toLocalDate()
                    .atStartOfDay(ZoneOffset.UTC);
            
            String weekStart = new SimpleDateFormat("dd/MM/yyyy").format(startDateTime);
            
            cal.add(Calendar.DAY_OF_WEEK, 6);
            
            ZonedDateTime endDateTime = cal.getTime().toInstant()
                    .atZone(ZoneOffset.systemDefault()).toLocalDate()
                    .atTime(LocalTime.MAX).atZone(ZoneOffset.UTC);
            
            String weekEnd = new SimpleDateFormat("dd/MM/yyyy").format(endDateTime);
            
            WeekEntity week = new WeekEntity();
            week.setWeek(String.format("%s - %s", weekStart, weekEnd));
            week.setWeekAsInt(weekInt);
            week.setStartDateTime(startDateTime);
            week.setEndDateTime(endDateTime);
            
            weeks.add(week);
            
            cal.add(Calendar.DAY_OF_WEEK, 1);
            weekInt++;
        }
        
        return weeks;
    }
}
