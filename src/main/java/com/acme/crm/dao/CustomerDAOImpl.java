package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;

import com.acme.crm.entities.AddressEntity;
import com.acme.crm.entities.CityEntity;
import com.acme.crm.entities.CountryEntity;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.services.DatabaseService;
import java.sql.ResultSet;

public class CustomerDAOImpl implements CustomerDAO {
    
    @Inject
    private DatabaseService dbService;
    
    @Override
    public int createCustomer(String customerName, int addressId,boolean active,
            String createdBy) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("INSERT INTO `customer` "
                        + "(customerName, addressId, active, createDate, "
                        + "createdBy, lastUpdate, lastUpdateBy) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        
        ps.setString(1, customerName);
        ps.setInt(2, addressId);
        ps.setBoolean(3, active);
        ps.setTimestamp(4, dateTime);
        ps.setString(5, createdBy);
        ps.setTimestamp(6, dateTime);
        ps.setString(7, createdBy);
        ps.executeUpdate();
        
        ResultSet generatedKeys = ps.getGeneratedKeys();
        int newId = -1;
        
        if (generatedKeys.next()) {
            newId = generatedKeys.getInt(1);
        }
        
        return newId;
    }
    
    @Override
    public PreparedStatement updateCustomer(int customerId, String customerName,
            boolean active, String updatedBy) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("UPDATE `customer` "
                        + "SET `customerName` = ?, "
                        + "`active` = ?, "
                        + "`lastUpdate` = ?, "
                        + "`lastUpdateBy` = ? "
                        + "WHERE`customerId` = ?");
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        
        ps.setString(1, customerName);
        ps.setBoolean(2, active);
        ps.setTimestamp(3, dateTime);
        ps.setString(4, updatedBy);
        ps.setInt(5, customerId);
        
        return ps;
    }
    
    @Override
    public PreparedStatement deleteCustomer(int customerId) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("DELETE FROM `customer` "
                        + "WHERE `customerId` = ?");
        
        ps.setInt(1, customerId);
        
        return ps;
    }
    
    @Override
    public List<CustomerEntity> getCustomers() throws SQLException {
        Statement stmnt = this.dbService.getConnection().createStatement();
        
        ResultSet rs = stmnt.executeQuery("SELECT * FROM `customer` `c` "
                + "JOIN `address` `a` ON `a`.`addressId` = `c`.`addressId` "
                + "JOIN `city` `ci` ON `ci`.`cityId` = `a`.`cityId` "
                + "JOIN `country` `co` ON `co`.`countryId` = `ci`.`countryId`");
        
        List<CustomerEntity> customers = new LinkedList<>();
        
        while (rs.next()) {
            CountryEntity country = new CountryEntity();
            country.setCountryId(rs.getInt("co.countryId"));
            country.setCountry(rs.getString("co.country"));
            country.setCreateDate(rs.getTimestamp("co.createDate"));
            country.setCreatedBy(rs.getString("co.createdBy"));
            country.setLastUpdate(rs.getTimestamp("co.lastUpdate"));
            country.setLastUpdatedBy(rs.getString("co.lastUpdateBy"));
            
            CityEntity city = new CityEntity();
            city.setCityId(rs.getInt("ci.cityId"));
            city.setCity(rs.getString("ci.city"));
            city.setCountry(country);
            city.setCreatedBy(rs.getString("ci.createdBy"));
            city.setCreateDate(rs.getTimestamp("ci.createDate"));
            city.setLastUpdate(rs.getTimestamp("ci.lastUpdate"));
            city.setLastUpdatedBy(rs.getString("ci.lastUpdateBy"));
            
            AddressEntity address = new AddressEntity();
            address.setAddressId(rs.getInt("a.addressId"));
            address.setAddress(rs.getString("a.address"));
            address.setAddress2(rs.getString("a.address2"));
            address.setCity(city);
            address.setPostalCode(rs.getString("a.postalCode"));
            address.setPhone(rs.getString("a.phone"));
            address.setCreateDate(rs.getTimestamp("a.createDate"));
            address.setCreatedBy(rs.getString("a.createdBy"));
            address.setLastUpdate(rs.getTimestamp("a.lastUpdate"));
            address.setLastUpdatedBy(rs.getString("a.lastUpdateBy"));
            
            CustomerEntity customer = new CustomerEntity();
            customer.setCustomerId(rs.getInt("c.customerId"));
            customer.setCustomerName(rs.getString("c.customerName"));
            customer.setAddress(address);
            customer.setActive(rs.getBoolean("c.active"));
            customer.setCreateDate(rs.getTimestamp("c.createDate"));
            customer.setCreatedBy(rs.getString("c.createdBy"));
            customer.setLastUpdate(rs.getTimestamp("c.lastUpdate"));
            customer.setLastUpdatedBy(rs.getString("c.lastUpdateBy"));
            
            customers.add(customer);
        }
        
        return customers;
    }
}
