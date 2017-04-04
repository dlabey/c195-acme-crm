package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.util.List;

import com.acme.crm.entities.CustomerEntity;

public interface CustomerDAO {
    
    public int createCustomer(String customerName, int addressId,
            boolean active, String createdBy) throws Exception;
    
    public PreparedStatement updateCustomer(int customerId, String customerName,
            boolean active, String updatedBy) throws Exception;
    
    public PreparedStatement deleteCustomer(int customerId) throws Exception;
    
    public List<CustomerEntity> getCustomers() throws Exception;
}
