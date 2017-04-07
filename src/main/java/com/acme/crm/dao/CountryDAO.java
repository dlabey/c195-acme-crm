package com.acme.crm.dao;

import java.sql.SQLException;

import com.acme.crm.entities.CountryEntity;

public interface CountryDAO {
    
    public CountryEntity getCountryByCountryId(int countryId)
        throws SQLException;
}