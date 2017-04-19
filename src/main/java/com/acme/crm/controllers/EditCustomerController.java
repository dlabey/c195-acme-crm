package com.acme.crm.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import com.acme.crm.entities.CustomerEntity;
import org.apache.logging.log4j.LogManager;

public class EditCustomerController extends CustomerController {
    
    private static final org.apache.logging.log4j.Logger LOGGER =
            LogManager.getLogger(EditCustomerController.class);
    
    private CustomerEntity customer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.customer = this.contextService.getSelectedCustomer();
        
        this.headingText.setText("Edit Customer");
        this.nameInput.setText(this.customer.getCustomerName());
        this.addressInput.setText(this.customer.getAddress().getAddress());
        this.address2Input.setText(this.customer.getAddress().getAddress2());
        this.cityInput.getSelectionModel().select(this.customer.getAddress()
                .getCity());
        this.postalCodeInput.setText(this.customer.getAddress().getPostalCode());
        this.phoneInput.setText(this.customer.getAddress().getPhone());
        this.countryInput.setText(customer.getAddress().getCity().getCountry()
                .getCountry());
        this.activeInput.setSelected(this.customer.getActive());
    }
    
    @FXML
    protected void handleSubmit(MouseEvent event) throws Exception {
        LOGGER.debug("handleSubmit");
        
        super.handleSubmit(event, () -> {
            LOGGER.debug("childHandler");
            
            boolean updated = false;
            
            Runnable popup = () -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Updated");
                alert.setHeaderText(null);
                alert.setContentText("The Customer has successfully been updated.");
                alert.show();
            };
            
            try {
                updated = this.customerService.editCustomer(this.customer.getCustomerId(),
                    this.customer.getAddress().getAddressId(),
                    this.nameInput.getText(), this.addressInput.getText(),
                    this.address2Input.getText(),
                    this.cityInput.getValue().getCityId(),
                    this.postalCodeInput.getText(),
                    this.phoneInput.getText(), this.activeInput.isSelected(),
                    this.contextService.getUser().getUserName());
                
                this.customerService.loadCustomers(
                    this.contextService.getCustomersTable()
                );
                
                LOGGER.debug(updated);
            } catch (SQLException ex) {
                errorMessage.setText("Application error");
                
                LOGGER.error(ex.getMessage());
            }
            
            if (updated) {
                popup.run();
            }
            
            return updated;
        });
    }
}
