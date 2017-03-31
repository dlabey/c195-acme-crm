package com.acme.crm.controllers;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import com.acme.crm.dao.CityDAO;
import com.acme.crm.entities.CityEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class CustomerController extends MainController implements Initializable {
    
    private static final Logger logger = LogManager.getLogger(CustomerController.class);
    
    @Inject
    private CityDAO cityDAO;
    
    @FXML
    protected Text headingText;
    
    @FXML
    private TextField nameInput;
    
    @FXML
    private TextField addressInput;
    
    @FXML
    private TextField address2Input;
    
    @FXML
    private ComboBox cityInput;
    
    @FXML
    private TextField postalCodeInput;
    
    @FXML
    private TextField countryInput;
    
    @FXML
    private CheckBox activeInput;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadCities();
    }
    
    private void loadCities() {
        try {
            Set<CityEntity> cities = this.cityDAO.getCities();
            
            List<String> cityNames = cities.stream().map(city -> city.getCity())
                    .collect(Collectors.toList());
            
            this.cityInput.setItems(FXCollections.observableList(cityNames));
        } catch (Exception e) {
            System.out.println("Exception");
            System.out.println(e.getMessage());
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
    private void handleCitySelect(MouseEvent event) {
        this.activeInput.requestFocus();
    }
    
    @FXML
    protected void handleSubmit(MouseEvent event) {
        // process
    }
}
