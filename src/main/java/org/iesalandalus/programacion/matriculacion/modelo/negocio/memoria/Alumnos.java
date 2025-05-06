package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Alumnos implements IAlumnos {
    private ArrayList<Alumno> coleccionAlumnos;


    //CONSTRUCTORES
    public Alumnos (){
        this.coleccionAlumnos=new ArrayList<>();
    }

    //GETTER
    public ArrayList<Alumno>get(){
        return copiaProfundaAlumno();
    }

    private ArrayList<Alumno> copiaProfundaAlumno(){
        ArrayList<Alumno> copiaProfunda= new ArrayList<>();
        for (Alumno alumno:coleccionAlumnos){
            copiaProfunda.add(new Alumno(alumno));
        }
        return copiaProfunda;
    }



    public int getTamano() {
        return coleccionAlumnos.size();
    }



    /*
    Se permitirán insertar alumnos no nulos al final de la colección
    sin admitir repetidos.*/

    public void insertar (Alumno alumno) throws OperationNotSupportedException{
        if (alumno==null){
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }
        if (coleccionAlumnos.contains(alumno)){
            throw new OperationNotSupportedException("Error, el slumno está ya insertado.");
        }
        coleccionAlumnos.add(new Alumno(alumno));

    }


    /* buscar devolverá un alumno si éste se encuentra en la
    colección y null en caso contrario.*/

    public Alumno buscar (Alumno alumno) {
        if (alumno==null){
            throw new NullPointerException("Error, no se puede buscar un alumno nulo");
        }
        for (Alumno alumnoA: coleccionAlumnos){
            if (alumnoA.equals(alumno)){
                return new Alumno(alumnoA);
            }
        }
        return null;

    }


    public void borrar (Alumno alumno){
        if (alumno==null){
            throw new NullPointerException("Error, no se puede borrar un alumno nulo");
        }
        if (!coleccionAlumnos.remove(alumno)){
            throw new IllegalArgumentException("Error, no se puede borrar el alumno.");
        }

    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }
}
