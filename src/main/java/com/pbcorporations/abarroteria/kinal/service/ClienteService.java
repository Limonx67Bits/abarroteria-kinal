package main.java.com.pbcorporations.abarroteria.kinal.service;

import javafx.collections.ObservableList;
import main.java.com.pbcorporations.abarroteria.kinal.dto.request.ClienteDTORequest;
import main.java.com.pbcorporations.abarroteria.kinal.dto.response.ClienteDTOResponse;
import main.java.com.pbcorporations.abarroteria.kinal.repository.ClienteRepository;

public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ObservableList<ClienteDTOResponse> getAllClients() {
        return repository.findAll();
    }

    public ClienteDTOResponse findClientByFilter(String nombre, String apellido) {
        if (nombre == null || nombre.trim().isEmpty()
                || apellido == null || apellido.trim().isEmpty()) {
            System.out.println("No se encontró ningún usuario, pruebe con ingresar campos");
            return null;
        }

        return repository.findByNameAndLastName(nombre.trim(), apellido.trim());
    }

    public boolean saveClient(ClienteDTORequest request) {
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()
                || request.getApellido() == null || request.getApellido().trim().isEmpty()) {
            System.out.println("Debe ingresar el nombre del cliente");
            return false;
        }
        
        if (request.getTelefono() == null || request.getTelefono().trim().isEmpty()){
            System.out.println("Debe ingresar el número de telefono del cliente");
            return false;
        }
        
        if (request.getCiudad() == null || request.getCiudad().trim().isEmpty()
                || request.getZona() == null || request.getZona().trim().isEmpty()
                || request.getNoCasa() == null || request.getNoCasa().trim().isEmpty()
                || request.getColonia() == null || request.getColonia().trim().isEmpty()
                || request.getCalle() == null || request.getColonia().trim().isEmpty()) {
            System.out.println("Debe ingresar la dirección del usuario");
            return false;
        }
        
        return repository.save(request);
    }
    
    public boolean updateClient(String idCliente, ClienteDTORequest request) {
        if (idCliente == null || idCliente.trim().isEmpty()) {
            System.out.println("No se pudo realizar la operación, ingrese un ID valido");
            return false;
        }
        
        return repository.update(idCliente, request);
    }
    
    public boolean deleteClient(String idCliente){
        if (idCliente == null || idCliente.trim().isEmpty()) {
            System.out.println("No se pudo realizar la operación, ingrese un ID valido");
            return false;
        }
        
        return repository.delete(idCliente);
    }
}
