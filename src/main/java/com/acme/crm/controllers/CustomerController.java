package com.acme.crm.controllers;

import javax.inject.Inject;
import java.net.URL;
import java.sql.SQLException;
import java.util.function.BooleanSupplier;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import com.acme.crm.dao.AddressDAO;
import com.acme.crm.dao.CityDAO;
import com.acme.crm.dao.CountryDAO;
import com.acme.crm.dao.CustomerDAO;
import com.acme.crm.entities.CityEntity;
import com.acme.crm.entities.CountryEntity;
import com.acme.crm.exceptions.InvalidCustomerException;
import com.acme.crm.services.ContextService;
import com.acme.crm.services.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerController extends MainController implements Initializable {

    private static final Logger LOGGER =
            LogManager.getLogger(CustomerController.class);

    @Inject
    protected ContextService contextService;
    
    @Inject
    protected CustomerService customerService;
    
    @Inject
    protected CityDAO cityDAO;
    
    @Inject
    protected CountryDAO countryDAO;
    
    @Inject
    protected CustomerDAO customerDAO;
    
    @Inject
    protected AddressDAO addressDAO;

    @FXML
    protected Text headingText;

    @FXML
    protected TextField nameInput;

    @FXML
    protected TextField addressInput;

    @FXML
    protected TextField address2Input;

    @FXML
    protected ComboBox<CityEntity> cityInput;

    @FXML
    protected TextField postalCodeInput;
    
    @FXML
    protected TextField phoneInput;

    @FXML
    protected TextField countryInput;

    @FXML
    protected CheckBox activeInput;

    @FXML
    protected Text errorMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.errorMessage.setText("");

        this.cityInput.setConverter(new StringConverter<CityEntity>() {
            @Override
            public String toString(CityEntity city) {
                return city.getCity();
            }

            @Override
            public CityEntity fromString(String string) {
                return null;
            }
        });

        try {
            List<CityEntity> cities = new LinkedList<>();
            
            cities.add(null);
            cities.addAll(this.cityDAO.getCities());

            this.cityInput.setItems(FXCollections.observableList(cities));
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
    }

    @FXML
    private void handleNameLabelClick(MouseEvent event) {
        this.nameInput.requestFocus();
    }

    @FXML
    private void handleAddressLabelClick(MouseEvent event) {
        this.addressInput.requestFocus();
    }

    @FXML
    private void handleAddress2LabelClick(MouseEvent event) {
        this.address2Input.requestFocus();
    }

    @FXML
    private void handleCityLabelClick(MouseEvent event) {
        this.cityInput.requestFocus();
    }

    @FXML
    private void handlePostalCodeLabelClick(MouseEvent event) {
        this.postalCodeInput.requestFocus();
    }
    
    @FXML
    private void handlePhoneLabelClick(MouseEvent event) {
        this.phoneInput.requestFocus();
    }

    @FXML
    private void handleCountryLabelClick(MouseEvent event) {
        this.countryInput.requestFocus();
    }

    @FXML
    private void handleActiveLabelClick(MouseEvent event) {
        this.activeInput.requestFocus();
    }

    @FXML
    private void handleCitySelect(ActionEvent event) {
        LOGGER.debug("handleCitySelect");
        
        CityEntity city = this.cityInput.getValue();
        
        try {
            CountryEntity country = this.countryDAO.getCountryByCountryId(city.getCountryId());
            
            LOGGER.debug(country.getCountry());
            
            this.countryInput.setText(country.getCountry());
        } catch (SQLException e) {
            errorMessage.setText("Application error");
            
            LOGGER.error(e.getMessage());
        }
    }

    protected void handleSubmit(MouseEvent event, BooleanSupplier childHandler) {
        LOGGER.debug("handleSubmit");
        
        try {
            this.validate();

            boolean result = childHandler.getAsBoolean();

            LOGGER.debug(result);

            if (result) {
                this.nameInput.clear();
                this.addressInput.clear();
                this.address2Input.clear();
                this.cityInput.getSelectionModel().clearSelection();
                this.postalCodeInput.clear();
                this.phoneInput.clear();
                this.countryInput.clear();

                ((Node) event.getSource()).getScene().getWindow().hide();
            }
        } catch (InvalidCustomerException ex) {
            this.errorMessage.setText(ex.getMessage());
            
            LOGGER.warn(ex.getMessage());
        }
    }
    
    private void validate() throws InvalidCustomerException {
        if (this.isInvalidData()) {
            throw new InvalidCustomerException();
        }
    }
    
    private boolean isInvalidData() {
        return (this.nameInput.getText().isEmpty() ||
            this.addressInput.getText().isEmpty() ||
            this.cityInput.getValue() == null ||
            this.postalCodeInput.getText().isEmpty() ||
            this.phoneInput.getText().isEmpty() ||
            this.countryInput.getText().isEmpty());
    }
}
