package com.acme.crm.dao;

import java.sql.SQLException;
import java.util.List;

import com.acme.crm.entities.CityEntity;

public interface CityDAO {
    
    public List<CityEntity> getCities() throws SQLException;
}