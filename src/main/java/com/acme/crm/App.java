package com.acme.crm;

import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        mainLoader.setResources(ResourceBundle.getBundle("bundles.lang",
                this.contextService.getLocale()));
        mainLoader.setLocation(getClass().getResource("/ui/Login.fxml"));
        
        Parent root = mainLoader.load();

        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
