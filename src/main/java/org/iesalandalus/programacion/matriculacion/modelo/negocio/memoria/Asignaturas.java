package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Asignaturas implements IAsignaturas {
    private ArrayList<Asignatura> coleccionAsignaturas;


    //CONSTRUCTORES
    public Asignaturas (){
        this.coleccionAsignaturas=new ArrayList<>();
    }

    //GETTER
    public ArrayList <Asignatura>get(){
        return copiaProfundaAsignatura();
    }

    private ArrayList <Asignatura> copiaProfundaAsignatura(){
        ArrayList<Asignatura> copiaProfunda=new ArrayList<>();
        for (Asignatura asignatura: coleccionAsignaturas){
            copiaProfunda.add(new Asignatura(asignatura));
        }
        return copiaProfunda;
    }



    public int getTamano() {
        return coleccionAsignaturas.size();
    }




    public void insertar (Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura==null){
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }

        if (coleccionAsignaturas.contains(asignatura)){
            throw new OperationNotSupportedException("Error, el la asignatura ya está insertada.");
        }
        coleccionAsignaturas.add(new Asignatura(asignatura));

    }


    public Asignatura buscar (Asignatura asignatura){
        if (asignatura==null){
            throw new NullPointerException("Error, no se puede buscar una Asignatura nula");
        }
        for (Asignatura a: coleccionAsignaturas){
            if (a.equals(asignatura)){
                return new Asignatura(a);
            }
        }
        return null;
    }


    public void borrar (Asignatura asignatura){
        if (asignatura==null){
            throw new NullPointerException("Error, no se puede borrar una Asignatura nula");
        }

        if (!coleccionAsignaturas.remove(asignatura)){
            throw new IllegalArgumentException("Error, no se puede borrar la asignatura.");
        }


    }


    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }
}
