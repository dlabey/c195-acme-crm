package com.acme.crm.services;

import javafx.scene.control.TreeTableView;

public interface CustomerService {
    
    public int createCustomer(String name, String address, String address2,
            int cityId, String postalCode, String phone, boolean active,
            String createdBy) throws Exception;
    
    public boolean editCustomer(int customerId, int addressId, String name,
            String address, String address2, int cityId, String postalCode,
            String phone, boolean active, String updatedBy) throws Exception;
    
    public boolean deleteCustomer(int customerId, int addressId)
            throws Exception;
    
    public void loadCustomers(TreeTableView customersTable) throws Exception;
}
