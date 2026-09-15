package main.java.com.pbcorporations.abarroteria.kinal.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import main.java.com.pbcorporations.abarroteria.kinal.dto.request.ClienteDTORequest;
import main.java.com.pbcorporations.abarroteria.kinal.dto.response.ClienteDTOResponse;
import main.java.com.pbcorporations.abarroteria.kinal.service.ClienteService;
import main.java.com.pbcorporations.abarroteria.kinal.util.SceneManager;

public class ClienteController implements Initializable {

    private ClienteService service;
    private SceneManager manager;

    @FXML
    private TextField txtBuscarCliente;
    @FXML
    private TableView<ClienteDTOResponse> tableClientes;
    @FXML
    private TableColumn<ClienteDTOResponse, String> columnIdCliente;
    @FXML
    private TableColumn<ClienteDTOResponse, String> columnNombreCliente;
    @FXML
    private TableColumn<ClienteDTOResponse, String> columnTelefonoCliente;
    @FXML
    private TableColumn<ClienteDTOResponse, String> columnDireccionCliente;

    public ClienteController(ClienteService service, SceneManager manager) {
        this.service = service;
        this.manager = manager;
    }

    public ClienteController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadClientsInTable();
    }

    @FXML
    public void loadClientsInTable() {
        columnIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        columnNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        columnTelefonoCliente.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
        columnDireccionCliente.setCellValueFactory(new PropertyValueFactory<>("direccionCliente"));
        tableClientes.setItems(service.getAllClients());
    }

    @FXML
    public void handleAgregarCliente() {
        Optional<ClienteDTORequest> resultado = mostrarFormularioFlotante(null);

        resultado.ifPresent(request -> {
            boolean exito = service.saveClient(request);
            if (exito) {
                manager.showAlertInfo("Éxito", "Registrando...", "Cliente registrado correctamente.", Alert.AlertType.INFORMATION);
                loadClientsInTable();
            } else {
                manager.showAlertInfo("Error", "Registrando...", "No se pudo registrar el cliente.", Alert.AlertType.ERROR);
            }
        });
    }

    @FXML
    public void handleActualizarCliente() {
        ClienteDTOResponse seleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            manager.showAlertInfo("Advertencia", "Selección invalida", "Selecciona un cliente de la tabla.", Alert.AlertType.WARNING);
            return;
        }

        Optional<ClienteDTORequest> resultado = mostrarFormularioFlotante(seleccionado);

        resultado.ifPresent(request -> {
            boolean exito = service.updateClient(seleccionado.getIdCliente(), request);
            if (exito) {
                manager.showAlertInfo("Éxito", "Actualizando...", "Cliente actualizado correctamente.", Alert.AlertType.INFORMATION);
                loadClientsInTable();
            } else {
                manager.showAlertInfo("Error", "Actualizando...", "No se pudo actualizar el cliente.", Alert.AlertType.ERROR);
            }
        });
    }

    @FXML
    public void handleEliminarCliente() {
        ClienteDTOResponse seleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            manager.showAlertInfo("Advertencia", "Selección invalida", "Selecciona un cliente de la tabla para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        boolean exito = service.deleteClient(seleccionado.getIdCliente());
        if (exito) {
            manager.showAlertInfo("Éxito", "Eliminando...", "Cliente eliminado.", Alert.AlertType.INFORMATION);
            loadClientsInTable();
        } else {
            manager.showAlertInfo("Error", "Eliminando...", "No se pudo eliminar el cliente.", Alert.AlertType.ERROR);
        }
    }

    private Optional<ClienteDTORequest> mostrarFormularioFlotante(ClienteDTOResponse cliente) {
        Dialog<ClienteDTORequest> dialog = new Dialog<>();
        dialog.setTitle(cliente == null ? "Agregar Cliente" : "Actualizar Cliente");
        dialog.setHeaderText("Ingresa los datos del cliente:");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        TextField txtNombre = new TextField();
        TextField txtApellido = new TextField();
        TextField txtTelefono = new TextField();
        TextField txtCiudad = new TextField();
        TextField txtZona = new TextField();
        TextField txtNoCasa = new TextField();
        TextField txtColonia = new TextField();
        TextField txtCalle = new TextField();

        if (cliente != null) {
            String[] nombres = cliente.getNombreCliente().split(" ");
            if (nombres.length > 0) {
                txtNombre.setText(nombres[0]);
            }
            if (nombres.length > 1) {
                txtApellido.setText(nombres[1]);
            }
            txtTelefono.setText(cliente.getTelefonoCliente());

            String[] dirPartes = cliente.getDireccionCliente().split(" ");

            if (dirPartes.length > 0) {
                txtCiudad.setText(dirPartes[0]);
            }
            if (dirPartes.length > 1) {
                txtZona.setText(dirPartes[1]);
            }
            if (dirPartes.length > 2) {
                txtNoCasa.setText(dirPartes[2]);
            }

            if (dirPartes.length > 3) {
                txtColonia.setText(dirPartes[3]);
                if (dirPartes.length > 4) {
                    txtCalle.setText(dirPartes[4]);
                }
            }
        }
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Apellido:"), 0, 1);
        grid.add(txtApellido, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(txtTelefono, 1, 2);
        grid.add(new Label("Ciudad:"), 0, 3);
        grid.add(txtCiudad, 1, 3);
        grid.add(new Label("Zona:"), 0, 4);
        grid.add(txtZona, 1, 4);
        grid.add(new Label("No. Casa:"), 0, 5);
        grid.add(txtNoCasa, 1, 5);
        grid.add(new Label("Colonia:"), 0, 6);
        grid.add(txtColonia, 1, 6);
        grid.add(new Label("Calle:"), 0, 7);
        grid.add(txtCalle, 1, 7);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                return new ClienteDTORequest(
                        txtNombre.getText(), txtApellido.getText(), txtTelefono.getText(),
                        txtCiudad.getText(), txtZona.getText(), txtNoCasa.getText(),
                        txtColonia.getText(), txtCalle.getText()
                );
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
