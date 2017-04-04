package com.acme.crm.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.acme.crm.dao.AddressDAO;
import com.acme.crm.dao.CustomerDAO;
import com.acme.crm.entities.AddressEntity;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.services.ContextService;
import com.acme.crm.services.DatabaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManageController extends MainController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ManageController.class);

    @Inject
    private ContextService contextService;
    
    @Inject
    private DatabaseService dbService;
    
    @Inject
    private Provider<FXMLLoader> loader;
    
    @Inject
    private Provider<NewCustomerController> newCustomerController;
    
    @Inject
    private Provider<EditCustomerController> editCustomerController;
    
    @Inject
    private CustomerDAO customerDAO;
    
    @Inject
    private AddressDAO addressDAO;
    
    @FXML
    private TreeTableView customersTable;
    
    @FXML
    private TreeTableColumn customerIdCol;
    
    @FXML
    private TreeTableColumn customerNameCol;
    
    @FXML
    private TreeTableColumn customerAddressCol;
    
    @FXML
    private TreeTableColumn customerPhoneCol;
    
    @FXML
    private TreeTableColumn customerActiveCol;
    
    @FXML
    private TreeTableColumn customerCreatedCol;
    
    @FXML
    private TreeTableColumn customerUpdatedCol;
    
    @FXML
    private Hyperlink customerDeleteLink;
    
    @FXML
    private Hyperlink customerEditLink;
    
    private CustomerEntity customerSelected;
    
    private Stage newCustomerStage;
    
    private Stage editCustomerStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.contextService.setManageController(this);
        
        this.setUpCustomersTable();
        
        // if a customer is not selected disable New Appointment
    }

    @FXML
    private void handleCustomerDeselect(MouseEvent event) throws Exception {
        logger.debug("handleCustomerDeselect");
        
        this.customerSelected = null;
        this.customerDeleteLink.setDisable(true);
        this.customerEditLink.setDisable(true);
        this.customersTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleNewCustomerLink(MouseEvent event) throws Exception {
        logger.debug("handleNewCustomerLink");
       
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
    private void handleDeleteCustomerLink(MouseEvent event) throws Exception {
        logger.debug("handleDeleteCustomerLink");
        
        if (!this.customerDeleteLink.isDisabled() && this.getSelectedCustomer() != null) {
            this.deleteCustomer();
        }
    }
    
    @FXML
    private void handleEditCustomerLink(MouseEvent event) throws Exception {
        logger.debug("handleEditCustomerLink");
        
        if (!this.customerEditLink.isDisabled() && this.getSelectedCustomer() != null) {
            if (editCustomerStage != null) {
                editCustomerStage.hide();
                editCustomerStage = null;
            }
            
            FXMLLoader loader = this.loader.get();

            loader.setLocation(getClass().getResource("/ui/Customer.fxml"));
            loader.setController(editCustomerController.get());

            Parent root = loader.load();

            editCustomerStage = new Stage();

            editCustomerStage.setTitle("Edit Customer");
            editCustomerStage.setScene(new Scene(root));
        
            editCustomerStage.show();
        }
    }

    @FXML
    private void handleNewAppointmentLink(MouseEvent event) throws Exception {
        System.out.println("handleNewAppointmentLinkClick");
        // 1. open form to enter appointment information for selected customer
        // 2. 
    }
    
    @FXML
    private void handleEditAppointmentLink(MouseEvent event) throws Exception {
        
    }
    
    @FXML
    private void handleDeleteAppointmentLink(MouseEvent event) throws Exception {
        
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
        
        this.customersTable.getSelectionModel().selectedItemProperty()
            .addListener((observableValue, oldSelection, newSelection) -> {
                this.handleCustomerSelect((TreeItem<CustomerEntity>) newSelection);
            });
        
        this.loadCustomers();
    }
    
    private void handleCustomerSelect(TreeItem<CustomerEntity> customerRow) {
        if (customerRow != null) {
            this.customerDeleteLink.setDisable(false);
            this.customerEditLink.setDisable(false);
            this.customerSelected = customerRow.getValue();
        }
    }
    
    public void loadCustomers() {
        logger.debug("loadCustomers");
        
        try {
            List<CustomerEntity> customers = this.customerDAO.getCustomers();
            List<TreeItem<CustomerEntity>> customerRows = customers.stream()
                    .map(customer -> {
                        TreeItem<CustomerEntity> rootItem = new TreeItem<>(customer);
                        TreeItem<CustomerEntity> childItem = new TreeItem<>(customer);
                        
                        rootItem.getChildren().setAll(childItem);
                        
                        return rootItem;
                    })
                    .collect(Collectors.toList());
           
            if (customerRows.size() > 0) {
                this.customersTable.getRoot().getChildren().setAll(
                    FXCollections.observableList(customerRows));
            } else {
                this.customersTable.setPlaceholder(new Label("No customers"));
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
    
    public CustomerEntity getSelectedCustomer() {
        return this.customerSelected;
    }
    
    public boolean deleteCustomer() {
        boolean deleted = false;
        
        try {
            try {
                this.dbService.getConnection().setAutoCommit(false);

                PreparedStatement customerPs = this.customerDAO.deleteCustomer(
                    this.getSelectedCustomer().getCustomerId()
                );
                customerPs.executeUpdate();

                PreparedStatement addressPs = this.addressDAO.deleteAddress(
                    this.getSelectedCustomer().getAddress().getAddressId()
                );
                addressPs.executeUpdate();

                this.dbService.getConnection().commit();

                deleted = true;
            } catch (Exception e) {
                this.dbService.getConnection().rollback();

                logger.debug(e.getMessage());
            } finally {
                this.dbService.getConnection().setAutoCommit(true);
                this.loadCustomers();

                logger.debug(deleted);
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        }
        
        return deleted;
    }
}
