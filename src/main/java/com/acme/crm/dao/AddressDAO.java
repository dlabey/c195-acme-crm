package com.acme.crm.dao;

import java.sql.PreparedStatement;

public interface AddressDAO {
    
    public int createAddress(String address, String address2, int cityId,
            String postalCode, String phone, String createdBy) throws Exception;
    
    public PreparedStatement updateAddress(int addressId, String address,
            String address2, int cityId, String postalCode, String phone,
            String updatedBy) throws Exception;
    
    public PreparedStatement deleteAddress(int addressId) throws Exception;
}
