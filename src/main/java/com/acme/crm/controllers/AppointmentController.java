package com.acme.crm.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.function.BooleanSupplier;
import java.util.LinkedList;
import java.util.List;
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

import com.acme.crm.dao.AppointmentDAO;
import com.acme.crm.dao.CustomerDAO;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.enums.InvalidAppointmentTypeEnum;
import com.acme.crm.exceptions.InvalidAppointmentException;
import com.acme.crm.exceptions.OverlappingAppointmentException;
import com.acme.crm.services.AppointmentService;
import com.acme.crm.services.ContextService;
import com.acme.crm.services.ReminderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tornadofx.control.DateTimePicker;

public abstract class AppointmentController extends MainController
        implements Initializable {
    
    private static final Logger LOGGER =
            LogManager.getLogger(AppointmentController.class);

    @Inject
    protected ContextService contextService;
    
    @Inject
    protected AppointmentService appointmentService;
    
    @Inject
    ReminderService reminderService;
    
    @Inject
    protected AppointmentDAO appointmentDAO;
    
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
        super.initialize(url, rb);
        
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
        LOGGER.debug("handleSubmit");
        
        try {
            this.validate();
            
            boolean result = childHandler.getAsBoolean();
            
            LOGGER.debug(result);
            
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
        } catch (InvalidAppointmentException | OverlappingAppointmentException ex) {
            this.errorMessage.setText(ex.getMessage());
            
            LOGGER.warn(ex.getMessage());
        }
    }
    
    private void validate()
            throws InvalidAppointmentException, OverlappingAppointmentException {
        int appointmentId = this.contextService
                .getSelectedAppointment() == null ? -1 :
                this.contextService.getSelectedAppointment().getAppointmentId();
        int customerId = this.customerInput.getValue() == null ? null : 
                this.customerInput.getValue().getCustomerId();
        
        String createdBy = this.contextService.getUser().getUserName();
        
        LocalDateTime startRaw = this.startInput.getDateTimeValue();
        LocalDateTime endRaw = this.endInput.getDateTimeValue();
        
        try {
            if (this.isInvalidData()) {
                throw new InvalidAppointmentException(InvalidAppointmentTypeEnum
                        .INCOMPLETE);
            } else if (this.isInvalidTime()) {
                throw new InvalidAppointmentException(InvalidAppointmentTypeEnum
                        .INVALID_TIME);
            } else if (this.isOutsideBusinessHours()) {
                throw new InvalidAppointmentException(InvalidAppointmentTypeEnum
                        .OUTSIDE_BUSINESS_HOURS);
            } else if (this.appointmentDAO.isOverlappingAppointment(
                    appointmentId, customerId, startRaw, endRaw, createdBy)) {
                throw new OverlappingAppointmentException();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    private boolean isInvalidData() {
        return (this.customerInput.getValue() == null ||
            this.titleInput.getText().isEmpty() ||
            this.descriptionInput.getText().isEmpty() ||
            this.locationInput.getText().isEmpty() ||
            this.contactInput.getText().isEmpty() || 
            this.urlInput.getText().isEmpty() ||
            this.startInput.getDateTimeValue() == null ||
            this.endInput.getDateTimeValue() == null);
    }
    
    private boolean isInvalidTime() {
        return this.startInput.getDateTimeValue()
                .isAfter(this.endInput.getDateTimeValue());
    }
    
    private boolean isOutsideBusinessHours() {
        int businessStartHour = Integer
                .valueOf(this.businessConfig.getProperty("open_hour"));
        int businessCloseHour = Integer
                .valueOf(this.businessConfig.getProperty("close_hour"));
        
        int appointmentStartHour = this.startInput
                .getDateTimeValue().getHour();
        int appointmentCloseHour = this.endInput
                .getDateTimeValue().getHour();
        
        LOGGER.debug(businessStartHour);
        LOGGER.debug(businessCloseHour);
        LOGGER.debug(appointmentStartHour);
        LOGGER.debug(appointmentCloseHour);
        
        return appointmentStartHour < businessStartHour
                || appointmentCloseHour > businessCloseHour;
    }
    
    public void populateCustomers() {
        try {
            List<CustomerEntity> customers = new LinkedList<>();
            
            customers.add(null);
            customers.addAll(this.customerDAO.getCustomers());

            this.customerInput.setItems(FXCollections.observableList(customers));
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
        
        CustomerEntity customerSelected = this.contextService
                .getSelectedCustomer();
        
        if (customerSelected != null) {
            this.customerInput.getSelectionModel().select(customerSelected);
        }
    }
}
