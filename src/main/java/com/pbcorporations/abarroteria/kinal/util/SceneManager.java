package main.java.com.pbcorporations.abarroteria.kinal.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.com.pbcorporations.abarroteria.kinal.controller.AuthController;
import main.java.com.pbcorporations.abarroteria.kinal.controller.DashboardController;
import main.java.com.pbcorporations.abarroteria.kinal.controller.LoginController;
import main.java.com.pbcorporations.abarroteria.kinal.repository.AuthRepository;
import main.java.com.pbcorporations.abarroteria.kinal.repository.ProductoRepository;
import main.java.com.pbcorporations.abarroteria.kinal.service.AuthService;
import main.java.com.pbcorporations.abarroteria.kinal.service.DashboardService;

public class SceneManager {
    private final Stage stage;
    private final String FXML_PATH = "/main/resources/view/";
    
    public SceneManager(Stage stage){
        this.stage = stage;
    }
    
    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "login-view.fxml"));
        
        loader.setControllerFactory(
        clazz -> {
            if(clazz == LoginController.class){
                AuthRepository repository = new AuthRepository();
                AuthService service = new AuthService(repository);
                return new LoginController(service, this);
            }
            
            try{
                return clazz.getDeclaredConstructor().newInstance();
            }catch (Exception e){
                throw new RuntimeException("Error al crear constructor... " + e.getMessage());
            }
        }
        );
        
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        stage.setMinWidth(420);
        stage.setMinHeight(360);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    
    public void showAlertInfo(String head, String title, String content, AlertType type){
        Alert alert = new Alert(type);
        alert.initOwner(this.stage);
        alert.setHeaderText(head);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
        public void showDashboardView() throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "dashboard-view.fxml"));
            
            loader.setControllerFactory(
                    clazz -> {
                        if(clazz == DashboardController.class){
                            ProductoRepository repository = new ProductoRepository();
                            DashboardService service = new DashboardService(repository);
                            return new DashboardController(service, this);
                        }
                        try{
                            return clazz.getDeclaredConstructor().newInstance();
                        }catch (Exception e){
                            throw new RuntimeException("Error al crear constructor... " + e.getMessage());
                        }
                    });
            
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 600);
            stage.setMinHeight(360);
            stage.setMinWidth(420);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
    }
        
    public void showRegisterView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "register-view.fxml"));
        
        loader.setControllerFactory(
        clazz -> {
            if(clazz == AuthController.class){
                AuthRepository repository = new AuthRepository();
                AuthService service = new AuthService(repository);
                return new AuthController(service, this);
            }
            
            try{
                return clazz.getDeclaredConstructor().newInstance();
            }catch (Exception e){
                throw new RuntimeException("Error al crear constructor... " + e.getMessage());
            }
        }
        );
        
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        stage.setMinWidth(420);
        stage.setMinHeight(360);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}