package com.acme.crm.services;

import com.acme.crm.entities.UserEntity;

public interface SessionService {
    public UserEntity getUser();
    
    public UserEntity setUser(UserEntity user);
    
    public boolean clearUser();
}
