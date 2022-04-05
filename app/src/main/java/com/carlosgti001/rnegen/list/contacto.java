package com.carlosgti001.rnegen.list;

public class contacto
{
    private String nombre;
    private String rne;
    private String fecha;
    private String apellido;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRne() {
        return rne;
    }
    public void setRne(String rne) {
        this.rne = rne;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setApellido (String apellido){
        this.apellido = apellido;
    }
    public String getApellido (){
        return apellido;
    }
}
