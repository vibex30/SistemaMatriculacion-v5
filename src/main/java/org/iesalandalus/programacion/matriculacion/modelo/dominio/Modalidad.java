package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum Modalidad {
    PRESENCIAL("PRESENCIAL"),
    SEMIPRESENCIAL("SEMIPRESENCIAL");

    private final String cadenaAMostrar;


    Modalidad (String cadenaAMostrar){
        this.cadenaAMostrar=cadenaAMostrar;
    }

    public String imprimir(){
        return cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "Modalidad: " + imprimir();
    }
}
