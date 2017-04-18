package com.acme.crm.services;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.entities.MonthEntity;
import com.acme.crm.entities.UserEntity;
import com.acme.crm.entities.WeekEntity;
import com.acme.crm.entities.YearEntity;
import java.util.Locale;


@Singleton
public class ContextServiceImpl implements ContextService {
    
    private Locale locale;
    
    private UserEntity user;
    
    private Stage manageStage;
    
    private TreeTableView customersTable;
    
    private Map<String, Object> appointmentsTableFilters;
    
    private TreeTableView appointmentsTable;
    
    private CustomerEntity customer;
    
    private AppointmentEntity appointment;
    
    public ContextServiceImpl() {
        this.appointmentsTableFilters = new HashMap<>();
    }
    
    @Override
    public Locale getLocale() {
        return this.locale == null ? Locale.getDefault() : this.locale;
    }
    
    @Override
    public Locale setLocale(Locale locale) {
        this.locale = locale;
        
        return this.locale;
    }
    
    @Override
    public UserEntity getUser() {
        return this.user;
    }
    
    @Override
    public UserEntity setUser(UserEntity user) {
        this.user = user;
        
        return this.user;
    }
    
    @Override
    public Stage getManageStage() {
        return this.manageStage;
    }
    
    @Override
    public Stage setManageStage(Stage manageStage) {
        this.manageStage = manageStage;
        
        return this.manageStage;
    }
    
    @Override
    public TreeTableView getCustomersTable() {
        return this.customersTable;
    }
    
    @Override
    public TreeTableView setCustomersTable(TreeTableView customersTable) {
        this.customersTable = customersTable;
        
        return this.customersTable;
    }
    
    @Override
    public Map<String, Object> getAppointmentsTableFilters() {
        return this.appointmentsTableFilters;
    }
    
    @Override
    public Map<String, Object> setAppointmentsTableFilters(YearEntity year, MonthEntity month,
            WeekEntity week) {
        this.appointmentsTableFilters.put("year", year);
        this.appointmentsTableFilters.put("month", month);
        this.appointmentsTableFilters.put("week", week);
        
        return this.appointmentsTableFilters;
    }
    
    @Override
    public TreeTableView getAppointmentsTable() {
        return this.appointmentsTable;
    }
    
    @Override
    public TreeTableView setAppointmentsTable(TreeTableView appointmentsTable) {
        this.appointmentsTable = appointmentsTable;
        
        return this.appointmentsTable;
    }
    
    @Override
    public CustomerEntity getSelectedCustomer() {
        return this.customer;
    }
    
    @Override
    public CustomerEntity setSelectedCustomer(CustomerEntity customer) {
        this.customer = customer;
        
        return this.customer;
    }
    
    @Override
    public AppointmentEntity getSelectedAppointment() {
        return this.appointment;
    }
    
    @Override
    public AppointmentEntity setSelectedAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
        
        return this.appointment;
    }
}
