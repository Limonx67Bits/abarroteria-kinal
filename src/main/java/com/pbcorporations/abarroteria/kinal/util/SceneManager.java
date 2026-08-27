package main.java.com.pbcorporations.abarroteria.kinal.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.com.pbcorporations.abarroteria.kinal.controller.LoginController;
import main.java.com.pbcorporations.abarroteria.kinal.repository.AuthRepository;
import main.java.com.pbcorporations.abarroteria.kinal.service.AuthService;

public class SceneManager {
    private final Stage stage;
    
    public SceneManager(Stage stage){
        this.stage = stage;
    }
    
    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/login-view.fxml"));
        
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
    
        public void showDashboardView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/dashboard-view.fxml"));
            
            Parent root = loader.load();
            
            Scene scene = new Scene(root, 1024, 728);
            
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlertInfo("Error de Navegación", "No se pudo cargar la vista", "Error al abrir el Dashboard: " + e.getMessage(), AlertType.ERROR);
        }
    }
}