package com.acme.crm.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import org.apache.logging.log4j.LogManager;

public class NewAppointmentController extends AppointmentController {
    
    private static final org.apache.logging.log4j.Logger LOGGER =
            LogManager.getLogger(NewAppointmentController.class);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.headingText.setText("New Appointment");
    }
    
    @FXML
    protected void handleSubmit(MouseEvent event) throws Exception {
        LOGGER.debug("handleSubmit");
        
        // uses a callback pattern with lambdas for avoiding repeat of code
        super.handleSubmit(event, () -> {
            LOGGER.debug("childHandler");
            
            int appointmentId = 0;
            
            Runnable popup = () -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Created");
                alert.setHeaderText(null);
                alert.setContentText("The Appointment has successfully been created.");
                alert.show();
            };
            
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
                
                LOGGER.debug(appointmentId);
            } catch (SQLException ex) {
                errorMessage.setText("Application error");

                LOGGER.error(ex.getMessage());
            }
            
            boolean created = appointmentId > 0;
            
            popup.run();
            
            return created;
        });
    }
}
