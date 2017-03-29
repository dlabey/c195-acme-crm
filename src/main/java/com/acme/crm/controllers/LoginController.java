package com.acme.crm.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

import com.acme.crm.dao.UserDAO;
import com.acme.crm.entities.UserEntity;
import com.acme.crm.exceptions.InvalidUserException;
import com.acme.crm.services.SessionService;
import org.apache.logging.log4j.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController extends MainController implements Initializable {
    
    private static final Level level = Level.forName("LOGIN", 401);
    
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    
    @Inject UserDAO userDAO;
    
    @Inject SessionService sessionService;
    
    @FXML
    private TextField usernameInput;
    
    @FXML
    private PasswordField passwordInput;
    
    @FXML
    private Text errorMessage;

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
    private void handleLoginButtonClick(MouseEvent event) throws Exception {
        final String userName = this.usernameInput.getText();
        final String password = this.passwordInput.getText();
        
        logger.debug("Login");
        
        try {
            if (userName.equals("") || password.equals("")) {
                throw new InvalidUserException();
            }
            
            UserEntity user = this.userDAO.getUserByUserNameAndPassword(userName, password);
            
            sessionService.setUser(user);
            
            logger.log(level, userName);
            
            // go to customer management
        } catch (InvalidUserException e) {
            errorMessage.setText("User invalid error");
        } catch (Exception e) {
            errorMessage.setText("Application error");
        }
    }
}
