package com.acme.crm.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import com.acme.crm.dao.CustomerDAO;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.services.AppointmentService;
import com.acme.crm.services.ContextService;
import com.acme.crm.services.ReminderService;
import java.util.LinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tornadofx.control.DateTimePicker;

public class AppointmentController extends MainController implements Initializable {
    
    private static final Logger logger =
            LogManager.getLogger(AppointmentController.class);

    @Inject
    protected ContextService contextService;
    
    @Inject
    protected AppointmentService appointmentService;
    
    @Inject
    ReminderService reminderService;
    
    @Inject
    protected CustomerDAO customerDAO;
    
    @FXML
    protected Text headingText;
    
    @FXML
    protected ComboBox<CustomerEntity> customerInput;
    
    @FXML
    protected TextField titleInput;
    
    @FXML
    protected TextField descriptionInput;
    
    @FXML
    protected TextField locationInput;
    
    @FXML
    protected TextField contactInput;
    
    @FXML
    protected TextField urlInput;
    
    @FXML
    protected DateTimePicker startInput;
    
    @FXML
    protected DateTimePicker endInput;
    
    @FXML
    protected Text errorMessage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.errorMessage.setText("");
        
        this.customerInput.setConverter(new StringConverter<CustomerEntity>() {
            @Override
            public String toString(CustomerEntity customer) {
                return customer.getCustomerName();
            }

            @Override
            public CustomerEntity fromString(String string) {
                return null;
            }
        });

        try {
            List<CustomerEntity> customers = new LinkedList<>();
            
            customers.add(null);
            customers.addAll(this.customerDAO.getCustomers());

            this.customerInput.setItems(FXCollections.observableList(customers));
        } catch (SQLException e) {
            logger.debug(e);
        }
        
        CustomerEntity customerSelected = this.contextService
                .getSelectedCustomer();
        
        if (customerSelected != null) {
            this.customerInput.getSelectionModel().select(customerSelected);
        }
    }
    
    @FXML
    private void handleCustomerLabelClick(MouseEvent event) {
        this.customerInput.requestFocus();
    }
    
    @FXML
    private void handleTitleLabelClick(MouseEvent event) {
        this.titleInput.requestFocus();
    }
    
    @FXML
    private void handleDescriptionLabelClick(MouseEvent event) {
        this.descriptionInput.requestFocus();
    }
    
    @FXML
    private void handleLocationLabelClick(MouseEvent event) {
        this.locationInput.requestFocus();
    }
    
    @FXML
    private void handleContactLabelClick(MouseEvent event) {
        this.contactInput.requestFocus();
    }
    
    @FXML
    private void handleUrlLabelClick(MouseEvent event) {
        this.urlInput.requestFocus();
    }
    
    @FXML
    private void handleStartLabelClick(MouseEvent event) {
        this.startInput.requestFocus();
    }
    
    @FXML
    private void handleEndLabelClick(MouseEvent event) {
        this.endInput.requestFocus();
    }
    
    protected void handleSubmit(MouseEvent event, BooleanSupplier childHandler) {
        logger.debug("handleSubmit");
        
        if (this.titleInput.getText().isEmpty() ||
            this.descriptionInput.getText().isEmpty() ||
            this.locationInput.getText().isEmpty() ||
            this.contactInput.getText().isEmpty() || 
            this.urlInput.getText().isEmpty() ||
            this.startInput.getDateTimeValue() == null ||
            this.endInput.getDateTimeValue() == null) {
            this.errorMessage.setText("All fields are required");
        } else {
            boolean result = childHandler.getAsBoolean();
            
            logger.debug(result);
            
            if (result) {
                this.titleInput.clear();
                this.descriptionInput.clear();
                this.locationInput.clear();
                this.contactInput.clear();
                this.urlInput.clear();
                this.startInput.setValue(null);
                this.endInput.setValue(null);
                
                ((Node) event.getSource()).getScene().getWindow().hide();
                
                this.reminderService.scheduleReminders();
            }
        }
    }
}
