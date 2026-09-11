package main.java.com.pbcorporations.abarroteria.kinal.model;

public class Telefono {
    private String idTelefono;
    private String idUsuario;
    private String telefono;
    
    public Telefono(String idTelefono, String idUsuario, String telefono){
        this.idTelefono = idTelefono;
        this.idUsuario = idUsuario;
        this.telefono = telefono;
    }
    
    public String getIdTelefono(){
        return idTelefono;
    }
    
    public void setIdTelefono(String idTelefono){
        this.idTelefono = idTelefono;
    }
    
    public String getIdUsuario(){
        return idUsuario;
    }
    
    public void setIdUsuario(String idUsuario){
        this.idUsuario = idUsuario;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
}
