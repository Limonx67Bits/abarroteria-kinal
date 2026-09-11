package main.java.com.pbcorporations.abarroteria.kinal.dto.request;

public class ClienteDTORequest {
    private String nombre;
    private String apellido;
    
    private String telefono;
    
    private String ciudad;
    private String zona;
    private String noCasa;
    private String colonia;
    private String calle;
    
    public ClienteDTORequest(){
    }
    
    public ClienteDTORequest(String nombre, String apellido, String telefono, String ciudad, String zona, String noCasa, String colonia, String calle){
        this.nombre = nombre;
        this.apellido = apellido;
        
        this.telefono = telefono;
        
        this.ciudad = ciudad;
        this.zona = zona;
        this.noCasa = noCasa;
        this.colonia = colonia;
        this.calle = calle;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getApellido(){
        return apellido;
    }
    
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
    public String getCiudad(){
        return ciudad;
    }
    
    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }
    
    public String getZona(){
        return zona;
    }
    
    public void setZona(String zona){
        this.zona = zona;
    }
    
    public String getNoCasa(){
        return noCasa;
    }
    
    public void setNoCasa(){
        this.noCasa = noCasa;
    }
    
    public String getColonia(){
        return colonia;
    }
    
    public void setColonia(String colonia){
        this.colonia = colonia;
    }
    
    public String getCalle(){
        return calle;
    }
    
    public void setCalle(String calle){
        this.calle = calle;
    }
}
