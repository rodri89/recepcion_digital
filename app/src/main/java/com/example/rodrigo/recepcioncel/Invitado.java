package com.example.rodrigo.recepcioncel;

/**
 * Created by Rodrigo on 20/11/2017.
 */
public class Invitado {
    private String nombre;
    private String  apellido;
    private int mesa;

    public Invitado(String nombre,String apellido,int mesa){
        this.nombre=nombre;
        this.apellido=apellido;
        this.mesa=mesa;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public void setApellido(String apellido){
        this.apellido=apellido;
    }

    public void setMesa(int mesa){
        this.mesa=mesa;
    }

    public String getNombre(){
        return nombre;
    }
    public String getApellido(){return apellido;}
    public int getMesa(){
        return mesa;
    }
}
