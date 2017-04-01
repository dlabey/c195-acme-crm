package com.acme.crm.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

import com.acme.crm.dao.AddressDAO;
import com.acme.crm.services.ContextService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewCustomerController extends CustomerController {
    
    @Inject
    ContextService contextService;
    
    @Inject
    protected AddressDAO addressDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.headingText.setText("New Customer");
    }
    
    @FXML
    protected void handleSubmit(MouseEvent event) throws Exception {
        super.handleSubmit(event, () -> {
            try {
                this.addressDAO.createAddress(this.addressInput.getText(),
                        this.address2Input.getText(),
                        this.cityInput.getValue().getCityId(),
                        this.postalCodeInput.getText(),
                        this.phoneInput.getText(),
                        this.contextService.getUser().getUserName());
            } catch (Exception ex) {
                // log exception message
            }
            
            // create address
            // create customer
            
            return 1 > 0;
        });
    }
}
