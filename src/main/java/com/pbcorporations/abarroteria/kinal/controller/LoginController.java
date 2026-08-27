package main.java.com.pbcorporations.abarroteria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import javafx.scene.control.PasswordField;
import main.java.com.pbcorporations.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.pbcorporations.abarroteria.kinal.dto.response.LoginDTOResponse;
import main.java.com.pbcorporations.abarroteria.kinal.service.AuthService;
import main.java.com.pbcorporations.abarroteria.kinal.util.SceneManager;

public class LoginController implements Initializable {
    private AuthService authService;
    private SceneManager sceneManager;
    
    @FXML
    private ImageView imgLogin;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Button btnRegistrarse;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private PasswordField pwField;
    
    
    public LoginController(AuthService authService, SceneManager sceneManager){
        this.authService = authService;
        this.sceneManager = sceneManager;
    }
    
    public LoginController(){
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    public void handleLogin(){
        if(txtFieldEmail.getText().isEmpty() || pwField.getText().isEmpty()){
            sceneManager.showAlertInfo("Campos sin llenar", "No se pueden dejar espacios en blanco", "Intenta de nuevo", Alert.AlertType.INFORMATION);
        }else{
            try{
                LoginDTOResponse response = authService.login(new LoginDTORequest(txtFieldEmail.getText(), pwField.getText()));
                sceneManager.showAlertInfo("Es bueno verte de nuevo", "¡Bienvenido " + response.getNombre() + "!", "Inicio de sesión correcto", Alert.AlertType.INFORMATION);
                sceneManager.showDashboardView();
            }catch (RuntimeException re){
                sceneManager.showAlertInfo("Error al iniciar sesión", "Verificar campos", "No se ha podido iniciar sesión", Alert.AlertType.WARNING);
            }
        }
    }
    
    @FXML
    public void handleRegister(){
        
    }
}
