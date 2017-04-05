package com.acme.crm.controllers;

import java.util.function.BooleanSupplier;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

import com.acme.crm.services.ContextService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppointmentController extends MainController implements Initializable {
    
    private static final Logger logger = LogManager.getLogger(AppointmentController.class);

    @Inject
    protected ContextService contextService;
    
    protected void handleSubmit(MouseEvent event, BooleanSupplier childHandler) {
        logger.debug("handleSubmit");
    }
}
