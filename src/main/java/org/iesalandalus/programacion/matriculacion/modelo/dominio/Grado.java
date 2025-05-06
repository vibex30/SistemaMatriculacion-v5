package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public abstract class Grado {
    protected String nombre;
    protected String iniciales;
    protected int numAnios;

    public Grado(String nombre){
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }

    protected void setNombre(String nombre) {
        if (nombre==null){
            throw new NullPointerException("Error, el nomnbre no puede ser null.");
        }
        if (nombre.isEmpty()||nombre.isBlank()){
            throw new IllegalArgumentException("Error, el nombre no puede estar en blanco");
        }
        this.nombre = nombre;
        getIniciales();

    }


    private String getIniciales() {
        return nombre.substring(0, Math.min(nombre.length(), 3)).toUpperCase();
    }

    public int getNumAnios() {
        return numAnios;
    }

    public abstract int setNumAnios(int numAnios);


    @Override
    public String toString() {
        return "( "+ getIniciales() + " ) "+ " - Nombre del Grado: "+ nombre+ "\n";
    }
}
