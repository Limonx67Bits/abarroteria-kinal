package main.java.com.pbcorporations.abarroteria.kinal.dto.response;

public class ClienteDTOResponse {
    private String idCliente;
    private String nombreCliente;
    private String telefonoCliente;
    private String direccionCliente;
    
    public ClienteDTOResponse(String idCliente, String nombreCliente, String telefonoCliente, String direccionCliente){
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
    }
    
    public String getIdCliente(){
        return idCliente;
    }
    
    public void setIdCliente(String idCliente){
        this.idCliente = idCliente;
    }
    
    public String getNombreCliente(){
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente){
        this.nombreCliente = nombreCliente;
    }
    
    public String getTelefonoCliente(){
        return telefonoCliente;
    }
    
    public void setTelefonoCliente(String telefonoCliente){
        this.telefonoCliente = telefonoCliente;
    }
    
    public String getDireccionCliente(){
        return direccionCliente;
    }
    
    public void setDireccionCliente(String direccionCliente){
        this.direccionCliente = direccionCliente;
    }
}