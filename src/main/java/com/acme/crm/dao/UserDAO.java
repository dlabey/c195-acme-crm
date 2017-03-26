package com.acme.crm.dao;

import com.acme.crm.entities.UserEntity;

public interface UserDAO {
    public UserEntity getUserByUserNameAndPassword(String username, String password) throws Exception;
}
