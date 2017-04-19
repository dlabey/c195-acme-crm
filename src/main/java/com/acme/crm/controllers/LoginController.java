package com.acme.crm.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javafx.event.ActionEvent;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController extends MainController implements Initializable {

    private static final Logger LOGGER =
            LogManager.getLogger(LoginController.class);

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
    
    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        this.rb = rb;
        
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
        LOGGER.debug("Login");

        final String userName = this.usernameInput.getText();
        final String password = this.passwordInput.getText();

        try {
            if (userName.equals("") || password.equals("")) {
                throw new InvalidUserException(this.contextService);
            }

            UserEntity user = this.userDAO.getUserByUserNameAndPassword(userName, password);

            this.contextService.setUser(user);
            
            this.loader.setLocation(getClass().getResource("/ui/Manage.fxml"));
            
            Parent root = this.loader.load();
            
            ((Node) event.getSource()).getScene().getWindow().hide();
            
            LOGGER.debug(userName);
            
            this.auditUsername(userName);
            
            Stage stage = new Stage();
            stage.setTitle("Manage");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest((e) -> this.exit());
            
            this.reminderService.scheduleReminders();
        } catch (InvalidUserException ex) {
            errorMessage.setText(ex.getMessage());

            LOGGER.warn(ex.getMessage());
        } catch (SQLException ex) {
            errorMessage.setText(this.rb.getString("Application_error"));

            LOGGER.error(ex.getMessage());
        }
    }
    
    @FXML
    private void handleEnglish(ActionEvent event) {
        this.contextService.setLocale(new Locale("en", "EN"));
        
        this.contextService.getLoginStage().close();
        
        try {
            LoginController.setUp(this.contextService, this.loader,
                    getClass().getResource("/ui/Login.fxml"), new Stage());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    @FXML
    private void handleEspanol(ActionEvent event) {
        this.contextService.setLocale(new Locale("es", "ES"));
        
        this.contextService.getLoginStage().close();
        
        try {
            LoginController.setUp(this.contextService, this.loader,
                    getClass().getResource("/ui/Login.fxml"), new Stage());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    private void auditUsername(String userName) {
        Path log = Paths.get("logins.txt");

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        String line = String.format("%s - %s%n",
                LocalDateTime.now(ZoneOffset.UTC).format(formatter),
                userName);

        try {
            Files.write(log, line.getBytes(), StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    
    public static void setUp(ContextService contextService,
            FXMLLoader loader, URL resource, final Stage stage)
            throws IOException {
        loader.setResources(ResourceBundle.getBundle("bundles.lang",
                contextService.getLocale()));
        loader.setLocation(resource);
        
        Parent root = loader.load();

        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
        
        contextService.setLoginStage(stage);
    }
}
