package com.acme.crm.controllers;


import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import com.acme.crm.services.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppointmentTypesByMonthController extends MainController
        implements Initializable {
    
    private static final Logger LOGGER =
            LogManager.getLogger(AppointmentTypesByMonthController.class);
    
    @Inject
    protected AppointmentService appointmentService;
    
    @FXML
    private StackedBarChart appointmentTypesStackBarChart;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.appointmentTypesStackBarChart.getXAxis().setLabel("Month");
        this.appointmentTypesStackBarChart.getYAxis().setLabel("Count");
    }
    
    public void populateAppointmentTypesByMonth() {
        List<XYChart.Series> seriesList = new LinkedList<>();
        
        try {
            seriesList = this.appointmentService
                    .loadAppointmenTypesByMonth();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        
        this.appointmentTypesStackBarChart.getData().setAll(seriesList);
    }
}
