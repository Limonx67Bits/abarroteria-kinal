package main.java.com.pbcorporations.abarroteria.kinal.repository;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.pbcorporations.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.pbcorporations.abarroteria.kinal.dto.request.ClienteDTORequest;
import main.java.com.pbcorporations.abarroteria.kinal.dto.response.ClienteDTOResponse;

public class ClienteRepository {

    public ObservableList<ClienteDTOResponse> findAll() {
        String sql = "select c.id_cliente, CONCAT(c.nombre, ' ', c.apellido) as nombre_cliente, "
                + "t.telefono, "
                + "CONCAT(d.ciudad, ' ', d.zona, ' ', d.noCasa, ' ', d.colonia, ' ', d.calle) as direccion_cliente "
                + "from clientes as c "
                + "left join telefonos as t on c.id_cliente = t.id_cliente "
                + "left join direcciones as d on c.id_direccion = d.id_direccion";
        ObservableList<ClienteDTOResponse> lista = FXCollections.observableArrayList();

        try (Connection conn = DataBaseConnection.getDBConnection(); PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                lista.add(new ClienteDTOResponse(
                        rs.getString("id_cliente"),
                        rs.getString("nombre_cliente"),
                        rs.getString("telefono"),
                        rs.getString("direccion_cliente")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
        return lista;
    }

    public boolean save(ClienteDTORequest request) {
        String sqlDireccion = "insert into direcciones (id_direccion, ciudad, zona, noCasa, colonia, calle) values(?, ?, ?, ?, ?, ?)";
        String sqlCliente = "insert into clientes (id_cliente, id_direccion, nombre, apellido) values(?, ?, ?, ?)";
        String sqlTelefono = "insert into telefonos (id_telefono, id_cliente, telefono) values(?, ?, ?)";

        try (Connection conn = DataBaseConnection.getDBConnection()) {

            conn.setAutoCommit(false);

            try {

                String uuidDireccion = UUID.randomUUID().toString();

                try (PreparedStatement pstm = conn.prepareStatement(sqlDireccion)) {
                    pstm.setString(1, uuidDireccion);
                    pstm.setString(2, request.getCiudad());
                    pstm.setString(3, request.getZona());
                    pstm.setString(4, request.getNoCasa());
                    pstm.setString(5, request.getColonia());
                    pstm.setString(6, request.getCalle());

                    pstm.executeUpdate();
                }

                String uuidCliente = UUID.randomUUID().toString();

                try (PreparedStatement pstm = conn.prepareStatement(sqlCliente)) {
                    pstm.setString(1, uuidCliente);
                    pstm.setString(2, uuidDireccion);
                    pstm.setString(3, request.getNombre());
                    pstm.setString(4, request.getApellido());

                    pstm.executeUpdate();
                }

                String uuidTelefono = UUID.randomUUID().toString();

                try (PreparedStatement pstm = conn.prepareStatement(sqlTelefono)) {
                    pstm.setString(1, uuidTelefono);
                    pstm.setString(2, uuidCliente);
                    pstm.setString(3, request.getTelefono());

                    pstm.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                System.out.println("Error en la operación. Aplicando rollback...");
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
            return false;
        }
    }

    public boolean update(String idCliente, ClienteDTORequest request) {
        String sqlBuscarIds = "select id_direccion from clientes where id_cliente = ?";
        String sqlDireccion = "update direcciones set ciudad = ?, zona = ?, no_casa = ?, colonia = ?, calle = ? where id_direccion = ?";
        String sqlCliente = "update clientes set nombre = ?, apellido = ?, where id_cliente = ?";
        String sqlTelefono = "update telefonos set telefono = ? where id_cliente = ?";

        try (Connection conn = DataBaseConnection.getDBConnection()) {

            conn.setAutoCommit(false);

            try {

                String idDireccion;

                try (PreparedStatement pstm = conn.prepareStatement(sqlBuscarIds)) {
                    pstm.setString(1, idCliente);
                    try (ResultSet rs = pstm.executeQuery()) {
                        if (rs.next()) {
                            idDireccion = rs.getString("id_direccion");
                        } else {
                            return false;
                        }
                    }
                }

                try (PreparedStatement pstm = conn.prepareStatement(sqlDireccion)) {
                    pstm.setString(1, request.getCiudad());
                    pstm.setString(2, request.getZona());
                    pstm.setString(3, request.getNoCasa());
                    pstm.setString(4, request.getColonia());
                    pstm.setString(5, request.getCalle());
                    pstm.setString(6, idDireccion);

                    pstm.executeUpdate();
                }

                try (PreparedStatement pstm = conn.prepareStatement(sqlCliente)) {
                    pstm.setString(1, request.getNombre());
                    pstm.setString(2, request.getApellido());
                    pstm.setString(3, idCliente);

                    pstm.executeUpdate();
                }

                try (PreparedStatement pstm = conn.prepareStatement(sqlTelefono)) {
                    pstm.setString(1, request.getTelefono());
                    pstm.setString(2, idCliente);

                    pstm.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                System.out.println("Error en la operación. Aplicando rollback...");
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(String idCliente) {
        String sqlBuscarIds = "select id_direccion from clientes where id_cliente = ?";
        String sqlDireccion = "delete from direcciones where id_direccion = ?";
        String sqlCliente = "delete from clientes where id_cliente = ?";
        String sqlTelefono = "delete from telefonos where id_cliente = ?";

        try (Connection conn = DataBaseConnection.getDBConnection()) {

            conn.setAutoCommit(false);

            try {

                String idDireccion;

                try (PreparedStatement pstm = conn.prepareStatement(sqlBuscarIds)) {
                    pstm.setString(1, idCliente);

                    try (ResultSet rs = pstm.executeQuery()) {
                        if (rs.next()) {
                            idDireccion = rs.getString("id_direccion");
                        } else {
                            return false;
                        }
                    }
                }

                try (PreparedStatement pstm = conn.prepareStatement(sqlTelefono)) {
                    pstm.setString(1, idCliente);

                    pstm.executeUpdate();
                }

                try (PreparedStatement pstm = conn.prepareStatement(sqlCliente)) {
                    pstm.setString(1, idCliente);

                    pstm.executeUpdate();
                }

                if (idDireccion != null) {
                    try (PreparedStatement pstm = conn.prepareStatement(sqlDireccion)) {
                        pstm.setString(1, idDireccion);

                        pstm.executeUpdate();
                    }
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                System.out.println("Error en la operación. Aplicando rollback...");
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
            return false;
        }
    }

    public ClienteDTOResponse findByNameAndLastName(String nombre, String apellido) {
        String sql = "select c.id_cliente, CONCAT(c.nombre, ' ', c.apellido) as nombre_cliente, "
                + "t.telefono, "
                + "CONCAT(d.ciudad, ' ', d.zona, ' ', d.noCasa, ' ', d.colonia, ' ', d.calle) as direccion_cliente "
                + "from clientes as c "
                + "left join telefonos as t on c.id_cliente = t.id_cliente "
                + "left join direcciones as d on c.id_direccion = d.id_direccion "
                + "where c.nombre = ? and c.apellido = ?";

        try (Connection conn = DataBaseConnection.getDBConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, nombre);
            pstm.setString(2, apellido);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new ClienteDTOResponse(
                            rs.getString("id_cliente"),
                            rs.getString("nombre_cliente"),
                            rs.getString("telefono"),
                            rs.getString("direccion_clientes")
                    );
                }
            } 
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
        return null;
    }
}
