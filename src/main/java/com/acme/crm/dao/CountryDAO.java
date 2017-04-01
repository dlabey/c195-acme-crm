package com.acme.crm.dao;

import com.acme.crm.entities.CountryEntity;

public interface CountryDAO {
    
    public CountryEntity getCountryByCountryId(int countryId) throws Exception;
}