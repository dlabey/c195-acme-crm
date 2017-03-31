package com.acme.crm;

import javax.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App {
    
    @Inject
    private FXMLLoader mainLoader;
    
    public void start(final Stage stage) throws Exception {
        mainLoader.setLocation(getClass().getResource("/ui/Login.fxml"));
        
        System.out.println(mainLoader);
        
        Parent root = mainLoader.load();

        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
