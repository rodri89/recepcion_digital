package com.example.rodrigo.recepcioncel;

/**
 * Created by Rodrigo on 20/11/2017.
 */
public class Evento {
    private String nombre,lugar,fecha,empresa;
    private int mesa,codigo,cliente,activo;


    public Evento(int codigo,String nombre, String lugar){
        this.nombre=nombre;
        this.lugar=lugar;
        this.codigo=codigo;
    }

    //seria como la descripcion para la base
    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public void setCodigo(int codigo){
        this.codigo=codigo;
    }

    public void setLugar(String lugar){
        this.lugar=lugar;
    }

    public void setFecha(String fecha){
        this.fecha=fecha;
    }

    public void setMesas(int mesa){
        this.mesa=mesa;
    }

    public void setCliente(int cliente){
        this.cliente=cliente;
    }

    public void setEmpresa(String empresa){
        this.empresa=empresa;
    }

    public void setActivo(int valor){
        this.activo=valor;
    }

    public String getNombre(){
        return nombre;
    }
    public int getCodigo(){return codigo;}
    public int getMesa(){
        return mesa;
    }
    public String getLugar(){
        return lugar;
    }
    public String getFecha(){return fecha;}
    public int getCliente(){return cliente;}
    public String getEmpresa(){return empresa;}
    public int getActivo(){return activo;}
}
