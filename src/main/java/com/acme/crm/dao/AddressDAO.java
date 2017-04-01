package com.acme.crm.dao;

public interface AddressDAO {
    
    public int createAddress(String address, String address2, int cityId,
            String postalCode, String phone, String createdBy) throws Exception;
}
