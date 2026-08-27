package main.java.com.pbcorporations.abarroteria.kinal.service;

import main.java.com.pbcorporations.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.pbcorporations.abarroteria.kinal.dto.response.LoginDTOResponse;
import main.java.com.pbcorporations.abarroteria.kinal.repository.AuthRepository;
import main.java.com.pbcorporations.abarroteria.kinal.security.jbcrypt.BCrypt;

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
}
