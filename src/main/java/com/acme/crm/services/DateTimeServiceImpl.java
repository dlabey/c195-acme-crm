package com.acme.crm.services;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.LinkedList;
import javax.inject.Inject;

import com.acme.crm.entities.MonthEntity;
import com.acme.crm.entities.WeekEntity;
import com.acme.crm.entities.YearEntity;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * DateTimeServiceImpl
 * This class uses datetime ranges for working with different time zones
 * @author darren
 */
public class DateTimeServiceImpl implements DateTimeService {

    @Inject
    private DatabaseService dbService;

    @Override
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
                
                ZonedDateTime startDateTime = ZonedDateTime.ofInstant(
                        startOfYear.atStartOfDay(),
                        OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                        ZoneOffset.UTC);
                ZonedDateTime endDateTime = ZonedDateTime.ofInstant(
                        startOfYear.with(lastDayOfYear()).atTime(LocalTime.MAX),
                        OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                        ZoneOffset.UTC);

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
    
    @Override
    public List<MonthEntity> getMonths(int year) {
        int min = 1;
        int max = 12;
        
        List<MonthEntity> months = new LinkedList<>();
        
        for (int i = min; i <= max; i++) {
            LocalDate startOfMonth = LocalDate.of(year, i, 1);
            
            ZonedDateTime startDateTime = ZonedDateTime.ofInstant(
                        startOfMonth.atStartOfDay(),
                        OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                        ZoneOffset.UTC);
            ZonedDateTime endDateTime = ZonedDateTime.ofInstant(
                        startOfMonth.with(lastDayOfMonth())
                        .atTime(LocalTime.MAX),
                        OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                        ZoneOffset.UTC);
            
            String monthName = new DateFormatSymbols().getMonths()[i-1];
            
            MonthEntity month = new MonthEntity();
            month.setMonth(monthName);
            month.setMonthAsInt(i);
            month.setStartDateTime(startDateTime);
            month.setEndDateTime(endDateTime);
            
            months.add(month);
        }
        
        return months;
    }
    
    @Override
    public List<WeekEntity> getWeeks(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        
        List<WeekEntity> weeks = new LinkedList<>();
        
        YearMonth yearMonth = YearMonth.of(year, month);
        
        // count the end of the weeks
        // add 1 to account for the partial week if the first day is a Sunday
        // otherwise add 2 for the partial weeks
        int partialWeeks = 2;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            partialWeeks = 1;
        }
        
        LocalDate startOfMonth = yearMonth.atDay(1)
                .with(nextOrSame(DayOfWeek.SUNDAY));
        
        int weeksInMonth = (int) ChronoUnit.WEEKS
                .between(startOfMonth, yearMonth.atEndOfMonth()) + partialWeeks;
        
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("MM/dd/yyyy");
        
        for (int i = 1; i <= weeksInMonth; i++) {
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            
            LocalDate startDate = cal.getTime().toInstant()
                    .atZone(ZoneOffset.UTC).toLocalDate();
            
            String weekStart = startDate.atStartOfDay()
                    .format(dateTimeFormatter);
            
            ZonedDateTime startDateTime = ZonedDateTime.ofInstant(
                    startDate.atStartOfDay(),
                    OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                    ZoneOffset.UTC);
            
            cal.add(Calendar.DAY_OF_WEEK, 6);
            
            LocalDate endDate = cal.getTime().toInstant()
                    .atZone(ZoneOffset.UTC).toLocalDate();
            
            String weekEnd = endDate.atTime(LocalTime.MAX)
                    .format(dateTimeFormatter);
            
            ZonedDateTime endDateTime = ZonedDateTime.ofInstant(
                    endDate.atTime(LocalTime.MAX),
                    OffsetDateTime.now(ZoneId.systemDefault()).getOffset(),
                    ZoneOffset.UTC);
            
            WeekEntity week = new WeekEntity();
            week.setWeek(String.format("%s - %s", weekStart, weekEnd));
            week.setWeekAsInt(i);
            week.setStartDateTime(startDateTime);
            week.setEndDateTime(endDateTime);
            
            weeks.add(week);
            
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        
        return weeks;
    }
}
