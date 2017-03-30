package com.acme.crm.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class CustomerController extends MainController implements Initializable {
    
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
        // load cities
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
