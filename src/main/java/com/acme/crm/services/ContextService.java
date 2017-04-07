package com.acme.crm.services;

import javafx.scene.control.TreeTableView;

import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.entities.UserEntity;

public interface ContextService {
    
    public UserEntity getUser();
    
    public UserEntity setUser(UserEntity user);
    
    public TreeTableView getCustomersTable();
    
    public TreeTableView setCustomersTable(TreeTableView customersTable);
    
    public TreeTableView getAppointmentsTable();
    
    public TreeTableView setAppointmentsTable(TreeTableView customersTable);
    
    public CustomerEntity getSelectedCustomer();
    
    public CustomerEntity setSelectedCustomer(CustomerEntity customer);
    
    public AppointmentEntity getSelectedAppointment();
    
    public AppointmentEntity setSelectedAppointment(AppointmentEntity appointment);
}
