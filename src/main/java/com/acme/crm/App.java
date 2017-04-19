package com.acme.crm;

import com.acme.crm.controllers.LoginController;
import javax.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import com.acme.crm.services.ContextService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    
    private static final Logger LOGGER =
        LogManager.getLogger(App.class);
    
    @Inject
    private ContextService contextService;
    
    @Inject
    private FXMLLoader mainLoader;
    
    public void start(final Stage stage) throws Exception {
        LOGGER.debug("start");
        
        // setup the login user interface
        LoginController.setUp(this.contextService, this.mainLoader,
                getClass().getResource("/ui/Login.fxml"), stage);
    }
}
