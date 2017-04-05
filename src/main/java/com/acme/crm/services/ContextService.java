package com.acme.crm.services;

import javafx.scene.control.TreeTableView;

import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.entities.UserEntity;

public interface ContextService {
    
    public UserEntity getUser();
    
    public UserEntity setUser(UserEntity user);
    
    public TreeTableView getCustomersTable();
    
    public TreeTableView setCustomersTable(TreeTableView customersTable);
    
    public CustomerEntity getSelectedCustomer();
    
    public CustomerEntity setSelectedCustomer(CustomerEntity customer);
}
