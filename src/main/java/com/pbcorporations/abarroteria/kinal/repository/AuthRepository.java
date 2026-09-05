package main.java.com.pbcorporations.abarroteria.kinal.repository;

import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.com.pbcorporations.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.pbcorporations.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.pbcorporations.abarroteria.kinal.dto.response.LoginDTOResponse;
import main.java.com.pbcorporations.abarroteria.kinal.model.Usuario;

/**
 *
 * @author dbarrientos
 */

public class AuthRepository {
    
    private Boolean estado = false;
    
    public LoginDTOResponse findUserByEmail(LoginDTORequest request){
        
        String sql = "select u.nombre, u.apellido, u.contrasena_hash, r.nombre_rol\n" +
                           "from usuarios as u\n" +
                           "inner join roles as r\n" +
                           "on u.id_rol = r.id_rol\n" +
                           "where  u.email = ? ";
        
        try(PreparedStatement ps = DataBaseConnection.getDBConnection().prepareStatement(sql)){
            ps.setString(1, request.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new LoginDTOResponse(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("contrasena_hash"),
                        rs.getString("nombre_rol")
                );
            }
        }catch(SQLException e){
            System.out.println("Error al buscar en la base de datos" + e.getMessage());
        }
        return null;
    }
    
     public boolean save(Usuario usuario){
        
        String sql = "INSERT INTO usuarios (id_usuario, nombre, apellido, email, contrasena_hash, id_rol) VALUES (?, ?, ?, ?, ?, ?);";
        
        try(PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql);){
            
            pstm.setString(1, usuario.getId_usuario());
            pstm.setString(2, usuario.getNombre());
            pstm.setString(3, usuario.getApellido());
            pstm.setString(4, usuario.getEmail());
            pstm.setString(5, usuario.getContrasena_hash());
            pstm.setInt(6, usuario.getId_rol());
            pstm.execute();
            
            estado = true;
            
        }catch(SQLException e){
            
            System.out.println("Error al registrar usuario " + e.getMessage());
            
        }
        
        return estado;
    }
    

    
}


