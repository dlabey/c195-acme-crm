package com.acme.crm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;

import com.acme.crm.entities.UserEntity;
import com.acme.crm.exceptions.InvalidUserException;
import com.acme.crm.services.DatabaseService;

public class UserDAOImpl implements UserDAO {
    private final DatabaseService dbService;
    
    @Inject
    public UserDAOImpl(DatabaseService dbService) {
        this.dbService = dbService;
    }
    
    @Override
    public UserEntity getUserByUserNameAndPassword(String userName, String password)
            throws InvalidUserException, SQLException {
        UserEntity user = null;
        ResultSet rs = this.dbService.query("SELECT * FROM `user` WHERE `userName` = ? AND `password` = ?");
        
        if (rs.next()) {
            user = new UserEntity();
            user.setUserName(rs.getString("userName"));
        }
        
        return user;
    }
}
