package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class CiclosFormativos implements ICiclosFormativos {
    private ArrayList<CicloFormativo> coleccionCiclosFormativos;


    //CONSTRUCTORES
    public CiclosFormativos (){
        this.coleccionCiclosFormativos=new ArrayList<>();

    }

    //GETTER


    public ArrayList<CicloFormativo> get(){
        return copiaProfundaCicloFormativo();
    }

    private ArrayList<CicloFormativo> copiaProfundaCicloFormativo(){
        ArrayList<CicloFormativo> copiaProfunda=new ArrayList<>();
        for (CicloFormativo cicloFormativo: coleccionCiclosFormativos){
            copiaProfunda.add(new CicloFormativo(cicloFormativo));
        }
        return copiaProfunda;
    }


    public int getTamano() {
        return coleccionCiclosFormativos.size();
    }




    public void insertar (CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo==null){
            throw new NullPointerException("ERROR: No se puede insertar un ciclo nulo.");
        }
        if (coleccionCiclosFormativos.contains(cicloFormativo)){
            throw new OperationNotSupportedException("Error, el ciclo formativo está ya insertado.");
        }
        coleccionCiclosFormativos.add(new CicloFormativo(cicloFormativo));
    }



    public CicloFormativo buscar (CicloFormativo cicloFormativo){
        if (cicloFormativo==null){
            throw new NullPointerException("Error, no se puede buscar un Ciclo Formativo nulo");
        }
        for (CicloFormativo c: coleccionCiclosFormativos){
            if (c.equals(cicloFormativo)){
                return new CicloFormativo(c);
            }
        }
        return null;
    }


    public void borrar (CicloFormativo cicloFormativo){
        if (cicloFormativo==null){
            throw new NullPointerException("Error, no puede haber un ciclo formativo nulo");
        }
        if (!coleccionCiclosFormativos.remove(cicloFormativo)){
            throw new IllegalArgumentException("Error, no se puede borrar el ciclo formativo.");
        }

    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }


}
