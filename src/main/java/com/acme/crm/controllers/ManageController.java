package com.acme.crm.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Provider;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.acme.crm.dao.CustomerDAO;
import com.acme.crm.entities.AddressEntity;
import com.acme.crm.entities.CustomerEntity;
import javafx.scene.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

public class ManageController extends MainController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ManageController.class);

    private Stage newCustomerStage;
    
    @Inject
    private Provider<FXMLLoader> loader;
    
    @Inject
    private Provider<NewCustomerController> newCustomerController;
    
    @Inject
    protected CustomerDAO customerDAO;
    
    @FXML
    protected TreeTableView customersTable;
    
    @FXML
    protected TreeTableColumn customerIdCol;
    
    @FXML
    protected TreeTableColumn customerNameCol;
    
    @FXML
    protected TreeTableColumn customerAddressCol;
    
    @FXML
    protected TreeTableColumn customerPhoneCol;
    
    @FXML
    protected TreeTableColumn customerActiveCol;
    
    @FXML
    protected TreeTableColumn customerCreatedCol;
    
    @FXML
    protected TreeTableColumn customerUpdatedCol;
    
    @FXML
    protected TreeTableColumn customerEditCol;
    
    @FXML
    protected TreeTableColumn customerDeleteCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.setUpCustomersTable();
        this.loadCustomers();
        
        // if there are no appointments display No appointments
        // if a customer is not selected disable New Appointment
    }

    @FXML
    private void handleCustomerDeselect(MouseEvent event) throws Exception {
        // deselect customer in table
    }

    @FXML
    private void handleNewCustomerLink(MouseEvent event) throws Exception {
        logger.debug("handleNewCustomerLink");
        // 1. open form to enter customer information
        // 2. validate form
        // 3. on success close form and reload data table
        // 4. on error display error
       
        if (newCustomerStage == null) {
            FXMLLoader loader = this.loader.get();
        
            loader.setLocation(getClass().getResource("/ui/Customer.fxml"));
            loader.setController(newCustomerController.get());

            Parent root = loader.load();
            
            newCustomerStage = new Stage();

            newCustomerStage.setTitle("New Customer");
            newCustomerStage.setScene(new Scene(root));
        }
        
        newCustomerStage.show();
    }

    @FXML
    private void handleNewAppointmentLink(MouseEvent event) throws Exception {
        System.out.println("handleNewAppointmentLinkClick");
        // 1. open form to enter appointment information for selected customer
        // 2. 
    }
    
    private void setUpCustomersTable() {
        TreeItem<Void> root = new TreeItem<>();
        
        root.setExpanded(true);
        
        this.customersTable.setRoot(root);
        this.customersTable.setShowRoot(false);
        
        this.customerIdCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<CustomerEntity, Integer>,
                ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(
                    TreeTableColumn.CellDataFeatures<CustomerEntity, Integer> c) {
                int depth = c.getTreeTableView().getTreeItemLevel(c.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(c.getValue().getValue()
                        .getCustomerId());
                } else {
                    return null;
                }
            }
        });
        
        this.customerNameCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<CustomerEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<CustomerEntity, String> c) {
                int depth = c.getTreeTableView().getTreeItemLevel(c.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(c.getValue().getValue()
                        .getCustomerName());
                } else {
                    return null;
                }
            }
        });
        
        this.customerAddressCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<CustomerEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<CustomerEntity, String> c) {
                int depth = c.getTreeTableView().getTreeItemLevel(c.getValue());
                AddressEntity addressEntity = c.getValue().getValue().getAddress();
                String address;
                
                if (depth == 2) {
                    address = String.format("%s, %s, %s",
                        addressEntity.getCity().getCity(),
                        addressEntity.getCity().getCountryId(),
                        addressEntity.getPostalCode());
                } else {
                    address = String.format("%s %s",
                        addressEntity.getAddress(),
                                addressEntity.getAddress2());
                }
                
                return new ReadOnlyObjectWrapper(address);
            }
        });
        
        this.customerPhoneCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<CustomerEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<CustomerEntity, String> c) {
                int depth = c.getTreeTableView().getTreeItemLevel(c.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(c.getValue().getValue()
                        .getAddress().getPhone());
                } else {
                    return null;
                }
            }
        });
        
        this.customerActiveCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<CustomerEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<CustomerEntity, String> c) {
                int depth = c.getTreeTableView().getTreeItemLevel(c.getValue());
                
                if (depth == 1) {
                    String active = c.getValue().getValue().getActive() ? "Yes" : "No";

                    return new ReadOnlyObjectWrapper(active);
                } else {
                    return null;
                }
            }
        });
        
        this.customerCreatedCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<CustomerEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<CustomerEntity, String> c) {
                int depth = c.getTreeTableView().getTreeItemLevel(c.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(c.getValue().getValue()
                        .getCreateDate());
                } else {
                    return null;
                }
            }
        });
        
        this.customerUpdatedCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<CustomerEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<CustomerEntity, String> c) {
                int depth = c.getTreeTableView().getTreeItemLevel(c.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(c.getValue().getValue()
                        .getLastUpdate());
                } else {
                    return null;
                }
            }
        });
    }
    
    private void loadCustomers() {
        logger.debug("loadCustomers");
        
        try {
            List<CustomerEntity> customers = this.customerDAO.getCustomers();
            List<TreeItem<CustomerEntity>> customerRows = customers.stream()
                    .map(customer -> {
                        TreeItem<CustomerEntity> rootItem = new TreeItem<>(customer);
                        TreeItem<CustomerEntity> childItem = new TreeItem<>(customer);
                        
                        rootItem.getChildren().add(childItem);
                        
                        return rootItem;
                    })
                    .collect(Collectors.toList());
           
            
            this.customersTable.getRoot().getChildren().addAll(
                    FXCollections.observableList(customerRows));
            
            if (this.customersTable.getRoot().getChildren().size() == 0) {
                this.customersTable.setPlaceholder(new Label("No customers"));
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
}
