package com.acme.crm.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.inject.Provider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.acme.crm.services.ContextService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManageController extends MainController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ManageController.class);

    private Stage newCustomerStage;
    
    @Inject
    private Provider<FXMLLoader> loader;
    
    @Inject
    private Provider<NewCustomerController> newCustomerController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        // if there are no customers display No customers
        // if there are no appointments display No appointments
        // if a customer is not selected disable New Appointment
    }

    @FXML
    private void handleCustomerDeselect(MouseEvent event) throws Exception {
        // deselect customer in table
    }

    @FXML
    private void handleNewCustomerLink(MouseEvent event) throws Exception {
        logger.debug("handleNewCustomerLink");
        // 1. open form to enter customer information
        // 2. validate form
        // 3. on success close form and reload data table
        // 4. on error display error
       
        if (newCustomerStage == null) {
            FXMLLoader loader = this.loader.get();
        
            loader.setLocation(getClass().getResource("/ui/Customer.fxml"));
            loader.setController(newCustomerController.get());

            Parent root = loader.load();
            
            newCustomerStage = new Stage();

            newCustomerStage.setTitle("New Customer");
            newCustomerStage.setScene(new Scene(root));
        }
        
        newCustomerStage.show();
    }

    @FXML
    private void handleNewAppointmentLink(MouseEvent event) throws Exception {
        System.out.println("handleNewAppointmentLinkClick");
        // 1. open form to enter appointment information for selected customer
        // 2. 
    }
}
