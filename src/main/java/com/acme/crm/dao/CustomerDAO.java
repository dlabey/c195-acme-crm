package com.acme.crm.dao;

import com.acme.crm.entities.AddressEntity;
import com.acme.crm.entities.CustomerEntity;

public interface CustomerDAO {
    
    public int createCustomer(String customerName, int addressId,
            boolean active, String createdBy) throws Exception;
}
