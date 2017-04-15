package com.acme.crm.dao;

import java.sql.SQLException;
import java.util.List;

import com.acme.crm.entities.UserEntity;
import com.acme.crm.exceptions.InvalidUserException;

public interface UserDAO {

    public UserEntity getUserByUserNameAndPassword(String username,
            String password) throws InvalidUserException, SQLException;
    
    public List<UserEntity>  getUsers() throws SQLException;
}
