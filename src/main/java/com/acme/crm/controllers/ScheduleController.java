package com.acme.crm.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;

import com.acme.crm.entities.AppointmentEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ScheduleController extends MainController
        implements Initializable {
    
    private static final Logger LOGGER =
            LogManager.getLogger(ScheduleController.class);
    
    @FXML
    protected TitledPane titlePane;
    
    @FXML
    protected Label filterLabel;
    
    @FXML
    protected TableView scheduleTable;
    
    @FXML
    protected TableColumn column1Col;
    
    @FXML
    protected TableColumn column2Col;
    
    @FXML
    private TableColumn locationCol;
    
    @FXML
    private TableColumn startCol;
    
    @FXML
    private TableColumn endCol;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.setUpTable();
    }
    
    protected void setUpTable() {
        this.locationCol.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<AppointmentEntity, String> s) {
                return new ReadOnlyObjectWrapper(s.getValue().getLocation());
            }
        });
        
        this.startCol.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<AppointmentEntity, String> s) {
                return new ReadOnlyObjectWrapper(s.getValue().getStart());
            }
        });
        
        this.endCol.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<AppointmentEntity, String> s) {
                return new ReadOnlyObjectWrapper(s.getValue().getEnd());
            }
        });
    }
    
    abstract void handleFilterSelect(ActionEvent event);
}
