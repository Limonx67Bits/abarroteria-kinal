/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.pbcorporations.abarroteria.kinal.controller;

import java.util.UUID;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.pbcorporations.abarroteria.kinal.model.Usuario;
import main.java.com.pbcorporations.abarroteria.kinal.repository.AuthRepository;
import main.java.com.pbcorporations.abarroteria.kinal.security.jbcrypt.BCrypt;
import main.java.com.pbcorporations.abarroteria.kinal.service.AuthService;
import main.java.com.pbcorporations.abarroteria.kinal.util.SceneManager;

/**
 *
 * @author dbarrientos
 */
public class AuthController {
    

    private AuthService authService;
    private SceneManager sceneManager;
    
        public AuthController( AuthService authService, SceneManager sceneManager){
          
            this.authService = authService;
            this.sceneManager = sceneManager;
        }
     
     @FXML
     private TextField txtNombre;
     @FXML
     private TextField txtApellido;
     @FXML
     private TextField txtEmail;
     @FXML
     private PasswordField txtContrasena;
     @FXML
     private TextField txtId_rol;
     @FXML
     private Button btnRegistrarse;
     @FXML
     private Button btnRegresar;
     
     public void registerUser(){
         boolean registro;
        if(txtNombre.getText().isBlank() || txtNombre.getText().isEmpty() ||
        txtApellido.getText().isBlank() || txtApellido.getText().isEmpty() ||
        txtEmail.getText().isBlank() || txtEmail.getText().isEmpty() ||
        txtContrasena.getText().isBlank() || txtContrasena.getText().isEmpty() ||
        txtId_rol.getText().isBlank() || txtId_rol.getText().isEmpty()){
            sceneManager.showAlertInfo("Campos vacios", "Verificar campos", "No se ha podido registrar el usuario", Alert.AlertType.ERROR);
        }else{
        try{ 
        String contrasena_hash = BCrypt.hashpw(txtContrasena.getText(), BCrypt.gensalt(12));
        Usuario user = new Usuario();
        user.setId_usuario(UUID.randomUUID().toString());
        user.setNombre(txtNombre.getText());
        user.setApellido(txtApellido.getText());
        user.setEmail(txtEmail.getText());
        user.setContrasena_hash(contrasena_hash);
        user.setId_rol(Integer.parseInt(txtId_rol.getText()));
        registro = authService.makeNewUser(user);
        if (registro){
            sceneManager.showAlertInfo("Registro exitoso", "Su cuenta se ah creado con exito", "Bienvenido a abarroteria kinal", Alert.AlertType.CONFIRMATION); 
        }else{
            sceneManager.showAlertInfo("Error al Registrarse", "Verificar campos", "No se ha podido registrar el usuario", Alert.AlertType.ERROR);
        }
        }catch (RuntimeException re){
                sceneManager.showAlertInfo("Error al Registrar", "Verificar la Base de Datos", "No se ha podido registrar el usuario", Alert.AlertType.WARNING);
            }
        }
     }
     
     public void exit() throws Exception{
         sceneManager.showLoginView();
     }
     
     
}
