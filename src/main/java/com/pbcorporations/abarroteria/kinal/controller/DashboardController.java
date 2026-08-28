package main.java.com.pbcorporations.abarroteria.kinal.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
}
