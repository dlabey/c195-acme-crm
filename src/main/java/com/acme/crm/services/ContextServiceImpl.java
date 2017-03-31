package com.acme.crm.services;

import javax.inject.Singleton;

import com.acme.crm.entities.UserEntity;

@Singleton
public class ContextServiceImpl implements ContextService {
    
    private UserEntity user;
    
    @Override
    public UserEntity getUser() {
        return this.user;
    }
    
    @Override
    public UserEntity setUser(UserEntity user) {
        this.user = user;
        
        return this.user;
    }
    
    @Override
    public boolean clearUser() {
        this.user = null;
        
        return true;
    }
}
