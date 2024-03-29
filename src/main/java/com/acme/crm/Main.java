package com.acme.crm;

import java.util.Arrays;
import javax.inject.Inject;
import javafx.application.Application;
import javafx.stage.Stage;

import com.acme.crm.services.AppointmentService;
import com.acme.crm.services.AppointmentServiceImpl;
import com.acme.crm.services.CustomerService;
import com.acme.crm.services.CustomerServiceImpl;
import com.acme.crm.services.DatabaseService;
import com.acme.crm.services.DatabaseServiceImpl;
import com.acme.crm.services.DateTimeService;
import com.acme.crm.services.DateTimeServiceImpl;
import com.acme.crm.services.ContextService;
import com.acme.crm.services.ContextServiceImpl;
import com.acme.crm.services.ReminderService;
import com.acme.crm.services.ReminderServiceImpl;
import com.acme.crm.dao.AddressDAO;
import com.acme.crm.dao.AddressDAOImpl;
import com.acme.crm.dao.AppointmentDAO;
import com.acme.crm.dao.AppointmentDAOImpl;
import com.acme.crm.dao.CityDAO;
import com.acme.crm.dao.CityDAOImpl;
import com.acme.crm.dao.CountryDAO;
import com.acme.crm.dao.CountryDAOImpl;
import com.acme.crm.dao.CustomerDAO;
import com.acme.crm.dao.CustomerDAOImpl;
import com.acme.crm.dao.ReminderDAO;
import com.acme.crm.dao.ReminderDAOImpl;
import com.acme.crm.dao.UserDAO;
import com.acme.crm.dao.UserDAOImpl;

import com.google.inject.AbstractModule;
import com.gluonhq.ignite.guice.GuiceContext;

/**
 * ACME CRM
 * A CRM for ACME managing customers and appointments
 * @author darren
 */
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

/**
 * GuiceModule
 * Handles dependency injection for proper reuse throughout the application
 * @author darren
 */
class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AppointmentService.class).to(AppointmentServiceImpl.class);
        bind(CustomerService.class).to(CustomerServiceImpl.class);
        bind(DatabaseService.class).to(DatabaseServiceImpl.class);
        bind(DateTimeService.class).to(DateTimeServiceImpl.class);
        bind(ContextService.class).to(ContextServiceImpl.class);
        bind(ReminderService.class).to(ReminderServiceImpl.class);
        
        bind(AddressDAO.class).to(AddressDAOImpl.class);
        bind(AppointmentDAO.class).to(AppointmentDAOImpl.class);
        bind(CityDAO.class).to(CityDAOImpl.class);
        bind(CountryDAO.class).to(CountryDAOImpl.class);
        bind(CustomerDAO.class).to(CustomerDAOImpl.class);
        bind(ReminderDAO.class).to(ReminderDAOImpl.class);
        bind(UserDAO.class).to(UserDAOImpl.class);
    }
}