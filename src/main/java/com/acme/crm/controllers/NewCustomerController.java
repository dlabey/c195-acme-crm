package com.acme.crm.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import org.apache.logging.log4j.LogManager;

public class NewCustomerController extends CustomerController {
    
    private static final org.apache.logging.log4j.Logger LOGGER =
            LogManager.getLogger(NewCustomerController.class);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.headingText.setText("New Customer");
    }
    
    @FXML
    protected void handleSubmit(MouseEvent event) throws Exception {
        LOGGER.debug("handleSubmit");
        
        super.handleSubmit(event, () -> {
            LOGGER.debug("childHandler");
            
            int customerId = 0;
            
            Runnable popup = () -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Customer Created");
                alert.setHeaderText(null);
                alert.setContentText("The Customer has successfully been created.");
                alert.show();
            };
            
            try {
                customerId = this.customerService.createCustomer(
                        this.nameInput.getText(), this.addressInput.getText(),
                        this.address2Input.getText(),
                        this.cityInput.getValue().getCityId(),
                        this.postalCodeInput.getText(),
                        this.phoneInput.getText(), this.activeInput.isSelected(),
                        this.contextService.getUser().getUserName());
                
                this.customerService.loadCustomers(
                    this.contextService.getCustomersTable()
                );
                
                LOGGER.debug(customerId);
            } catch (SQLException ex) {
                errorMessage.setText("Application error");

                LOGGER.error(ex.getMessage());
            }
            
            boolean created = customerId > 0;
            
            if (created) {
                popup.run();
            }
            
            return created;
        });
    }
}
