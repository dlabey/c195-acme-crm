package com.acme.crm.dao;

import java.util.Set;

import com.acme.crm.entities.CityEntity;


public interface CityDAO {
    
    public Set<CityEntity> getCities() throws Exception;
}