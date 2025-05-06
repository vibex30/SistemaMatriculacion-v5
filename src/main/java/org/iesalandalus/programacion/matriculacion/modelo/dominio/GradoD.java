package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public class GradoD extends Grado{

    private Modalidad modalidad;


    public GradoD (String nombre, int numAnios, Modalidad modalidad){
        super(nombre);
        setNumAnios(numAnios);
        setModalidad(modalidad);

    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        if (modalidad==null){
            throw new NullPointerException("ERROR, la modalidad no puede ser nula.");
        }
        this.modalidad = modalidad;
    }

    @Override
    public int setNumAnios(int numAnios) {
        if (numAnios != 2 && numAnios!=3) {
            throw new IllegalArgumentException("Error, los años no pueden ser distintos de 2 o 3");
        }
        return numAnios;
    }

    @Override
    public String toString() {
        return super.toString() +modalidad;
    }
}
