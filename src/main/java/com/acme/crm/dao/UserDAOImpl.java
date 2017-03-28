package com.acme.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;

import com.acme.crm.entities.UserEntity;
import com.acme.crm.exceptions.InvalidUserException;
import com.acme.crm.services.DatabaseService;

public class UserDAOImpl implements UserDAO {
    @Inject private DatabaseService dbService;
    
    @Override
    public UserEntity getUserByUserNameAndPassword(String userName, String password)
            throws InvalidUserException, SQLException {
        UserEntity user = null;
        PreparedStatement ps = this.dbService.getConnection()
                .prepareStatement("SELECT * FROM `user` WHERE `userName` = ? AND `password` = ? AND `active` = true");
        
        ps.setString(1, userName);
        ps.setString(2, password);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            user = new UserEntity();
            user.setUserId(rs.getInt("userId"));
            user.setUserName(rs.getString("userName"));
            user.setActive(true);
            user.setCreatedBy(rs.getString("createBy"));
            user.setCreateDate(rs.getTimestamp("createDate"));
            user.setLastUpdate(rs.getTimestamp("lastUpdate"));
            user.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
        } else {
            throw new InvalidUserException();
        }
        
        return user;
    }
}
