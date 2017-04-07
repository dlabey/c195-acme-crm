package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.acme.crm.entities.CustomerEntity;

public interface CustomerDAO {
    
    public int createCustomer(String customerName, int addressId,
            boolean active, String createdBy) throws SQLException;
    
    public PreparedStatement updateCustomer(int customerId, String customerName,
            boolean active, String updatedBy) throws SQLException;
    
    public PreparedStatement deleteCustomer(int customerId) throws SQLException;
    
    public List<CustomerEntity> getCustomers() throws SQLException;
}
