package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public class GradoE extends Grado{
    private int numEdiciones;


    public GradoE (String nombre, int numAnios, int numEdiciones){
        super(nombre);
        this.setNumAnios(numAnios);
        setNumEdiciones(numEdiciones);

    }


    public int getNumEdiciones() {
        return numEdiciones;
    }

    public void setNumEdiciones(int numEdiciones) {
        if (numEdiciones<0){
            throw new IllegalArgumentException("Error, el número de ediciones no puede ser 0.");
        }
        this.numEdiciones = numEdiciones;
    }



    @Override
    public int setNumAnios(int numAnios) {
        if (numAnios != 1) {
            throw new IllegalArgumentException("Error, tiene que ser 1 año.");
        }
        return numAnios;

    }

    @Override
    public String toString() {
        return super.toString()+ "Número de ediciones: "+ numEdiciones;
    }
}
