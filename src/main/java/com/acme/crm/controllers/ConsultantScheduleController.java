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

import com.acme.crm.dao.UserDAO;
import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.UserEntity;
import com.acme.crm.services.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsultantScheduleController extends ScheduleController {
    
    private static final Logger LOGGER =
            LogManager.getLogger(ConsultantScheduleController.class);
    
    @Inject
    private UserDAO userDAO;
    
    @Inject
    private AppointmentService appointmentService;
    
    @FXML
    private ComboBox<UserEntity> filterInput;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.titlePane.setText("Consultant Schedules");
        
        this.filterLabel.setText("Consultant:");
        
        this.filterInput.setConverter(new StringConverter<UserEntity>() {
            @Override
            public String toString(UserEntity user) {
                return user.getUserName();
            }

            @Override
            public UserEntity fromString(String string) {
                return null;
            }
        });

        try {
            List<UserEntity> users = new LinkedList<>();
            
            users.add(null);
            users.addAll(this.userDAO.getUsers());

            this.filterInput.setItems(FXCollections.observableList(users));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        
        this.setUpTable();
    }
    
    @Override
    protected void setUpTable() {
        super.setUpTable();
        
        this.column1Col.setText("Consultant");
        this.column1Col.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<AppointmentEntity, String> s) {
                return new ReadOnlyObjectWrapper(s.getValue().getCreatedBy());
            }
        });
        
        this.column2Col.setText("Customer");
        this.column2Col.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<AppointmentEntity, String> s) {
                return new ReadOnlyObjectWrapper(s.getValue().getCustomer()
                        .getCustomerName());
            }
        });
        
        try {
            this.appointmentService
                    .loadAppointmentScheduleByUserName(this.scheduleTable, null);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    @Override
    @FXML
    public void handleFilterSelect(ActionEvent event) {
        LOGGER.debug("handleFilterSelect");
        
        try {
            String userName = this.filterInput.getValue() == null ? null :
                    this.filterInput.getValue().getUserName();
            
            this.appointmentService
                        .loadAppointmentScheduleByUserName(this.scheduleTable,
                                userName);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
