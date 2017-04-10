package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.inject.Inject;

import com.acme.crm.services.DatabaseService;
import org.apache.logging.log4j.LogManager;

public class AddressDAOImpl implements AddressDAO {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AddressDAOImpl.class);

    @Inject
    private DatabaseService dbService;

    @Override
    public int createAddress(String address, String address2, int cityId,
            String postalCode, String phone, String createdBy)
            throws SQLException {
        logger.debug("createAddress");
        logger.debug(address, address2, cityId, postalCode, phone, createdBy);

        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("INSERT INTO `address` "
                        + "(address, address2, cityId, postalCode, phone,"
                        + "createDate, createdBy, lastUpdate, lastUpdateBy) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));

        ps.setString(1, address);
        ps.setString(2, address2);
        ps.setInt(3, cityId);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setTimestamp(6, now);
        ps.setString(7, createdBy);
        ps.setTimestamp(8, now);
        ps.setString(9, createdBy);
        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        int newId = 0;

        if (generatedKeys.next()) {
            newId = generatedKeys.getInt(1);
        }

        return newId;
    }

    @Override
    public PreparedStatement updateAddress(int addressId, String address,
            String address2, int cityId, String postalCode, String phone,
            String updatedBy) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("UPDATE `address` "
                        + "SET `address`=?, "
                        + "`address2`=?, "
                        + "`cityId`=?, "
                        + "`postalCode`=?, "
                        + "`phone`=?, "
                        + "`lastUpdate`=?, "
                        + "`lastUpdateBy`=? "
                        + "WHERE `addressId`=?");
        Timestamp now = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));

        ps.setString(1, address);
        ps.setString(2, address2);
        ps.setInt(3, cityId);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setTimestamp(6, now);
        ps.setString(7, updatedBy);
        ps.setInt(8, addressId);

        return ps;
    }

    @Override
    public PreparedStatement deleteAddress(int addressId) throws SQLException {
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("DELETE FROM `address` "
                        + "WHERE `addressId`=?");

        ps.setInt(1, addressId);

        return ps;
    }
}
