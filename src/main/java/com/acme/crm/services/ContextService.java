package com.acme.crm.services;

import java.util.Locale;
import java.util.Map;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.entities.MonthEntity;
import com.acme.crm.entities.UserEntity;
import com.acme.crm.entities.WeekEntity;
import com.acme.crm.entities.YearEntity;

public interface ContextService {
    
    public Stage getLoginStage();
    
    public Stage setLoginStage(Stage loginStage);
    
    public Locale getLocale();
    
    public Locale setLocale(Locale locale);
    
    public UserEntity getUser();
    
    public UserEntity setUser(UserEntity user);
    
    public TreeTableView getCustomersTable();
    
    public TreeTableView setCustomersTable(TreeTableView customersTable);
    
    public Map<String, Object> getAppointmentsTableFilters();
    
    public Map setAppointmentsTableFilters(YearEntity year, MonthEntity month,
            WeekEntity week);
    
    public TreeTableView getAppointmentsTable();
    
    public TreeTableView setAppointmentsTable(TreeTableView customersTable);
    
    public CustomerEntity getSelectedCustomer();
    
    public CustomerEntity setSelectedCustomer(CustomerEntity customer);
    
    public AppointmentEntity getSelectedAppointment();
    
    public AppointmentEntity setSelectedAppointment(AppointmentEntity appointment);
}
