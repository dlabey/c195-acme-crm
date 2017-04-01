package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.inject.Inject;

import com.acme.crm.services.DatabaseService;

public class CustomerDAOImpl implements CustomerDAO {
    
    @Inject
    private DatabaseService dbService;
    
    @Override
    public int createCustomer(String customerName, int addressId,boolean active,
            String createdBy) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("INSERT INTO `customer`"
                        + "(customerName, addressId, active, createDate,"
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
        
        return ps.executeUpdate();
    }
}
