package main.java.com.pbcorporations.abarroteria.kinal.repository;

import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import main.java.com.pbcorporations.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.pbcorporations.abarroteria.kinal.model.Producto;

public class ProductoRepository {

    public ObservableList<Producto> findAll() {
        String sql = "select * from productos";
        ObservableList<Producto> lista = FXCollections.observableArrayList();
        try (PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getInt("stock"),
                        rs.getBigDecimal("precio"))
                );
            }
        }catch(SQLException e){
            System.out.println("Error en la base de datos" + e.getMessage());
        }
        return lista;
    }
    
    public boolean delete(Producto producto){
        String sql = "delete from productos where id_producto = ?";
        boolean estado = false;
        try(PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)){
            ps.setInt(1, producto.getIdProducto());
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas > 0){
                estado = true;
            }
        }catch(SQLException e){
            System.out.println("Error en la base de datos" + e.getMessage());
            estado = false;
        }
        return estado;
    }
}
