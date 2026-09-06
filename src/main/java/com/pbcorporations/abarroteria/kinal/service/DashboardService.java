package main.java.com.pbcorporations.abarroteria.kinal.service;

import java.math.BigDecimal;
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
    
    public boolean agregarProducto(Producto producto) {
        if (producto == null) {
            throw new RuntimeException("Los datos del producto están vacíos");
        }else if (producto.getNombreProducto() == null || producto.getNombreProducto().isEmpty()
                || producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0
                || producto.getStock() <= 0) {
            throw new RuntimeException("Revise los datos ingresados");
        }
       return repository.agregar(producto);     
        
    }
    
    public boolean actualizarProducto(Producto producto) {
        if(producto == null){
            throw new RuntimeException("Los datos del producto son vacios");
        }else if (producto.getNombreProducto() == null || producto.getNombreProducto().isEmpty()
                || producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0
                || producto.getStock() <= 0){
            throw new RuntimeException("Revise los datos ingresados");
        }
        return repository.update(producto);
    }
}