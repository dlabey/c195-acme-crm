package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface AddressDAO {
    
    public int createAddress(String address, String address2, int cityId,
            String postalCode, String phone, String createdBy)
            throws SQLException;
    
    public PreparedStatement updateAddress(int addressId, String address,
            String address2, int cityId, String postalCode, String phone,
            String updatedBy) throws SQLException;
    
    public PreparedStatement deleteAddress(int addressId) throws SQLException;
}
