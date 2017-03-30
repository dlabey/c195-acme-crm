package com.acme.crm.services;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import com.acme.crm.entities.UserEntity;

public interface ContextService {
    
    public FXMLLoader getFXMLLoader();
    
    public FXMLLoader setFXMLLoader(FXMLLoader fxmlLoader);
    
    public boolean clearFXMLLoader();
    
    public Stage getStage();
    
    public Stage setStage(Stage stage);
    
    public boolean clearStage();
    
    public UserEntity getUser();
    
    public UserEntity setUser(UserEntity user);
    
    public boolean clearUser();
}
