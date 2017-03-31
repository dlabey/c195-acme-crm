package com.acme.crm.dao;

import java.util.List;

import com.acme.crm.entities.CountryEntity;

public interface CountryDAO {
    
    public List<CountryEntity> getCountriesByCity(int cityId) throws Exception;
}