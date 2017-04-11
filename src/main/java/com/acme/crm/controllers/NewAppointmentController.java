package com.acme.crm.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import org.apache.logging.log4j.LogManager;

public class NewAppointmentController extends AppointmentController {
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(NewAppointmentController.class);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.headingText.setText("New Appointment");
    }
    
    @FXML
    protected void handleSubmit(MouseEvent event) throws Exception {
        logger.debug("handleSubmit");
        
        super.handleSubmit(event, () -> {
            logger.debug("childHandler");
            
            int appointmentId = 0;
            
            try {
                appointmentId = this.appointmentService.createAppointment(
                        this.customerInput.getValue().getCustomerId(),
                        this.titleInput.getText(),
                        this.descriptionInput.getText(),
                        this.locationInput.getText(),
                        this.contactInput.getText(),
                        this.urlInput.getText(),
                        this.startInput.getDateTimeValue(),
                        this.endInput.getDateTimeValue(),
                        this.contextService.getUser().getUserName());
                
                this.appointmentService.loadAppointments(
                    this.contextService.getAppointmentsTable()
                );
                
                logger.debug(appointmentId);
            } catch (SQLException e) {
                errorMessage.setText("Application error");

                logger.debug(e.getMessage());
            }
            
            return appointmentId > 0;
        });
    }
}
