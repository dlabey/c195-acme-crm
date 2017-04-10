package com.acme.crm.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import com.acme.crm.entities.AppointmentEntity;
import org.apache.logging.log4j.LogManager;

public class EditAppointmentController extends AppointmentController {
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(EditAppointmentController.class);
    
    private AppointmentEntity appointment;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.appointment = this.contextService.getSelectedAppointment();
        
        this.headingText.setText("Edit Appointment");
        this.customerInput.getSelectionModel().select(
            this.appointment.getCustomer()
        );
        this.titleInput.setText(this.appointment.getTitle());
        this.descriptionInput.setText(this.appointment.getDescription());
        this.locationInput.setText(this.appointment.getLocation());
        this.contactInput.setText(this.appointment.getContact());
        this.urlInput.setText(this.appointment.getUrl());
        this.startInput.setDateTimeValue(this.appointment.getStart().toLocalDateTime());
        this.endInput.setDateTimeValue(this.appointment.getEnd().toLocalDateTime());
    }
    
    @FXML
    protected void handleSubmit(MouseEvent event) throws Exception {
        logger.debug("handleSubmit");
        
        super.handleSubmit(event, () -> {
            logger.debug("childHandler");
            
            boolean updated = false;
        
            try {
                this.appointmentDAO.updateAppointment(
                    this.appointment.getAppointmentId(),
                    this.customerInput.getValue().getCustomerId(),
                    this.titleInput.getText(),
                    this.descriptionInput.getText(),
                    this.locationInput.getText(),
                    this.contactInput.getText(),
                    this.urlInput.getText(),
                    this.startInput.getDateTimeValue(),
                    this.endInput.getDateTimeValue(),
                    this.contextService.getUser().getUserName());

                updated = true;
                
                this.appointmentService.loadAppointments(
                    this.contextService.getAppointmentsTable()
                );
                
                logger.debug(updated);
            } catch (SQLException e) {
                errorMessage.setText("Application error");
                
                logger.debug(e.getMessage());
            }

            return updated;
        });
    }
}
