package main.java.com.pbcorporations.abarroteria.kinal.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.com.pbcorporations.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.pbcorporations.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.pbcorporations.abarroteria.kinal.dto.response.LoginDTOResponse;

public class AuthRepository {
    
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
    
}
