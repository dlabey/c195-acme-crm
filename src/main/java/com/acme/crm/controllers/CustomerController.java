package com.acme.crm.controllers;

import javax.inject.Inject;
import java.net.URL;
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
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import com.acme.crm.dao.CityDAO;
import com.acme.crm.dao.CountryDAO;
import com.acme.crm.entities.CityEntity;
import com.acme.crm.entities.CountryEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerController extends MainController implements Initializable {

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    @Inject
    private CityDAO cityDAO;
    
    @Inject
    private CountryDAO countryDAO;

    @FXML
    protected Text headingText;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField addressInput;

    @FXML
    private TextField address2Input;

    @FXML
    private ComboBox<CityEntity> cityInput;

    @FXML
    private TextField postalCodeInput;

    @FXML
    private TextField countryInput;

    @FXML
    private CheckBox activeInput;

    @FXML
    private Text errorMessage;

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
            List<CityEntity> cities = this.cityDAO.getCities();

            this.cityInput.setItems(FXCollections.observableList(cities));
        } catch (Exception e) {
            logger.debug(e);
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
    private void handleCountryLabelClick(MouseEvent event) {
        this.countryInput.requestFocus();
    }

    @FXML
    private void handleActiveLabelClick(MouseEvent event) {
        this.activeInput.requestFocus();
    }

    @FXML
    private void handleCitySelect(ActionEvent event) {
        logger.debug("handleCitySelect");
        
        CityEntity city = this.cityInput.getValue();
        
        try {
            CountryEntity country = this.countryDAO.getCountryByCountryId(city.getCountryId());
            
            logger.debug(country.getCountry());
            
            this.countryInput.setText(country.getCountry());
        } catch (Exception e) {
            errorMessage.setText("Application error");
            
            logger.debug(e.getMessage());
        }
    }

    @FXML
    protected void handleSubmit(MouseEvent event) throws Exception {
        if (this.nameInput.getText().isEmpty() ||
            this.addressInput.getText().isEmpty() ||
            this.cityInput.getValue() == null ||
            this.postalCodeInput.getText().isEmpty() ||
            this.countryInput.getText().isEmpty()) {
            throw new Exception("Fields marked with * are required");
        } else {
            
        }
    }
}
