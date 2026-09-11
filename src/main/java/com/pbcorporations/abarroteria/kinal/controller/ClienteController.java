package main.java.com.pbcorporations.abarroteria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
}
