package com.acme.crm.services;

import javax.inject.Singleton;
import javafx.scene.control.TreeTableView;

import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.entities.UserEntity;

@Singleton
public class ContextServiceImpl implements ContextService {
    
    private UserEntity user;
    
    private TreeTableView customersTable;
    
    private CustomerEntity customer;
    
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
    public TreeTableView getCustomersTable() {
        return this.customersTable;
    }
    
    @Override
    public TreeTableView setCustomersTable(TreeTableView customersTable) {
        this.customersTable = customersTable;
        
        return this.customersTable;
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
}
