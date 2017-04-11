package com.acme.crm.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.inject.Provider;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import com.acme.crm.dao.AppointmentDAO;
import com.acme.crm.entities.AddressEntity;
import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.CustomerEntity;
import com.acme.crm.entities.MonthEntity;
import com.acme.crm.entities.WeekEntity;
import com.acme.crm.entities.YearEntity;
import com.acme.crm.services.AppointmentService;
import com.acme.crm.services.ContextService;
import com.acme.crm.services.CustomerService;
import com.acme.crm.services.DateTimeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManageController extends MainController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ManageController.class);

    @Inject
    private ContextService contextService;
    
    @Inject
    protected CustomerService customerService;
    
    @Inject
    protected DateTimeService dateTimeService;
    
    @Inject
    protected AppointmentService appointmentService;
    
    @Inject
    private Provider<FXMLLoader> loader;
    
    @Inject
    private Provider<NewCustomerController> newCustomerController;
    
    @Inject
    private Provider<EditCustomerController> editCustomerController;
    
    @Inject
    private Provider<NewAppointmentController> newAppointmentController;
    
    @Inject
    private Provider<EditAppointmentController> editAppointmentController;
    
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
    
    @FXML
    private ComboBox<YearEntity> yearInput;
    
    @FXML
    private ComboBox<MonthEntity> monthInput;
    
    @FXML
    private ComboBox<WeekEntity> weekInput;
    
    @FXML
    private TreeTableView appointmentsTable;
    
    @FXML
    private TreeTableColumn appointmentIdCol;
    
    @FXML
    private TreeTableColumn appointmentCustomerCol;
    
    @FXML
    private TreeTableColumn appointmentDetailsCol;
    
    @FXML
    private TreeTableColumn appointmentStartCol;
    
    @FXML
    private TreeTableColumn appointmentEndCol;
    
    @FXML
    private TreeTableColumn appointmentCreatedCol;
    
    @FXML
    private TreeTableColumn appointmentUpdatedCol;
    
    @FXML
    private Hyperlink appointmentDeleteLink;
    
    @FXML
    private Hyperlink appointmentEditLink;
    
    private CustomerEntity customerSelected;
    
    private YearEntity yearSelected;
    
    private MonthEntity monthSelected;
    
    private WeekEntity weekSelected;
    
    private AppointmentEntity appointmentSelected;
    
    private Stage newCustomerStage;
    
    private Stage editCustomerStage;
    
    private Stage newAppointmentStage;
    
    private Stage editAppointmentStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.setUpCustomersTable();
        this.setUpAppointmentsTable();
        
        this.contextService.setCustomersTable(this.customersTable);
        this.contextService.setAppointmentsTable(this.appointmentsTable);
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
        
        if (!this.customerDeleteLink.isDisabled() && this.customerSelected != null) {
            this.deleteCustomer();
        }
    }
    
    @FXML
    private void handleEditCustomerLink(MouseEvent event) throws Exception {
        logger.debug("handleEditCustomerLink");
        
        if (!this.customerEditLink.isDisabled() && this.customerSelected != null) {
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
        logger.debug("handleNewAppointmentLink");
       
        if (newAppointmentStage == null) {
            FXMLLoader loader = this.loader.get();
        
            loader.setLocation(getClass().getResource("/ui/Appointment.fxml"));
            loader.setController(newAppointmentController.get());

            Parent root = loader.load();
            
            newAppointmentStage = new Stage();

            newAppointmentStage.setTitle("New Appointment");
            newAppointmentStage.setScene(new Scene(root));
        }
        
        newAppointmentStage.show();
    }
    
    @FXML
    private void handleEditAppointmentLink(MouseEvent event) throws Exception {
        logger.debug("handleEditAppointmentLink");
        
        if (!this.appointmentEditLink.isDisabled() &&
                this.appointmentSelected != null) {
            if (editAppointmentStage != null) {
                editAppointmentStage.hide();
                editAppointmentStage = null;
            }
            
            FXMLLoader loader = this.loader.get();

            loader.setLocation(getClass().getResource("/ui/Appointment.fxml"));
            loader.setController(editAppointmentController.get());

            Parent root = loader.load();

            editAppointmentStage = new Stage();

            editAppointmentStage.setTitle("Edit Appointment");
            editAppointmentStage.setScene(new Scene(root));
        
            editAppointmentStage.show();
        } 
    }
    
    @FXML
    private void handleDeleteAppointmentLink(MouseEvent event) throws Exception {
        logger.debug("handleDeleteAppointmentLink");
        
        if (!this.appointmentDeleteLink.isDisabled() &&
                this.appointmentSelected != null) {
            this.deleteAppointment();
        }
    }
    
    @FXML
    private void handleYearSelect(ActionEvent event) {
        logger.debug("handleYearSelect");
        
        this.setAppointmentsTableFilters(this.yearInput.getValue(),
                this.monthInput.getValue(), this.weekInput.getValue());
        
        if (this.yearSelected != null) {
            List<MonthEntity> months = new LinkedList<>();
            
            months.add(null);
            months.addAll(this.dateTimeService.getMonths(this.yearSelected.getYearAsInt()));

            this.monthInput.setItems(FXCollections.observableList(months));
        }
    }
    
    @FXML
    private void handleMonthSelect(ActionEvent event) {
        logger.debug("handleMonthSelect");
        
        this.setAppointmentsTableFilters(this.yearInput.getValue(),
                this.monthInput.getValue(), this.weekInput.getValue());
        
        if (this.yearSelected != null && this.monthSelected != null) {
            List<WeekEntity> weeks = new LinkedList<>();
            
            weeks.add(null);
            weeks.addAll(this.dateTimeService.getWeeks(
                    this.yearSelected.getYearAsInt(),
                    this.monthSelected.getMonthAsInt()));

            this.weekInput.setItems(FXCollections.observableList(weeks));
        }
    }
    
    @FXML
    private void handleWeekSelect(ActionEvent event) {
        logger.debug("handleWeekSelect");
        
        this.setAppointmentsTableFilters(this.yearInput.getValue(),
                this.monthInput.getValue(), this.weekInput.getValue());
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
                        addressEntity.getCity().getCountry().getCountry(),
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
        
        try {
            this.customerService.loadCustomers(this.customersTable);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
    
    private void handleCustomerSelect(TreeItem<CustomerEntity> customerRow) {
        if (customerRow != null) {
            this.customerDeleteLink.setDisable(false);
            this.customerEditLink.setDisable(false);
            this.customerSelected = customerRow.getValue();
            
            this.contextService.setSelectedCustomer(customerSelected);
        }
    }
    
    public boolean deleteCustomer() {
        boolean deleted = false;
        
        try {
            deleted = this.customerService.deleteCustomer(
                            this.customerSelected.getCustomerId(),
                            this.customerSelected.getAddress().getAddressId());
            
            this.customerService.loadCustomers(this.customersTable);
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        }
        
        return deleted;
    }
    
    private void setUpAppointmentsTable() {
        this.yearInput.setConverter(new StringConverter<YearEntity>() {
            @Override
            public String toString(YearEntity year) {
                return year == null ? null : year.getYear();
            }

            @Override
            public YearEntity fromString(String string) {
                return null;
            }
        });

        try {
            List<YearEntity> years = new LinkedList<>();
            
            years.add(null);
            years.addAll(this.dateTimeService.getYears());

            this.yearInput.setItems(FXCollections.observableList(years));
        } catch (SQLException e) {
            logger.debug(e);
        }
        
        this.monthInput.setConverter(new StringConverter<MonthEntity>() {
            @Override
            public String toString(MonthEntity month) {
                return month == null ? null : month.getMonth();
            }

            @Override
            public MonthEntity fromString(String string) {
                return null;
            }
        });
        
        this.weekInput.setConverter(new StringConverter<WeekEntity>() {
            @Override
            public String toString(WeekEntity week) {
                return week == null ? null : week.getWeek();
            }

            @Override
            public WeekEntity fromString(String string) {
                return null;
            }
        });

        try {
            List<YearEntity> years = this.dateTimeService.getYears();

            this.yearInput.setItems(FXCollections.observableList(years));
        } catch (SQLException e) {
            logger.debug(e);
        }
        
        TreeItem<Void> root = new TreeItem<>();
        
        root.setExpanded(true);
        
        this.appointmentsTable.setRoot(root);
        this.appointmentsTable.setShowRoot(false);
        
        this.appointmentIdCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<AppointmentEntity, Integer>,
                ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(
                    TreeTableColumn.CellDataFeatures<AppointmentEntity, Integer> a) {
                int depth = a.getTreeTableView().getTreeItemLevel(a.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(a.getValue().getValue()
                        .getAppointmentId());
                } else {
                    return null;
                }
            }
        });
        
        this.appointmentCustomerCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<AppointmentEntity, String> a) {
                int depth = a.getTreeTableView().getTreeItemLevel(a.getValue());
                CustomerEntity customerEntity = a.getValue().getValue().getCustomer();
                String customer = null; 
                
                if (depth == 1) {
                    customer = String.format("%s (%s)",
                        customerEntity.getCustomerName(),
                        customerEntity.getCustomerId());
                }
                
                return new ReadOnlyObjectWrapper(customer);
            }
        });
        
        this.appointmentDetailsCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<AppointmentEntity, String> a) {
                int depth = a.getTreeTableView().getTreeItemLevel(a.getValue());
                AppointmentEntity appointmentEntity = a.getValue().getValue();
                String details;
                
                if (depth == 2) {
                    details = String.format("%s: %s%n%s: %s%n%s: %s %s: %s%n%s: %s",
                        "Description",
                        appointmentEntity.getDescription(),
                        "Consultant",
                        appointmentEntity.getCreatedBy(),
                        "Location",
                        appointmentEntity.getLocation(),
                        "Contact",
                        appointmentEntity.getContact(),
                        "URL",
                        appointmentEntity.getUrl());
                } else {
                    details = appointmentEntity.getTitle();
                    
                }
                
                return new ReadOnlyObjectWrapper(details);
            }
        });
        
        this.appointmentStartCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<AppointmentEntity, String> a) {
                int depth = a.getTreeTableView().getTreeItemLevel(a.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(a.getValue().getValue()
                        .getStart());
                }
                
                return null;
            }
        });
        
        this.appointmentEndCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<AppointmentEntity, String> a) {
                int depth = a.getTreeTableView().getTreeItemLevel(a.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(a.getValue().getValue()
                        .getEnd());
                }
                
                return null;
            }
        });
        
        this.appointmentCreatedCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<AppointmentEntity, String> a) {
                int depth = a.getTreeTableView().getTreeItemLevel(a.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(a.getValue().getValue()
                        .getCreateDate());
                }
                
                return null;
            }
        });
        
        this.appointmentUpdatedCol.setCellValueFactory(
            new Callback<TreeTableColumn.CellDataFeatures<AppointmentEntity, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(
                    TreeTableColumn.CellDataFeatures<AppointmentEntity, String> a) {
                int depth = a.getTreeTableView().getTreeItemLevel(a.getValue());
                
                if (depth == 1) {
                    return new ReadOnlyObjectWrapper(a.getValue().getValue()
                        .getLastUpdate());
                }
                
                return null;
            }
        });
        
        this.appointmentsTable.getSelectionModel().selectedItemProperty()
            .addListener((observableValue, oldSelection, newSelection) -> {
                this.handleAppointmentSelect((TreeItem<AppointmentEntity>) newSelection);
            });
        
        try {
            this.appointmentService.loadAppointments(this.appointmentsTable);
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        }
    }
    
    private void handleAppointmentSelect(TreeItem<AppointmentEntity>
            appointmentRow) {
        if (appointmentRow != null) {
            this.appointmentDeleteLink.setDisable(false);
            this.appointmentEditLink.setDisable(false);
            this.appointmentSelected = appointmentRow.getValue();
            
            this.contextService.setSelectedAppointment(appointmentSelected);
        }
    }
    
    private void setAppointmentsTableFilters(YearEntity year, MonthEntity month,
            WeekEntity week) {
        this.yearSelected = year;
        this.monthSelected = month;
        this.weekSelected = week;
        
        this.contextService.setAppointmentsTableFilters(year, month, week);
        
        try {
            this.appointmentService.loadAppointments(this.appointmentsTable);
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        }
    }
    
    private boolean deleteAppointment() {
        boolean deleted = false;
        
        try {
            this.appointmentService.deleteAppointment(
                this.appointmentSelected.getAppointmentId()
            );
            
            this.appointmentService.loadAppointments(this.appointmentsTable);
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        }
        
        return deleted;
    }
}
