package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.inject.Inject;

import com.acme.crm.services.DatabaseService;

public class AddressDAOImpl implements AddressDAO {
    
    @Inject
    private DatabaseService dbService;
    
    @Override
    public int createAddress(String address, String address2, int cityId,
            String postalCode, String phone, String createdBy)
            throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("INSERT INTO `address`"
                        + "(address, address2, cityId, postalCode, phone,"
                        + "createDate, createdBy, lastUpdate, lastUpdateBy) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
        
        ps.setString(1, address);
        ps.setString(2, address2);
        ps.setInt(3, cityId);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setTimestamp(6, dateTime);
        ps.setString(7, createdBy);
        ps.setTimestamp(8, dateTime);
        ps.setString(9, createdBy);
        
        return ps.executeUpdate();
    }
}
