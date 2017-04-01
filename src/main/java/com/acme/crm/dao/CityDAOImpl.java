package com.acme.crm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;
import javax.inject.Inject;

import com.acme.crm.entities.CityEntity;
import com.acme.crm.services.DatabaseService;

public class CityDAOImpl implements CityDAO {
    
    @Inject
    private DatabaseService dbService;
    
    @Override
    public List<CityEntity> getCities() throws SQLException {
        Statement stmnt = this.dbService.getConnection().createStatement();
        
        ResultSet rs = stmnt.executeQuery("SELECT * FROM `city` ORDER BY `city`");
        
        List<CityEntity> cities = new LinkedList<>();
        
        while (rs.next()) {
            CityEntity city = new CityEntity();
            city.setCityId(rs.getInt("cityId"));
            city.setCity(rs.getString("city"));
            city.setCountryId(rs.getInt("countryId"));
            city.setCreatedBy(rs.getString("createdBy"));
            city.setCreateDate(rs.getTimestamp("createDate"));
            city.setLastUpdate(rs.getTimestamp("lastUpdate"));
            city.setLastUpdatedBy(rs.getString("lastUpdateBy"));
            
            cities.add(city);
        }
        
        return cities;
    }
}
