package com.acme.crm.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.entities.UserEntity;
import com.acme.crm.services.DatabaseService;
import org.apache.logging.log4j.LogManager;

public class EditCustomerController extends CustomerController {
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(EditCustomerController.class);
    
    @Inject
    private DatabaseService dbService;
    
    private CustomerEntity customer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.customer = this.contextService.getManageController()
                    .getSelectedCustomer();
        
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
        logger.debug("handleSubmit");
        
        super.handleSubmit(event, () -> {
            logger.debug("childHandler");
            
            UserEntity user = this.contextService.getUser();
            
            boolean updated = false;
            
            try {
                try {
                    this.dbService.getConnection().setAutoCommit(false);

                    PreparedStatement customerPs = this.customerDAO
                            .updateCustomer(this.customer.getCustomerId(),
                                    this.nameInput.getText(),
                                    this.activeInput.isSelected(),
                                    user.getUserName());
                    customerPs.executeUpdate();

                    PreparedStatement addressPs = this.addressDAO
                            .updateAddress(this.customer.getAddress().getAddressId(),
                                    this.addressInput.getText(),
                                    this.address2Input.getText(),
                                    this.cityInput.getValue().getCityId(),
                                    this.postalCodeInput.getText(),
                                    this.phoneInput.getText(),
                                    user.getUserName());
                    addressPs.executeUpdate();

                    this.dbService.getConnection().commit();

                    updated = true;
                } catch (Exception e) {
                    this.dbService.getConnection().rollback();

                    errorMessage.setText("Application error");

                    logger.debug(e.getMessage());
                } finally {
                    this.dbService.getConnection().setAutoCommit(true);
                    this.contextService.getManageController().loadCustomers();

                    logger.debug(updated);
                }
            } catch (SQLException e) {
                errorMessage.setText("Application error");
                
                logger.debug(e.getMessage());
            }
            
            return updated;
        });
    }
}
