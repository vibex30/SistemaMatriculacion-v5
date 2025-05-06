package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum EspecialidadProfesorado {
    INFORMATICA ("Informática"),
    SISTEMAS ("Sistemas"),
    FOL ("FOL");

    private String cadenaAMostrar;

    private EspecialidadProfesorado(String cadenaAMostrar){
        this.cadenaAMostrar=cadenaAMostrar;
    }

    public String imprimir(){
        return cadenaAMostrar;
    }

    @Override
    public String toString() {
        return this.ordinal()+1+".-"+cadenaAMostrar;
    }
}

