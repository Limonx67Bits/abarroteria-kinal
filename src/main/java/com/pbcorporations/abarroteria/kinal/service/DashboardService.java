package main.java.com.pbcorporations.abarroteria.kinal.service;

import javafx.collections.ObservableList;
import main.java.com.pbcorporations.abarroteria.kinal.model.Producto;
import main.java.com.pbcorporations.abarroteria.kinal.repository.ProductoRepository;

public class DashboardService {
    private final ProductoRepository repository;
    
    public DashboardService(ProductoRepository repository){
        this.repository = repository;
    }
    
    public ObservableList<Producto> getListaProductos(){
        if(repository.findAll() == null){
            throw new RuntimeException("Sin productos");
        }
        return repository.findAll();
    }
    
    public boolean eliminarProducto(Producto producto){
        if(producto != null){
            return repository.delete(producto);
        }
        return false;
    }
}
