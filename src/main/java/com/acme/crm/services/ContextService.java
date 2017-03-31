package com.acme.crm.services;

import com.acme.crm.entities.UserEntity;

public interface ContextService {
    
    public UserEntity getUser();
    
    public UserEntity setUser(UserEntity user);
    
    public boolean clearUser();
}
