package com.acme.crm.services;

import javax.inject.Singleton;

import com.acme.crm.controllers.ManageController;
import com.acme.crm.entities.UserEntity;

@Singleton
public class ContextServiceImpl implements ContextService {
    
    private UserEntity user;
    
    private ManageController manageController;
    
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
    public ManageController getManageController() {
        return this.manageController;
    }
    
    @Override
    public ManageController setManageController(ManageController controller) {
        this.manageController = controller;
        
        return this.manageController;
    }
}
