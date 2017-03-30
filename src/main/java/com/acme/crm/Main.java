package com.acme.crm;

import java.util.Arrays;
import javax.inject.Inject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.acme.crm.services.DatabaseService;
import com.acme.crm.services.DatabaseServiceImpl;
import com.acme.crm.services.ContextService;
import com.acme.crm.services.ContextServiceImpl;
import com.acme.crm.dao.UserDAO;
import com.acme.crm.dao.UserDAOImpl;

import com.google.inject.AbstractModule;
import com.gluonhq.ignite.guice.GuiceContext;

public class Main extends Application {
    
    private GuiceContext guiceContext = new GuiceContext(this, () -> Arrays.asList(new GuiceModule()));
    
    @Inject
    FXMLLoader fxmlLoader;
    
    @Inject
    ContextService contextService;
    
    @Override
    public void start(Stage stage) throws Exception {
        guiceContext.init();
        
        fxmlLoader.setLocation(getClass().getResource("/ui/Login.fxml"));
        
        Parent login = fxmlLoader.load();

        stage.setTitle("Login");
        stage.setScene(new Scene(login));
        stage.show();
        
        contextService.setFXMLLoader(fxmlLoader);
        contextService.setStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseService.class).to(DatabaseServiceImpl.class);
        bind(ContextService.class).to(ContextServiceImpl.class);
        
        bind(UserDAO.class).to(UserDAOImpl.class);
    }
}