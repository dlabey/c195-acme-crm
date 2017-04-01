package com.acme.crm;

import java.util.Arrays;
import javax.inject.Inject;
import javafx.application.Application;
import javafx.stage.Stage;

import com.acme.crm.services.DatabaseService;
import com.acme.crm.services.DatabaseServiceImpl;
import com.acme.crm.services.ContextService;
import com.acme.crm.services.ContextServiceImpl;
import com.acme.crm.dao.CityDAO;
import com.acme.crm.dao.CityDAOImpl;
import com.acme.crm.dao.CountryDAO;
import com.acme.crm.dao.CountryDAOImpl;
import com.acme.crm.dao.UserDAO;
import com.acme.crm.dao.UserDAOImpl;

import com.google.inject.AbstractModule;
import com.gluonhq.ignite.guice.GuiceContext;

public class Main extends Application {
    
    private final GuiceContext guiceContext = new GuiceContext(this, () -> Arrays.asList(new GuiceModule()));
    
    @Inject
    private App app;
    
    @Override
    public void start(final Stage stage) throws Exception {
        guiceContext.init();
        app.start(stage);
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
        
        bind(CityDAO.class).to(CityDAOImpl.class);
        bind(CountryDAO.class).to(CountryDAOImpl.class);
        bind(UserDAO.class).to(UserDAOImpl.class);
    }
}