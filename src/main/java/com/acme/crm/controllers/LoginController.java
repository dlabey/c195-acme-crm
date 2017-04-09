package com.acme.crm.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.acme.crm.dao.UserDAO;
import com.acme.crm.entities.UserEntity;
import com.acme.crm.exceptions.InvalidUserException;
import com.acme.crm.services.ContextService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController extends MainController implements Initializable {

    private static final Level level = Level.forName("LOGIN", 401);

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Inject
    private FXMLLoader loader;
    
    @Inject
    UserDAO userDAO;

    @Inject
    ContextService contextService;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Text errorMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.errorMessage.setText("");
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
    private void handleKeyPress(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {
            handleLogin(event);
        }
    }

    @FXML
    private void handleLogin(InputEvent event) throws Exception {
        logger.debug("Login");

        final String userName = this.usernameInput.getText();
        final String password = this.passwordInput.getText();

        //try {
            if (userName.equals("") || password.equals("")) {
                throw new InvalidUserException();
            }

            UserEntity user = this.userDAO.getUserByUserNameAndPassword(userName, password);

            this.contextService.setUser(user);
            
            this.loader.setLocation(getClass().getResource("/ui/Manage.fxml"));
            
            Parent root = this.loader.load();
            
            ((Node) event.getSource()).getScene().getWindow().hide();
            
            Stage stage = new Stage();
            stage.setTitle("Manage");
            stage.setScene(new Scene(root));
            stage.show();
//        } catch (InvalidUserException e) {
//            errorMessage.setText("User invalid error");
//
//            logger.debug(e.getMessage());
//        } catch (SQLException e) {
//            errorMessage.setText("Application error");
//
//            logger.debug(e.getMessage());
//        }
    }
}
