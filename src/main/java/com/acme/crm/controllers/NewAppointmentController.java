package com.acme.crm.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import com.acme.crm.dao.AppointmentDAO;
import org.apache.logging.log4j.LogManager;

public class NewAppointmentController extends AppointmentController {
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(NewAppointmentController.class);

    @Inject
    private AppointmentDAO appointmentDAO;
    
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
                appointmentId = this.appointmentDAO.createAppointment(
                        this.customerInput.getValue().getCustomerId(),
                        this.titleInput.getText(),
                        this.descriptionInput.getText(),
                        this.locationInput.getText(),
                        this.contactInput.getText(),
                        this.urlInput.getText(),
                        this.startInput.getDateTimeValue(),
                        this.endInput.getDateTimeValue(),
                        this.contextService.getUser().getUserName());
                
                // todo load table
                
                logger.debug(appointmentId);
            } catch (Exception e) {
                errorMessage.setText("Application error");

                logger.debug(e.getMessage());
            }
            
            return appointmentId > 0;
        });
    }
}
