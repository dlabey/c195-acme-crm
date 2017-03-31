package com.acme.crm.dao;

import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;
import javax.inject.Inject;

import com.acme.crm.entities.CityEntity;
import com.acme.crm.entities.UserEntity;
import com.acme.crm.exceptions.InvalidUserException;
import com.acme.crm.services.DatabaseService;

import java.sql.SQLException;



public class CityDAOImpl implements CityDAO {
    
    @Inject
    private DatabaseService dbService;
    
    @Override
    public Set<CityEntity> getCities() throws SQLException {
        Statement stmnt = this.dbService.getConnection().createStatement();
        
        ResultSet rs = stmnt.executeQuery("SELECT * FROM `city` ORDER BY `city`");
        
        Set<CityEntity> cities = new TreeSet<>();
        
        while (rs.next()) {
            CityEntity city = new CityEntity();
            city.setCityId(rs.getInt("cityId"));
            city.setCity(rs.getString("city"));
            city.setCountryId(rs.getInt("countryId"));
            city.setCreatedBy(rs.getString("createdBy"));
            city.setCreateDate(rs.getTimestamp("createDate"));
            city.setLastUpdate(rs.getTimestamp("lastUpdate"));
            city.setLastUpdatedBy(rs.getString("lastUpdateBy"));
        }
        
        return cities;
    }
}
