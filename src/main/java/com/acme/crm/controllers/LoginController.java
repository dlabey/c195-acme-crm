package com.acme.crm.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javax.inject.Inject;

import com.acme.crm.dao.UserDAO;
import com.acme.crm.entities.UserEntity;
import com.acme.crm.exceptions.InvalidUserException;
import com.acme.crm.services.SessionService;

/**
 *
 * @author darren
 */
public class LoginController extends MainController implements Initializable {
    
    private final UserDAO userDAO;
    
    private final SessionService sessionService;
    
    @FXML
    private TextField usernameInput;
    
    @FXML
    private PasswordField passwordInput;
    
    @FXML
    private Text errorMessage;
    
    @Inject
    public LoginController(UserDAO userDAO, SessionService sessionService) {
        this.userDAO = userDAO;
        this.sessionService = sessionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }
    
    @FXML
    private void handleUsernameLabelClick(MouseEvent event) {
        this.usernameInput.requestFocus();
    }
    
    @FXML
    private void handlePasswordLabelClick(MouseEvent event) {
        this.passwordInput.requestFocus();
    }
    
    @FXML
    private void handleLoginButtonClick(MouseEvent event) {
        final String userName = this.usernameInput.getText();
        final String password = this.passwordInput.getText();
        
        try {
            UserEntity user = userDAO.getUserByUserNameAndPassword(userName, password);
            
            sessionService.setUser(user);
            
            // go to customer scene
        } catch (InvalidUserException e) {
            errorMessage.setText("User invalid error");
        } catch (Exception e) {
            errorMessage.setText("Application error");
        }
    }
}
