package com.acme.crm.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController implements Initializable {
    
    private static final Logger LOGGER =
            LogManager.getLogger(MainController.class);
    
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
        // TODO load language
    }
}
