package com.acme.crm.dao;

import java.util.List;

import com.acme.crm.entities.CustomerEntity;

public interface CustomerDAO {
    
    public int createCustomer(String customerName, int addressId,
            boolean active, String createdBy) throws Exception;
    
    public List<CustomerEntity> getCustomers() throws Exception;
}
