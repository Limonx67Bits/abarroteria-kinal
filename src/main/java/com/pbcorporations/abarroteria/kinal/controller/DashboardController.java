package main.java.com.pbcorporations.abarroteria.kinal.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
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
    private TableColumn<Producto, Integer> columnStock;
    @FXML
    private TableColumn<Producto, BigDecimal> columnPrecio;

    @FXML
    private Label lblTotalProductos;
    @FXML
    private Label lblValorInventario;
    @FXML
    private Label lblStockBajoCount;

    public DashboardController(DashboardService service, SceneManager manager) {
        this.service = service;
        this.manager = manager;
    }

    public DashboardController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosEnTabla();
        actualizarMetricas();
    }

    private void configurarTabla() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        columnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tableViewProductos.setRowFactory(tv -> new TableRow<Producto>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("");
                } else if (item.getStock() < 5) {
                    setStyle("-fx-background-color: #ffcccc; -fx-text-fill: #b30000; -fx-font-weight: bold;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    @FXML 
    private void cargarDatosEnTabla() {
        if (service != null) {
            tableViewProductos.setItems(service.getListaProductos());
        }
    }

    private void actualizarMetricas() {
        if (service != null && lblTotalProductos != null) {
            int totalProd = service.obtenerTotalProductos();
            BigDecimal valorTotal = service.obtenerValorTotalInventario();
            int bajoStock = service.obtenerConteoStockBajo();

            lblTotalProductos.setText(String.valueOf(totalProd));
            lblValorInventario.setText(String.format("Q%.2f", valorTotal));
            lblStockBajoCount.setText(String.valueOf(bajoStock));
        }
    }

    @FXML
    private void handleEliminarProducto() {
        Producto productoSeleccionado = tableViewProductos.getSelectionModel().getSelectedItem();
        
        if (productoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de eliminar el producto?");
            alert.setContentText("Producto: " + productoSeleccionado.getNombreProducto() + "\nEsta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean productoEliminado = service.eliminarProducto(productoSeleccionado);
                if (productoEliminado) {
                    tableViewProductos.getItems().remove(productoSeleccionado);
                    actualizarMetricas();
                    if (manager != null) {
                        manager.showAlertInfo("Eliminación exitosa", "Eliminando...", "El objeto fue borrado de la base de datos con éxito.", Alert.AlertType.INFORMATION);
                    }
                } else {
                    if (manager != null) {
                        manager.showAlertInfo("Eliminación fallida", "Eliminando...", "El objeto no fue eliminado de la base de datos.", Alert.AlertType.ERROR);
                    }
                }
            }
        } else {
            if (manager != null) {
                manager.showAlertInfo("Eliminación inválida", "Eliminando...", "No has seleccionado ningún objeto para eliminar.", Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    private void handleActualizarProducto() {
        Producto productoSeleccionado = tableViewProductos.getSelectionModel().getSelectedItem();
        
        if (productoSeleccionado != null) {
           
            Dialog<Producto> dialog = new Dialog<>();
            dialog.setTitle("Editar Producto");
            dialog.setHeaderText("Modifique los datos del producto (ID: " + productoSeleccionado.getIdProducto() + "):");

            ButtonType btnGuardarType = new ButtonType("Guardar Cambios", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(btnGuardarType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

           
            TextField txtNombre = new TextField(productoSeleccionado.getNombreProducto());
            TextField txtStock = new TextField(String.valueOf(productoSeleccionado.getStock()));
            TextField txtPrecio = new TextField(productoSeleccionado.getPrecio() != null ? productoSeleccionado.getPrecio().toString() : "");

            grid.add(new Label("Nombre:"), 0, 0);
            grid.add(txtNombre, 1, 0);
            grid.add(new Label("Stock:"), 0, 1);
            grid.add(txtStock, 1, 1);
            grid.add(new Label("Precio (Q):"), 0, 2);
            grid.add(txtPrecio, 1, 2);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnGuardarType) {
                    try {
                        String nuevoNombre = txtNombre.getText().trim();
                        int nuevoStock = Integer.parseInt(txtStock.getText().trim());
                        BigDecimal nuevoPrecio = new BigDecimal(txtPrecio.getText().trim());

                        productoSeleccionado.setNombreProducto(nuevoNombre);
                        productoSeleccionado.setStock(nuevoStock);
                        productoSeleccionado.setPrecio(nuevoPrecio);

                        return productoSeleccionado;
                    } catch (NumberFormatException e) {
                        if (manager != null) {
                            manager.showAlertInfo("Error de datos", "Datos inválidos", "Ingrese valores numéricos válidos en stock y precio.", Alert.AlertType.ERROR);
                        }
                        return null;
                    }
                }
                return null;
            });

            Optional<Producto> result = dialog.showAndWait();

            result.ifPresent(productoEditado -> {
                try {
                    boolean productoActualizado = service.actualizarProducto(productoEditado);
                    if (productoActualizado) {
                        tableViewProductos.refresh();
                        actualizarMetricas();
                        if (manager != null) {
                            manager.showAlertInfo("Actualización exitosa", "Actualizando...", "El objeto fue modificado en la base de datos con éxito.", Alert.AlertType.INFORMATION);
                        }
                    } else {
                        if (manager != null) {
                            manager.showAlertInfo("Actualización fallida", "Actualizando...", "El objeto no pudo ser actualizado.", Alert.AlertType.ERROR);
                        }
                    }
                } catch (RuntimeException e) {
                    if (manager != null) {
                        manager.showAlertInfo("Error de validación", "Atención", e.getMessage(), Alert.AlertType.WARNING);
                    }
                }
            });

        } else {
            if (manager != null) {
                manager.showAlertInfo("Actualización inválida", "Actualizando...", "No has seleccionado ningún objeto para actualizar.", Alert.AlertType.WARNING);
            }
        }
    }
    
    @FXML
    private void handleAgregarProducto() {
      
        Dialog<Producto> dialog = new Dialog<>();
        dialog.setTitle("Agregar Producto");
        dialog.setHeaderText("Ingrese los datos del nuevo producto:");

        ButtonType btnGuardarType = new ButtonType("Guardar", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardarType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del producto");
        TextField txtStock = new TextField();
        txtStock.setPromptText("Cantidad en stock");
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio (ej. 10.50)");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Stock:"), 0, 1);
        grid.add(txtStock, 1, 1);
        grid.add(new Label("Precio (Q):"), 0, 2);
        grid.add(txtPrecio, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardarType) {
                try {
                    String nombre = txtNombre.getText().trim();
                    int stock = Integer.parseInt(txtStock.getText().trim());
                    BigDecimal precio = new BigDecimal(txtPrecio.getText().trim());

                    Producto nuevoProducto = new Producto();
                    nuevoProducto.setNombreProducto(nombre);
                    nuevoProducto.setStock(stock);
                    nuevoProducto.setPrecio(precio);

                    return nuevoProducto;
                } catch (NumberFormatException e) {
                    if (manager != null) {
                        manager.showAlertInfo("Error de datos", "Datos inválidos", "Ingrese valores numéricos válidos en stock y precio.", Alert.AlertType.ERROR);
                    }
                    return null;
                }
            }
            return null;
        });

        Optional<Producto> result = dialog.showAndWait();

        result.ifPresent(nuevoProducto -> {
            try {
                boolean agregado = service.agregarProducto(nuevoProducto);
                if (agregado) {
                    cargarDatosEnTabla();
                    actualizarMetricas();
                    if (manager != null) {
                        manager.showAlertInfo("Éxito", "Producto Agregado", "El producto fue registrado con éxito.", Alert.AlertType.INFORMATION);
                    }
                } else {
                    if (manager != null) {
                        manager.showAlertInfo("Error", "Error al guardar", "No se pudo registrar el producto en la base de datos.", Alert.AlertType.ERROR);
                    }
                }
            } catch (RuntimeException e) {
                if (manager != null) {
                    manager.showAlertInfo("Error de validación", "Atención", e.getMessage(), Alert.AlertType.WARNING);
                }
            }
        });
    }
}
