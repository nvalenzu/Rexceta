package com.example.jesi.rexceta;

/**
 * Created by Jesi on 05-10-16.
 */
public class Item_Receta {

    private String nombre;
    private String descripcion;
    private int valoracion; //si me funciona como string busco como dejarlo como fecha
    private String video;
    private String imagen;
    private String dificultad;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValoracion(){
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getVideo(){
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImagen(){
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDificultad(){
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

}
