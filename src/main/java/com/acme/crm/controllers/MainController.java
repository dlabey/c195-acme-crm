package com.acme.crm.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import com.acme.crm.services.ReminderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController implements Initializable {
    
    private static final Logger LOGGER =
            LogManager.getLogger(MainController.class);
    
    @Inject
    protected ReminderService reminderService;
    
    protected final Properties businessConfig;
    
    public MainController() {
        super();
        
        businessConfig = new Properties();
        
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream fileInputStream = loader.getResourceAsStream("business.properties");

            
            businessConfig.load(fileInputStream);
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    protected void handleQuit(ActionEvent event) {
        this.exit();
    }
    
    @FXML
    protected void handleAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("ACME CRM by Darren Labey");
        alert.show();
    }
    
    /**
     * exit
     * Exits the application properly shutting down the scheduler thread
     */
    protected void exit() {
        this.reminderService.shutdownScheduler();
                    
        Platform.exit();
    }
}
