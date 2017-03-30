package com.acme.crm.services;

import javax.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import com.acme.crm.entities.UserEntity;

@Singleton
public class ContextServiceImpl implements ContextService {
    
    private FXMLLoader fxmlLoader;
    
    private Stage stage;
    
    private UserEntity user;
    
    @Override
    public FXMLLoader getFXMLLoader() {
        return this.fxmlLoader;
    }
    
    @Override
    public FXMLLoader setFXMLLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
        
        return this.fxmlLoader;
    }
    
    @Override
    public boolean clearFXMLLoader() {
        this.fxmlLoader = null;
        
        return true;
    }
    
    @Override
    public Stage getStage() {
        return this.stage;
    }
    
    @Override
    public Stage setStage(Stage stage) {
        this.stage = stage;
        
        return this.stage;
    }
    
    @Override
    public boolean clearStage() {
        this.stage = null;
        
        return true;
    }
    
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
