package main.java.com.pbcorporations.abarroteria.kinal.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.pbcorporations.abarroteria.kinal.model.Producto;
import main.java.com.pbcorporations.abarroteria.kinal.service.DashboardService;
import main.java.com.pbcorporations.abarroteria.kinal.util.SceneManager;

public class DashboardController implements Initializable {

    private DashboardService service;
    private SceneManager manager;

    @FXML
    private TableView<Producto> tableViewProductos;
    @FXML
    private TableColumn<Producto, Integer> columnId;
    @FXML
    private TableColumn<Producto, String> columnNombreProducto;
    @FXML
    private TableColumn<Producto, BigDecimal> columnStock;
    @FXML
    private TableColumn<Producto, Integer> columnPrecio;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;

  
    public void configurarVistasPorRol(String nombreRol) {
       
    if (nombreRol == null) {
        ocultarTodo();
        return;
    }

    switch (nombreRol.toLowerCase()) {
        case "admin":
            
            mostrarBoton(btnAgregar, true);
            mostrarBoton(btnActualizar, true);
            mostrarBoton(btnEliminar, true);
            break;

        case "user":
            
            mostrarBoton(btnAgregar, true);
            mostrarBoton(btnActualizar, true);
            mostrarBoton(btnEliminar, false);
            break;

        case "tester":
            
            mostrarBoton(btnAgregar, true);
            mostrarBoton(btnActualizar, true);
            mostrarBoton(btnEliminar, true);
            break;

        case "cliente":
            // Solo lectura
            ocultarTodo();
            break;

        default:
           
            ocultarTodo();
            break;
    }
 }

    private void ocultarTodo() {
    mostrarBoton(btnAgregar, false);
    mostrarBoton(btnActualizar, false);
    mostrarBoton(btnEliminar, false);
    }

    private void mostrarBoton(Button boton, boolean visible) {
    if (boton != null) {
        boton.setVisible(visible);
        boton.setManaged(visible);
    }
   }

    public DashboardController(DashboardService service, SceneManager manager) {
        this.service = service;
        this.manager = manager;
    }

    public DashboardController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosEnTabla();
    }
    
    @FXML 
    private void cargarDatosEnTabla(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        columnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tableViewProductos.setItems(service.getListaProductos());
    }
    
    @FXML
    private void handleEliminarProducto(){
        Producto productoSeleccionado = tableViewProductos.getSelectionModel().getSelectedItem();
        
        if(productoSeleccionado != null){
            boolean productoEliminado = service.eliminarProducto(productoSeleccionado);
            if(productoEliminado){
                tableViewProductos.getItems().remove(productoSeleccionado);
                manager.showAlertInfo("Eliminación exitosa", "Eliminando...", "El objeto fue borrado de la base de datos con exito.", Alert.AlertType.INFORMATION);
            }else{
                manager.showAlertInfo("Eliminación fallida", "Eliminando...", "El objeto no fue eliminado de la base de datos.", Alert.AlertType.ERROR);
            }
        }else{
            manager.showAlertInfo("Eliminación invalida", "Eliminando...", "No haz seleccionado ningún objeto para eliminar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleActualizarProducto(){
        Producto productoSeleccionado = tableViewProductos.getSelectionModel().getSelectedItem();
        
        if(productoSeleccionado != null){
            boolean productoActualizado = service.actualizarProducto(productoSeleccionado);
            if(productoActualizado){
                tableViewProductos.refresh();
                manager.showAlertInfo("Actualización exitosa", "Actualizando...", "El objeto fue modificado en la base de datos con éxito.", Alert.AlertType.INFORMATION);
            }else{
                manager.showAlertInfo("Actualización fallida", "Actualizando...", "El objeto no pudo ser actualizado.", Alert.AlertType.ERROR);
            }
        }else{
            manager.showAlertInfo("Actualización inválida", "Actualizando...", "No has seleccionado ningún objeto para actualizar.", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void handleAgregarProducto(){
        Producto productoSeleccionado = tableViewProductos.getSelectionModel().getSelectedItem();
        
        if(productoSeleccionado != null){
            boolean productoAgregado = service.agregarProducto(productoSeleccionado);
            if(productoAgregado){
                tableViewProductos.refresh();
                manager.showAlertInfo("Producto agregado", "Agregando...", "El objeto fue agregado en la base de datos con exito", Alert.AlertType.INFORMATION);
            }else{
                manager.showAlertInfo("Producto no agregado", "Agregando...", "El objeto no pudo ser agregado", Alert.AlertType.ERROR);
            }
        }else{
            manager.showAlertInfo("Acción invalida", "Agregando...", "No has seleccionado ningún objeto para actualizar.", Alert.AlertType.WARNING);
        }
    }
}