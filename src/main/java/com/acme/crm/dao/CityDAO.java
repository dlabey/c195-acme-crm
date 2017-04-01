package com.acme.crm.dao;

import java.util.List;

import com.acme.crm.entities.CityEntity;


public interface CityDAO {
    
    public List<CityEntity> getCities() throws Exception;
}