package com.acme.crm.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import org.apache.logging.log4j.LogManager;

public class NewCustomerController extends CustomerController {
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(NewCustomerController.class);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.headingText.setText("New Customer");
    }
    
    @FXML
    protected void handleSubmit(MouseEvent event) throws Exception {
        logger.debug("handleSubmit");
        
        super.handleSubmit(event, () -> {
            logger.debug("childHandler");
            
            int customerId = 0;
            
            try {
                int addressId = this.addressDAO.createAddress(
                        this.addressInput.getText(),
                        this.address2Input.getText(),
                        this.cityInput.getValue().getCityId(),
                        this.postalCodeInput.getText(),
                        this.phoneInput.getText(),
                        this.contextService.getUser().getUserName());
                
                customerId = this.customerDAO.createCustomer(
                        this.nameInput.getText(), addressId,
                        this.activeInput.isSelected(),
                        contextService.getUser().getUserName());
                
                this.contextService.getManageController().loadCustomers();
                
                logger.debug(customerId);
            } catch (Exception e) {
                errorMessage.setText("Application error");

                logger.debug(e.getMessage());
            }
            
            return customerId > 0;
        });
    }
}
