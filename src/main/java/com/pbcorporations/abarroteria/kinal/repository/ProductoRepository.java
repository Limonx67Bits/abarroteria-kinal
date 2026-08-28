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
            System.out.println("Error en mi la base de datos" + e.getMessage());
        }
        return lista;
    }
}
