package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;

import com.acme.crm.entities.CountryEntity;
import com.acme.crm.services.DatabaseService;

public class CountryDAOImpl implements CountryDAO {
    
    @Inject
    private DatabaseService dbService;
    
    @Override
    public CountryEntity getCountryByCountryId(int countryId)
            throws SQLException {
        CountryEntity country = null;
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("SELECT * FROM `country` WHERE `countryId` = ?");
        
        ps.setInt(1, countryId);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            country = new CountryEntity();
            country.setCountryId(countryId);
            country.setCountry(rs.getString("country"));
            country.setCreatedBy(rs.getString("createdBy"));
            country.setCreateDate(rs.getTimestamp("createDate"));
            country.setLastUpdate(rs.getTimestamp("lastUpdate"));
            country.setLastUpdatedBy(rs.getString("lastUpdateBy"));
        }
        
        return country;
    }
}
