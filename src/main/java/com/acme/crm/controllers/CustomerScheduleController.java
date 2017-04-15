package com.acme.crm.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;

import com.acme.crm.dao.CustomerDAO;
import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.services.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerScheduleController extends ScheduleController {
    
    private static final Logger LOGGER =
            LogManager.getLogger(CustomerScheduleController.class);
    
    @Inject
    private CustomerDAO customerDAO;
    
    @Inject
    private AppointmentService appointmentService;
    
    @FXML
    private ComboBox<CustomerEntity> filterInput;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.titlePane.setText("Customer Schedules");
        
        this.filterLabel.setText("Customer:");
        
        this.filterInput.setConverter(new StringConverter<CustomerEntity>() {
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

            this.filterInput.setItems(FXCollections.observableList(customers));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        
        this.setUpTable();
    }
    
    @Override
    protected void setUpTable() {
        super.setUpTable();
        
        this.column1Col.setText("Customer");
        this.column1Col.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<AppointmentEntity, String> s) {
                return new ReadOnlyObjectWrapper(s.getValue().getCustomer()
                    .getCustomerName());
            }
        });
        
        this.column2Col.setText("Consultant");
        this.column2Col.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<AppointmentEntity, String> s) {
                return new ReadOnlyObjectWrapper(s.getValue().getCreatedBy());
            }
        });
        
        try {
            this.appointmentService
                    .loadAppointmentScheduleByCustomerId(this.scheduleTable, null);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    @Override
    @FXML
    public void handleFilterSelect(ActionEvent event) {
        LOGGER.debug("handleFilterSelect");
        
        try {
            String customerId = this.filterInput.getValue() == null ? null :
                    String.valueOf(this.filterInput.getValue().getCustomerId());
            
            this.appointmentService
                        .loadAppointmentScheduleByCustomerId(this.scheduleTable,
                                customerId);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}