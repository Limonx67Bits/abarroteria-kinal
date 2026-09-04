package main.java.com.pbcorporations.abarroteria.kinal.model;

/**
 *
 * @author dbarrientos
 */

public class Usuario {
    
   private String id_usuario;
   private String nombre;
   private String apellido;
   private String email;
   private String contrasena_hash;
   private int id_rol;

   public Usuario(){
       
   }
   
    public Usuario(String id_usuario, String nombre, String apellido, String email, String contrasena_hash, int id_rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena_hash = contrasena_hash;
        this.id_rol = id_rol;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena_hash() {
        return contrasena_hash;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasena_hash(String contrasena_hash) {
        this.contrasena_hash = contrasena_hash;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
   
    
    
}
