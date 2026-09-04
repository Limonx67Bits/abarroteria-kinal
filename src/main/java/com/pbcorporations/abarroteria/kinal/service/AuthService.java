package main.java.com.pbcorporations.abarroteria.kinal.service;

import main.java.com.pbcorporations.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.pbcorporations.abarroteria.kinal.dto.response.LoginDTOResponse;
import main.java.com.pbcorporations.abarroteria.kinal.model.Usuario;
import main.java.com.pbcorporations.abarroteria.kinal.repository.AuthRepository;
import main.java.com.pbcorporations.abarroteria.kinal.security.jbcrypt.BCrypt;
/**
 *
 * @author dbarrientos
 */

public class AuthService {
    private final AuthRepository authRepository;
    
    public AuthService(AuthRepository authRepository){
        this.authRepository = authRepository;
    }
    
    public LoginDTOResponse login(LoginDTORequest request){
        if(request == null){
            throw new RuntimeException("Los datos están vacíos");
        }else if (request.getEmail() == null || request.getPassword() == null){
            throw new RuntimeException("Se encontraron campos vacíos");
        }else if (request.getEmail().isEmpty() || request.getPassword().isEmpty()){
            throw new RuntimeException("No pueden existir campos en blanco");
        }
        LoginDTOResponse response = authRepository.findUserByEmail(request);
        
        if(response == null){
            System.out.println("No se encontró el usuario");
        }
        
        if(response.getContrasenaHash() == null){
            throw new RuntimeException("No se logró concretar la operación");
        }else{
            if(BCrypt.checkpw(request.getPassword(), response.getContrasenaHash())){
                return response;
            }
        }
        return null;
    }
    
        public boolean makeNewUser(Usuario user) {
           boolean registro;
        String hashContrasena = BCrypt.hashpw(user.getContrasena_hash(), BCrypt.gensalt(12));
        Usuario userARegistrar = new Usuario();
        userARegistrar.setId_usuario(user.getId_usuario());
        userARegistrar.setNombre(user.getNombre());
        userARegistrar.setApellido(user.getApellido());
        userARegistrar.setEmail(user.getEmail());
        userARegistrar.setContrasena_hash(hashContrasena);
        userARegistrar.setId_rol(user.getId_rol());
        registro = authRepository.save(user);
        return registro;
    }
}
